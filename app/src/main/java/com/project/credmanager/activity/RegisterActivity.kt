package com.project.credmanager.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.project.credmanager.R
import com.project.credmanager.databinding.ActivityRegisterBinding
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserDetails
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserReq
import com.project.credmanager.network.ApiClient
import com.project.credmanager.network.repository.UserDetailsRepo
import com.project.credmanager.userViewModel.UserApiViewModel.UserApiViewModelFactory
import com.project.credmanager.userViewModel.UserApiViewModel.UserDetailsApiViewModel
import com.project.credmanager.userViewModel.UserDetailsViewModel
import com.project.credmanager.userViewModel.UserViewModelFactory
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading
import com.project.credmanager.utils.NetworkDialog
import com.project.credmanager.utils.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    //    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var userDetailsApiViewModel: UserDetailsApiViewModel
    private var networkDialog: AlertDialog? = null
//    private var isPhoneExists = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NetworkMonitor.isConnected.observe(this) { isConnected ->
            if (!isConnected) {
                if (networkDialog == null || !networkDialog!!.isShowing)
                    networkDialog = NetworkDialog.showNetworkDialog(this)
            } else {
                networkDialog?.dismiss()
                networkDialog = null
            }
        }

        /*val database = CredDB.getDatabase(this)
        val userDetailsDao = database.userDetailsDao()
        val factory = UserViewModelFactory(null, userDetailsDao)
        userDetailsViewModel = ViewModelProvider(this, factory)[UserDetailsViewModel::class.java]*/

        val userDetailsRepo = UserDetailsRepo(ApiClient.apiInterface)
        userDetailsApiViewModel = ViewModelProvider(
            this,
            UserApiViewModelFactory(userDetailsRepo, null)
        )[UserDetailsApiViewModel::class.java]

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        binding.registerBtn.setOnClickListener {
            val phone = binding.phone.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val conPass = binding.conPassword.text.toString()

            val result = HandleUserInput.checkUserInput(false, phone, email, password, conPass)

            if (result.second) {
                binding.registerBtn.isEnabled = false
                userDetailsApiViewModel.getAllUser()
//                handleRegister()
            } else {
                binding.registerBtn.isEnabled = true
                Snackbar.make(this, binding.root, result.first, Snackbar.LENGTH_SHORT).show()
            }
        }

        userDetailsApiViewModel.users.observe(this) { user ->
            Loading.showLoading(this)
            var isPhoneExists = false
            if (user.isNotEmpty()) {
                isPhoneExists = user.any {
                    it.userphone == binding.phone.text.toString().toLongOrNull()
                }
            }
            if (!isPhoneExists) {
                insertUser(binding.password.text.toString())
            } else {
                Loading.dismissLoading()
                binding.registerBtn.isEnabled = true
                Snackbar.make(
                    this@RegisterActivity,
                    binding.root,
                    "Phone number already exists",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@observe
            }
        }

        userDetailsApiViewModel.insertedUser.observe(this) { user ->
            Loading.dismissLoading()
            if (user.status) {
                Toast.makeText(
                    this@RegisterActivity,
                    "User registered successfully",
                    Toast.LENGTH_SHORT
                )
                    .show()
                showLoginPage()
            }
        }

        userDetailsApiViewModel.errorUsers.observe(this) { msg ->
            Loading.dismissLoading()
            binding.registerBtn.isEnabled = true
            Snackbar.make(this, binding.root, msg, Snackbar.LENGTH_SHORT).show()
        }

        userDetailsApiViewModel.errorInsertUsers.observe(this) { msg ->
            Loading.dismissLoading()
            binding.registerBtn.isEnabled = true
            Snackbar.make(this, binding.root, msg, Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun insertUser(password: String) {
        val userId = "CM" + (1000..9999).random()
        val pass = HandleUserInput.bcryptHash(password)
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        /*val userDetails = UserDetails(
            id = 0,
            userId = userId,
            userPhone = binding.phone.text.toString().toLong(),
            password = pass,
            deviceId = deviceId
        )
        userDetailsViewModel.insertUser(userDetails)
        */
        val userDetails = InsertUserReq(
            deviceId = deviceId,
            password = pass,
            userId = userId,
            userPhone = binding.phone.text.toString(),
            userEmail = binding.email.text.toString()
        )

        userDetailsApiViewModel.insertUser(userDetails)
    }

    /*private fun handleRegister() {
        val phone = binding.phone.text.toString()
        val password = binding.password.text.toString()
        val conPass = binding.conPassword.text.toString()

        val result = HandleUserInput.checkUserInput(false, phone, password, conPass)

        if (result.second) {
            Loading.showLoading(this)

            lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    val allUser = userDetailsViewModel.getAllUser()
                    for (element in allUser) {
                        if (element.userPhone == phone.toLong())
                            isPhoneExists = true
                    }
                    if (!isPhoneExists) {
                        val userId = "CM" + (1000..9999).random()
                        val pass = HandleUserInput.bcryptHash(password)
                        val deviceId =
                            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                        val userDetails = UserDetails(
                            id = 0,
                            userId = userId,
                            userPhone = binding.phone.text.toString().toLong(),
                            password = pass,
                            deviceId = deviceId
                        )
                        userDetailsViewModel.insertUser(userDetails)
                        Toast.makeText(
                            this@RegisterActivity,
                            "User registered successfully",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        showLoginPage()
                    } else {
                        Snackbar.make(
                            this@RegisterActivity,
                            binding.root,
                            "Phone number already exists",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            Loading.dismissLoading()
        } else {
            Snackbar.make(this, binding.root, result.first, Snackbar.LENGTH_SHORT).show()
        }
    }*/

    private fun showLoginPage() {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }


}
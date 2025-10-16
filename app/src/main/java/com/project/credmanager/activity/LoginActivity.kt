package com.project.credmanager.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.project.credmanager.R
import com.project.credmanager.dao.UserDetailsDao
import com.project.credmanager.databinding.ActivityLoginBinding
import com.project.credmanager.databinding.ActivityMainBinding
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserDetails
import com.project.credmanager.network.ApiClient
import com.project.credmanager.network.repository.UserDetailsRepo
import com.project.credmanager.userViewModel.UserApiViewModel.UserApiViewModelFactory
import com.project.credmanager.userViewModel.UserApiViewModel.UserDetailsApiViewModel
import com.project.credmanager.userViewModel.UserDetailsViewModel
import com.project.credmanager.userViewModel.UserViewModel
import com.project.credmanager.userViewModel.UserViewModelFactory
import com.project.credmanager.utils.AppPreference
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var userDetailsApiViewModel: UserDetailsApiViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AppPreference.getLoginStatus(this) && AppPreference.getGeneratedUserId(this) != "0") {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        val database = CredDB.getDatabase(this)
        val userDetailsDao = database.userDetailsDao()
        val factory = UserViewModelFactory(null, userDetailsDao)
        userDetailsViewModel = ViewModelProvider(this, factory)[UserDetailsViewModel::class.java]

        val apiInterface = ApiClient.apiInterface
        val userDetailsRepo = UserDetailsRepo(apiInterface)
        userDetailsApiViewModel = ViewModelProvider.create(
            this,
            UserApiViewModelFactory(userDetailsRepo, null)
        )[UserDetailsApiViewModel::class.java]


        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener {
            handleLogin()
        }

        binding.phone.addTextChangedListener(textWatcher)
        binding.password.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            if (p0.toString().isNotEmpty()) {
                Handler().postDelayed({
                    HandleUserInput.hideKeyboard(this@LoginActivity, binding.root)
                }, 5000)
            }
        }

    }

    private fun handleLogin() {
        val phone = binding.phone.text.toString()
        val pass = binding.password.text.toString()
        val isCheck = HandleUserInput.checkUserInput(true, phone, pass, "")
        if (isCheck.second) {
            Loading.showLoading(this)
            userDetailsViewModel.getSingleUser(phone.toLong()) { user ->
                Loading.dismissLoading()
                if (user == null) {
                    Snackbar.make(this, binding.root, "User not found", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    val storedPass = HandleUserInput.verifyPassword(pass, user.password)
                    if (storedPass) {
                        AppPreference.setGeneratedUserId(this, user.id.toString())
                        AppPreference.setUserId(this, user.userId)
                        AppPreference.setUserPhone(this, user.userPhone.toString())
                        AppPreference.setDeviceId(this, user.deviceId)
                        AppPreference.setLoginStatus(this, true)
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    } else {
                        Snackbar.make(
                            this,
                            binding.root,
                            "Wrong credentials",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Snackbar.make(this, binding.root, isCheck.first, Snackbar.LENGTH_SHORT).show()
        }
    }


}
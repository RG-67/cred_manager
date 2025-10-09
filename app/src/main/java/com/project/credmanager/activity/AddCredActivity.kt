package com.project.credmanager.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.project.credmanager.R
import com.project.credmanager.databinding.ActivityAddCredBinding
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserCred
import com.project.credmanager.userViewModel.UserViewModel
import com.project.credmanager.userViewModel.UserViewModelFactory
import com.project.credmanager.utils.AppPreference
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading

class AddCredActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCredBinding
    private lateinit var userViewModel: UserViewModel
    private var id = "0"
    private var valueFor = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = CredDB.getDatabase(this)
        val userCredDao = database.userCredDao()
        val factory = UserViewModelFactory(userCredDao, null)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        valueFor = intent.getStringExtra("for")!!
        if (valueFor == "edit") {
            id = intent.getStringExtra("id")!!
            val title = intent.getStringExtra("title")
            val userName = intent.getStringExtra("userName")
            val password = intent.getStringExtra("password")
            val description = intent.getStringExtra("description")

            binding.title.setText(title)
            binding.userName.setText(userName)
            binding.password.setText(password)
            binding.desc.setText(description)
        }

        clickMethod()
    }

    private fun clickMethod() {
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.submitBtn.setOnClickListener {
            val title = binding.title.text.toString()
            val userName = binding.userName.text.toString()
            val password = binding.password.text.toString()
            val description = binding.desc.text.toString()
            val isInput = HandleUserInput.checkCredInput(title, userName, password, description)
            if (isInput.second) {
                Loading.showLoading(this)
                val userCred = UserCred(
                    id.toInt(),
                    generatedUserId = AppPreference.getGeneratedUserId(this)!!.toInt(),
                    userId = AppPreference.getUserId(this)!!,
                    userPhone = AppPreference.getUserPhone(this)!!.toLong(),
                    deviceId = AppPreference.getDeviceId(this)!!,
                    title = title,
                    userName = userName,
                    password = password,
                    description = description
                )
                Log.d("UserCred ==>", userCred.toString())
                if (valueFor == "edit") {
                    userViewModel.updateUserCred(userCred)
                } else {
                    userViewModel.insertUserCred(userCred)
                }
                Loading.dismissLoading()
                finish()
            } else {
                Snackbar.make(this, binding.root, isInput.first, Snackbar.LENGTH_SHORT).show()
            }
        }
    }


}
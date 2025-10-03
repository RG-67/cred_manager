package com.project.credmanager.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.credmanager.R
import com.project.credmanager.databinding.ActivityAddCredBinding

class AddCredActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCredBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCredBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }


}
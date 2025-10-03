package com.project.credmanager.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.credmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickMethod()


    }

    private fun clickMethod() {
        binding.addBtn.setOnClickListener {
            startActivity(Intent(this, AddCredActivity::class.java))
        }

    }

}
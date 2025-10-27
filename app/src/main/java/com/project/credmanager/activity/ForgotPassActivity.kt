package com.project.credmanager.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.project.credmanager.R
import com.project.credmanager.databinding.ActivityForgotPassBinding
import com.project.credmanager.utils.Loading
import java.util.concurrent.TimeUnit

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPassBinding
    private var otp = ""
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setClickMethod()

    }

    private fun setClickMethod() {
        binding.submitBtn.setOnClickListener {
            val number = binding.phoneNumber.text.toString()
            if (number == "" || number.length != 10) {
                Snackbar.make(this, binding.root, "Enter valid number", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                sendOtp("+91" + number.trim())
            }
        }

        binding.verifyBtn.setOnClickListener {
            val enteredOtp =
                binding.firstOtp.text.toString() + binding.secondOtp.text.toString() + binding.thirdOtp.text.toString() + binding.forthOtp.text.toString() +
                        binding.fifthOtp.text.toString() + binding.sixthOtp.text.toString()

            if (enteredOtp.length != 6) {
                Snackbar.make(this, binding.root, "Enter OTP", Snackbar.LENGTH_SHORT).show()
            } else {
                val credential = PhoneAuthProvider.getCredential(otp, enteredOtp)
                signInWithCredential(credential)
            }
        }

        binding.saveBtn.setOnClickListener {
            if (binding.newPassword.text.toString().isEmpty()) {
                Snackbar.make(this, binding.root, "Enter new password", Snackbar.LENGTH_SHORT)
                    .show()
            } else if (binding.confirmPassword.text.toString().isEmpty()) {
                Snackbar.make(this, binding.root, "Enter confirm password", Snackbar.LENGTH_SHORT)
                    .show()
            } else if (binding.newPassword.text.toString() != binding.confirmPassword.text.toString()) {
                Snackbar.make(this, binding.root, "Password not match", Snackbar.LENGTH_SHORT)
                    .show()
            } else {

            }
        }

        binding.newPass.setOnClickListener {
            setPassVisibility(binding.newPassword, binding.newPass)
        }

        binding.conFirmPass.setOnClickListener {
            setPassVisibility(binding.confirmPassword, binding.newPass)
        }

        binding.firstOtp.addTextChangedListener(textWatcher)
        binding.secondOtp.addTextChangedListener(textWatcher)
        binding.thirdOtp.addTextChangedListener(textWatcher)
        binding.forthOtp.addTextChangedListener(textWatcher)
        binding.fifthOtp.addTextChangedListener(textWatcher)
        binding.sixthOtp.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (s.toString().isNotEmpty()) {
                if (binding.firstOtp.isFocused) binding.secondOtp.requestFocus()
                else if (binding.secondOtp.isFocused) binding.thirdOtp.requestFocus()
                else if (binding.thirdOtp.isFocused) binding.forthOtp.requestFocus()
                else if (binding.forthOtp.isFocused) binding.fifthOtp.requestFocus()
                else if (binding.fifthOtp.isFocused) binding.sixthOtp.requestFocus()
            }
        }
    }

    private fun sendOtp(number: String) {
        Loading.showLoading(this)
        val options = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Loading.dismissLoading()
            signInWithCredential(credential)
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            Loading.dismissLoading()
            Log.e("Verification Failed ==>", exception.toString())
            Snackbar.make(
                this@ForgotPassActivity,
                binding.root,
                "Invalid request try after sometime",
                Snackbar.LENGTH_SHORT
            ).show()
            finish()
        }

        override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
            Loading.dismissLoading()
            Toast.makeText(this@ForgotPassActivity, "OTP send successfully", Toast.LENGTH_SHORT)
                .show()

            val text = getString(R.string.enter_otp, binding.phoneNumber.text.toString())
            binding.enterOtpText.text = text

            otp = verificationId
            binding.numberLin.visibility = View.GONE
            binding.verifyLin.visibility = View.VISIBLE
        }
    }


    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verification complete", Toast.LENGTH_SHORT).show()
                    binding.verifyLin.visibility = View.GONE
                    binding.newPasswordLin.visibility = View.VISIBLE

                    binding.changePassText.text =
                        getString(R.string.change_password, binding.phoneNumber.text.toString())
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
    }

    private fun setPassVisibility(edtText: EditText, image: ImageView) {
        if (edtText.text.toString().isNotEmpty()) {
            if (edtText.transformationMethod == null) {
                edtText.transformationMethod = PasswordTransformationMethod()
                image.setImageResource(R.drawable.eye_close)
            } else {
                edtText.transformationMethod = null
                image.setImageResource(R.drawable.eye_open)
            }
            edtText.setSelection(edtText.text.length)
        }
    }


}
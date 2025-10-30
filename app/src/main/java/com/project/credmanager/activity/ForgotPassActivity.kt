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
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
/*import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider*/
import com.project.credmanager.R
import com.project.credmanager.databinding.ActivityForgotPassBinding
import com.project.credmanager.network.ApiClient
import com.project.credmanager.network.repository.UserDetailsRepo
import com.project.credmanager.userViewModel.UserApiViewModel.UserApiViewModelFactory
import com.project.credmanager.userViewModel.UserApiViewModel.UserDetailsApiViewModel
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading
import java.util.concurrent.TimeUnit

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPassBinding

    private var otp = ""

    //    private var auth: FirebaseAuth? = null
    private lateinit var userDetailsApiViewModel: UserDetailsApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        auth = FirebaseAuth.getInstance()

        val apiInterface = ApiClient.apiInterface
        userDetailsApiViewModel = ViewModelProvider(
            this,
            UserApiViewModelFactory(UserDetailsRepo(apiInterface), null)
        )[UserDetailsApiViewModel::class.java]

        userDetailsApiViewModel.getUserByPhone.observe(this) { user ->
            otp = (100000..999999).random().toString()
            userDetailsApiViewModel.sendOtp(user.email, otp)
        }

        userDetailsApiViewModel.errorUsersByPhone.observe(this) {
            Loading.dismissLoading()
            Snackbar.make(this, binding.root, "Phone or email not exists", Snackbar.LENGTH_SHORT)
                .show()
            setEnable()
        }

        userDetailsApiViewModel.sendOtp.observe(this) { msg ->
            Loading.dismissLoading()
            Snackbar.make(this, binding.root, msg, Snackbar.LENGTH_SHORT).show()
            binding.numberLin.visibility = View.GONE
            binding.verifyLin.visibility = View.VISIBLE
        }

        userDetailsApiViewModel.errorSendOtp.observe(this) {
            Loading.dismissLoading()
            Snackbar.make(this, binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        /*userDetailsApiViewModel.verifyOtp.observe(this) {
            Loading.dismissLoading()
            Snackbar.make(this, binding.root, it, Snackbar.LENGTH_SHORT).show()
            binding.verifyLin.visibility = View.GONE
            binding.newPasswordLin.visibility = View.VISIBLE
        }*/

        /*userDetailsApiViewModel.errorVerifyOtp.observe(this) {
            Loading.dismissLoading()
            Snackbar.make(this, binding.root, it, Snackbar.LENGTH_SHORT).show()
        }*/

        userDetailsApiViewModel.passwordChange.observe(this) {
            Loading.dismissLoading()
            Snackbar.make(this, binding.root, it, Snackbar.LENGTH_SHORT).show()
            finish()
        }

        userDetailsApiViewModel.errorPasswordChange.observe(this) {
            Loading.dismissLoading()
            Snackbar.make(this, binding.root, it, Snackbar.LENGTH_SHORT).show()
        }


        handleUserInput()

    }

    private fun handleUserInput() {
        binding.submitBtn.setOnClickListener {
            val number = binding.phoneNumber.text.toString()
            val email = binding.email.text.toString()

            val result = HandleUserInput.checkVerifyPassInput(number, email)

            if (!result.second) {
                Snackbar.make(this, binding.root, result.first, Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                setDisable()
                sendOtp()
            }
        }

        binding.verifyBtn.setOnClickListener {
            val enteredOtp =
                binding.firstOtp.text.toString() + binding.secondOtp.text.toString() + binding.thirdOtp.text.toString() + binding.forthOtp.text.toString() +
                        binding.fifthOtp.text.toString() + binding.sixthOtp.text.toString()

            if (enteredOtp.length != 6) {
                Snackbar.make(this, binding.root, "Enter OTP", Snackbar.LENGTH_SHORT).show()
            } else {
                /*val credential = PhoneAuthProvider.getCredential(otp, enteredOtp)
                signInWithCredential(credential)*/
                if (enteredOtp == otp) {
                    Snackbar.make(
                        this,
                        binding.root,
                        "OTP verified successfully",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    binding.changePassText.text = getString(
                        R.string.change_password,
                        "+91 " + binding.phoneNumber.text.toString(),
                        binding.email.text.toString()
                    )
                    binding.verifyLin.visibility = View.GONE
                    binding.newPasswordLin.visibility = View.VISIBLE
                } else {
                    Snackbar.make(this, binding.root, "OTP does not match", Snackbar.LENGTH_SHORT)
                        .show()
                }
                /*Loading.showLoading(this)
                userDetailsApiViewModel.verifyOtp(binding.email.text.toString(), enteredOtp)*/
            }
        }

        binding.saveBtn.setOnClickListener {
            val newPass = binding.newPassword.text.toString()
            val conPass = binding.confirmPassword.text.toString()
            val result = HandleUserInput.checkNewPassInput(newPass, conPass)

            if (result.second) {
                Loading.showLoading(this)
                val bCryptPass = HandleUserInput.bcryptHash(conPass)
                userDetailsApiViewModel.passwordChange(
                    binding.email.text.toString(),
                    binding.phoneNumber.text.toString(),
                    bCryptPass
                )
            } else {
                Snackbar.make(this, binding.root, result.first, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.newPass.setOnClickListener {
            setPassVisibility(binding.newPassword, binding.newPass)
        }

        binding.conFirmPass.setOnClickListener {
            setPassVisibility(binding.confirmPassword, binding.conFirmPass)
        }

        binding.firstOtp.addTextChangedListener(textWatcher)
        binding.secondOtp.addTextChangedListener(textWatcher)
        binding.thirdOtp.addTextChangedListener(textWatcher)
        binding.forthOtp.addTextChangedListener(textWatcher)
        binding.fifthOtp.addTextChangedListener(textWatcher)
        binding.sixthOtp.addTextChangedListener(textWatcher)

    }

    private fun setEnable() {
        binding.submitBtn.isClickable = true
        binding.submitBtn.isEnabled = true
        binding.phoneNumber.isEnabled = true
        binding.phoneNumber.isClickable = true
        binding.email.isEnabled = true
        binding.email.isClickable = true
    }

    private fun setDisable() {
        binding.submitBtn.isClickable = false
        binding.submitBtn.isEnabled = false
        binding.phoneNumber.isEnabled = false
        binding.phoneNumber.isClickable = false
        binding.email.isEnabled = false
        binding.email.isClickable = false
    }

    private fun sendOtp() {
        Loading.showLoading(this)
        userDetailsApiViewModel.getUserByPhone(
            phone = binding.phoneNumber.text.toString(),
            email = binding.email.text.toString()
        )
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


    /*private val callBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
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
    }*/

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
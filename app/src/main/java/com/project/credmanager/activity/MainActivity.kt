package com.project.credmanager.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.credmanager.R
import com.project.credmanager.adapter.UserCredAdapter
import com.project.credmanager.databinding.ActivityMainBinding
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserCred
import com.project.credmanager.userViewModel.UserViewModel
import com.project.credmanager.userViewModel.UserViewModelFactory
import com.project.credmanager.utils.AppPreference
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading
import kotlinx.serialization.descriptors.buildSerialDescriptor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userCredAdapter: UserCredAdapter
    private val credList = ArrayList<UserCred>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = CredDB.getDatabase(this)
        val userCredDao = database.userCredDao()
        val factory = UserViewModelFactory(userCredDao, null)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        clickMethod()
        getCreds()
        setAdapter()

    }


    private fun setAdapter() {
        userCredAdapter = UserCredAdapter(this, credList,
            edtBtn = { edtCred, _ ->
                binding.searchCred.setText("")
                val intent = Intent(this, AddCredActivity::class.java)
                intent.putExtra("for", "edit")
                intent.putExtra("id", edtCred.id.toString())
                intent.putExtra("title", edtCred.title)
                intent.putExtra("userName", edtCred.userName)
                intent.putExtra("password", edtCred.password)
                intent.putExtra("description", edtCred.description)
                startActivity(intent)
            },
            dltBtn = { dltCred, position ->
                showAlert(dltCred, position)
            })
        binding.recyclerManager.adapter = userCredAdapter
    }

    private fun clickMethod() {
        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddCredActivity::class.java)
            intent.putExtra("for", "add")
            startActivity(intent)
        }

        binding.profileBtn.setOnClickListener {
            binding.drawer.closeDrawers()
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.logOutBtn.setOnClickListener {
            showLogOutAlert()
        }

        binding.searchCred.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            if (p0.toString().isNotEmpty()) {
                Handler().postDelayed({
                    if (credList.size != 0)
                        showSearchItem(p0.toString())
                }, 2000)
            }
        }
    }

    private fun showSearchItem(query: String) {
        userCredAdapter.filteredItem(query)
    }

    private fun getCreds() {
        Loading.showLoading(this)
        userViewModel.getAllUserById(
            AppPreference.getGeneratedUserId(this)!!.toInt(),
            AppPreference.getUserId(this)!!
        ).observe(this) { crdList ->
            Log.d("CredList ==>", crdList.toString())
            if (crdList.isNotEmpty()) {
                binding.notFoundLin.visibility = View.GONE
                binding.recyclerManager.visibility = View.VISIBLE
                credList.clear()
                for (i in crdList.indices) {
                    val pos = crdList[i]
                    credList.add(
                        UserCred(
                            pos.id,
                            pos.generatedUserId,
                            pos.userId,
                            pos.userPhone,
                            pos.deviceId,
                            pos.title,
                            pos.userName,
                            pos.password,
                            pos.description
                        )
                    )
                }
            } else {
                binding.recyclerManager.visibility = View.GONE
                binding.notFoundLin.visibility = View.VISIBLE
            }
            setAdapter()
            Loading.dismissLoading()
        }
    }

    private fun showAlert(cred: UserCred, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setIcon(R.drawable.logo)
        builder.setMessage("You sure to remove ?")
        builder.setPositiveButton("Ok") { dialog, _ ->
            binding.searchCred.setText("")
            val userCred = UserCred(
                cred.id,
                cred.generatedUserId,
                cred.userId,
                cred.userPhone,
                cred.deviceId,
                cred.title,
                cred.userName,
                cred.password,
                cred.description
            )
            userViewModel.deleteUserCred(userCred)
            userCredAdapter.notifyItemRemoved(position)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showLogOutAlert() {
        binding.drawer.closeDrawers()
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.logo)
        builder.setTitle("Alert")
        builder.setMessage("Are you sure to log out ?")
        builder.setPositiveButton(
            "Ok"
        ) { dialog, _ ->
            dialog.dismiss()
            AppPreference.setLoginStatus(this, false)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }


}
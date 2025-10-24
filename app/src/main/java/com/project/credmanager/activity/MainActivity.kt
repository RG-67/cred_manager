package com.project.credmanager.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.credmanager.R
import com.project.credmanager.adapter.UserCredAdapter
import com.project.credmanager.databinding.ActivityMainBinding
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserCred
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.InsertUserCredReq
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UpdateUserCredReq
import com.project.credmanager.network.ApiClient
import com.project.credmanager.network.ApiInterface
import com.project.credmanager.network.repository.UserCredRepo
import com.project.credmanager.userViewModel.UserApiViewModel.UserApiViewModelFactory
import com.project.credmanager.userViewModel.UserApiViewModel.UserCredApiViewModel
import com.project.credmanager.userViewModel.UserViewModel
import com.project.credmanager.userViewModel.UserViewModelFactory
import com.project.credmanager.utils.AppPreference
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading
import com.project.credmanager.utils.NetworkMonitor
import kotlinx.serialization.descriptors.buildSerialDescriptor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userCredAdapter: UserCredAdapter
    private lateinit var userCredApiViewModel: UserCredApiViewModel
    private val credList = ArrayList<UserCred>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userCredRepo = UserCredRepo(ApiClient.apiInterface)
        userCredApiViewModel = ViewModelProvider(
            this,
            UserApiViewModelFactory(null, userCredRepo)
        )[UserCredApiViewModel::class.java]

        val database = CredDB.getDatabase(this)
        val userCredDao = database.userCredDao()
        val factory = UserViewModelFactory(userCredDao, null)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]


        userCredApiViewModel.allCred.observe(this) { cred ->
            if (cred.isNotEmpty() && credList.isEmpty()) {
                Loading.showLoading(this)
                for (element in cred) {
                    val userCred = UserCred(
                        0,
                        generatedUserId = AppPreference.getGeneratedUserId(this)!!.toInt(),
                        userId = AppPreference.getUserId(this)!!,
                        userPhone = AppPreference.getUserPhone(this)!!.toLong(),
                        deviceId = AppPreference.getDeviceId(this)!!,
                        title = element.title,
                        userName = element.username,
                        password = element.password,
                        description = element.description
                    )
                    userViewModel.insertUserCred(userCred)
                }
                Loading.dismissLoading()
            } else if (cred.isEmpty() && credList.isNotEmpty()) {
                Loading.showLoading(this)
                for (element in credList) {
                    val userCred = InsertUserCredReq(
                        description = element.description,
                        deviceId = element.deviceId,
                        generatedUserId = AppPreference.getInternalId(this)!!.toInt(),
                        password = element.password,
                        title = element.title,
                        userId = element.userId,
                        userName = element.userName,
                        userPhone = element.userPhone,
                        localCredId = element.id
                    )
                    userCredApiViewModel.insertUserCred(userCred)
                }
                Loading.dismissLoading()
            } else if (cred.isNotEmpty() && credList.isNotEmpty()) {
                if (cred.size == credList.size) {
                    for (localDbItem in credList) {
                        val isItemSame = cred.any { apiItem ->
                            localDbItem.title == apiItem.title ||
                                    localDbItem.description != apiItem.description ||
                                    localDbItem.userName != apiItem.username ||
                                    localDbItem.password != apiItem.password
                        }
                        if (isItemSame) {
                            val map = HashMap<String, String>()
                            map["internalId"] = AppPreference.getInternalId(this).toString()
                            map["generatedUserId"] =
                                AppPreference.getGeneratedUserId(this).toString()
                            map["userId"] = AppPreference.getUserId(this).toString()
                            map["localCredId"] = localDbItem.id.toString()

                            val updateUserCredReq = UpdateUserCredReq(
                                description = localDbItem.description,
                                deviceId = localDbItem.deviceId,
                                password = localDbItem.password,
                                title = localDbItem.title,
                                userName = localDbItem.userName,
                                userPhone = localDbItem.userPhone
                            )
                            userCredApiViewModel.updateUserCred(map, updateUserCredReq)
                        }
                    }
                } else {
//                Add api items missing from local database
                    for (apiData in cred) {
                        val isItemExists = credList.any { localItem ->
                            apiData.title == localItem.title &&
                                    apiData.description == localItem.description &&
                                    apiData.username == localItem.userName &&
                                    apiData.password == localItem.password &&
                                    apiData.local_cred_id == localItem.id.toLong()
                        }
                        if (!isItemExists) {
                            val newUserCred = UserCred(
                                0,
                                generatedUserId = AppPreference.getGeneratedUserId(this)!!.toInt(),
                                userId = AppPreference.getUserId(this)!!,
                                userPhone = AppPreference.getUserPhone(this)!!.toLong(),
                                deviceId = AppPreference.getDeviceId(this)!!,
                                title = apiData.title,
                                userName = apiData.username,
                                password = apiData.password,
                                description = apiData.description
                            )
                            userViewModel.insertUserCred(newUserCred)
                        }
                    }

//                Add local database items missing from api items
                    for (localDbItem in credList) {
                        val isItemExists = cred.any { apiItem ->
                            localDbItem.title == apiItem.title &&
                                    localDbItem.description == apiItem.description &&
                                    localDbItem.userName == apiItem.username &&
                                    localDbItem.password == apiItem.password &&
                                    localDbItem.id.toLong() == apiItem.local_cred_id
                        }
                        if (!isItemExists) {
                            val userCred = InsertUserCredReq(
                                description = localDbItem.description,
                                deviceId = localDbItem.deviceId,
                                generatedUserId = AppPreference.getInternalId(this)!!.toInt(),
                                password = localDbItem.password,
                                title = localDbItem.title,
                                userId = localDbItem.userId,
                                userName = localDbItem.userName,
                                userPhone = localDbItem.userPhone,
                                localCredId = localDbItem.id
                            )
                            userCredApiViewModel.insertUserCred(userCred)
                        }
                    }
                }
            }
        }

        userCredApiViewModel.allCredError.observe(this) { msg ->
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

        userCredApiViewModel.insertedCred.observe(this) { cred ->

        }

        userCredApiViewModel.insertedCredError.observe(this) { msg ->
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        userCredApiViewModel.updatedCred.observe(this) { cred ->

        }

        userCredApiViewModel.updateCredError.observe(this) { msg ->

        }


        clickMethod()
        setAdapter()

    }

    override fun onResume() {
        super.onResume()
        getCreds()
        NetworkMonitor.isConnected.observe(this) { isConnected ->
            if (isConnected) {
                syncData()
            }
        }
    }

    private fun syncData() {
        userCredApiViewModel.getAllUserCred(
            AppPreference.getInternalId(this)!!.toBigInteger(),
            AppPreference.getUserId(this)!!
        )
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
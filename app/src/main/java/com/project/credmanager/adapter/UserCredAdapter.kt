package com.project.credmanager.adapter

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.project.credmanager.R
import com.project.credmanager.databinding.ItemLayoutBinding
import com.project.credmanager.model.UserCred


class UserCredAdapter(
    private val context: Context,
    private val credList: ArrayList<UserCred>,
    private val edtBtn: (UserCred, Int) -> Unit,
    private val dltBtn: (UserCred, Int) -> Unit
) : RecyclerView.Adapter<UserCredAdapter.ViewHolder>() {

    private val filteredList: MutableList<UserCred> = credList.toMutableList()

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCredAdapter.ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: UserCredAdapter.ViewHolder, position: Int) {
        with(holder.binding) {
            val pos = filteredList[position]
            title.text = pos.title
            userName.text = pos.userName
            password.text = pos.password
            description.text = pos.description

            editBtn.setOnClickListener {
                edtBtn(pos, position)
            }

            deleteBtn.setOnClickListener {
                dltBtn(pos, position)
            }

            showEye.setOnClickListener {
                if (password.transformationMethod == null) {
                    password.transformationMethod = PasswordTransformationMethod()
                    showEye.setImageResource(R.drawable.eye_close)
                } else {
                    password.transformationMethod = null
                    showEye.setImageResource(R.drawable.eye_open)
                }
            }

        }
    }

    override fun getItemCount() = filteredList.size

    fun filteredItem(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(credList)
        } else {
            for (item in credList) {
                if (
                    item.title.lowercase().contains(query.lowercase()) ||
                    item.userName.lowercase().contains(query.lowercase()) ||
                    item.password.lowercase().contains(query.lowercase()) ||
                    item.description.lowercase().contains(query.lowercase())
                )
                    filteredList.add(item)
            }
        }
        notifyDataSetChanged()
    }


}
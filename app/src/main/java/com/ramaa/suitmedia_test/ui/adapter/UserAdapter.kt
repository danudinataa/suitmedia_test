package com.ramaa.suitmedia_test.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramaa.suitmedia_test.R
import com.ramaa.suitmedia_test.data.UserResponseItem
import com.ramaa.suitmedia_test.databinding.ItemLayoutBinding

class UserAdapter(
    private val userList: MutableList<UserResponseItem?>,
    private val onItemClicked: (UserResponseItem) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLayoutBinding.bind(itemView)

        fun bind(user: UserResponseItem) {
            binding.tvName.text = "${user.firstName} ${user.lastName}"
            binding.tvEmail.text = user.email
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(binding.imgUser)

            itemView.setOnClickListener {
                onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        userList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = userList.size

    fun addUsers(users: List<UserResponseItem?>) {
        val startPosition = userList.size
        userList.addAll(users)
        notifyItemRangeInserted(startPosition, users.size)
    }

    fun clearUsers() {
        userList.clear()
        notifyDataSetChanged()
    }
}
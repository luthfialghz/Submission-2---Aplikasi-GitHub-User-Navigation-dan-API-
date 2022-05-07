package com.example.githubuserapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.ItemUserBinding
import com.example.githubuserapp.ui.activity.DetailUserActivity

class ListUserAdapter(private val listDataUsersGithub: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListDataHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<User>) {
        listDataUsersGithub.clear()
        listDataUsersGithub.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataUsers: User) {
            Glide.with(itemView.context)
                .load(dataUsers.avatar)
                .circleCrop()
                .into(binding.ivItemAvatar)

            binding.tvItemName.text = dataUsers.name
            binding.tvItemUsername.text = dataUsers.username
            binding.tvItemCompany.text = dataUsers.company
            binding.countRepository.text =
                itemView.context.getString(R.string.repository, dataUsers.repository)
            binding.countFollowers.text =
                itemView.context.getString(R.string.follower, dataUsers.followers)
            binding.countFollowing.text =
                itemView.context.getString(R.string.followings, dataUsers.following)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListDataHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDataUsersGithub.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataUsersGithub[position])

        val data = listDataUsersGithub[position]
        holder.itemView.setOnClickListener {

            val dataUserIntent = User(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val mIntent = Intent(it.context, DetailUserActivity::class.java)
            mIntent.putExtra(DetailUserActivity.EXTRA_USER, dataUserIntent)
            it.context.startActivity(mIntent)
        }

    }

}
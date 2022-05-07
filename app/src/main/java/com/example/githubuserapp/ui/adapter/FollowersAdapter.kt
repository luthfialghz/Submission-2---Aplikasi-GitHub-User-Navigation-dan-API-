package com.example.githubuserapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.ItemUserBinding
import com.example.githubuserapp.ui.activity.DetailUserActivity

class FollowersAdapter(private val listFollower: ArrayList<User>) :
    RecyclerView.Adapter<FollowersAdapter.ListDataHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<User>) {
        listFollower.clear()
        listFollower.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataFollowers: User) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(dataFollowers.avatar)
                    .circleCrop()
                    .into(binding.ivItemAvatar)

                binding.tvItemName.text = dataFollowers.name
                binding.tvItemUsername.text = dataFollowers.username
                binding.tvItemCompany.text = dataFollowers.company


                binding.countRepository.text =
                    itemView.context.getString(R.string.repository, dataFollowers.repository)
                binding.countFollowers.text =
                    itemView.context.getString(R.string.follower, dataFollowers.followers)
                binding.countFollowing.text =
                    itemView.context.getString(R.string.follower, dataFollowers.following)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListDataHolder(binding)

    }

    override fun getItemCount(): Int {
        return listFollower.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listFollower[position])


        val data = listFollower[position]
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
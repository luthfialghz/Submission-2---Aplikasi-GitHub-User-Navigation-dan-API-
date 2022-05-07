package com.example.githubuserapp.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.ui.adapter.FollowersAdapter
import com.example.githubuserapp.ui.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {
    private val listData: ArrayList<User> = ArrayList()
    private lateinit var adapter: FollowersAdapter
    private lateinit var followerViewModel: FollowersViewModel


    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter(listData)
        followerViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        val dataUser = requireActivity().intent.getParcelableExtra<User>(EXTRA_USER)!!
        config()

        followerViewModel.getDataGit(
            requireActivity().applicationContext,
            dataUser.username.toString()
        )

        if (dataUser.followers == "0") {
            showLoading(false)
            errorMessage(true)
        } else {
            showLoading(true)
        }

        followerViewModel.getListFollower().observe(viewLifecycleOwner) { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        }
    }

    private fun config() {
        binding.recyclerViewFollowers.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewFollowers.setHasFixedSize(true)
        binding.recyclerViewFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun errorMessage(state: Boolean) {
        if (state) {
            binding.cvErrorMessage.visibility = View.VISIBLE
        } else {
            binding.cvErrorMessage.visibility = View.INVISIBLE
        }
    }

    companion object {
        val TAG = FollowersFragment::class.java.simpleName
        const val EXTRA_USER = "extra_user"

    }
}

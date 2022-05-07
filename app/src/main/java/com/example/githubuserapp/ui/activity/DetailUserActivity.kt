package com.example.githubuserapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.User
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.ui.adapter.SectionViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurationActionBar()
        callingData()
        configurationViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        if (menu != null) {
            val item = menu.findItem(R.id.search)
            item.isVisible = false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

//    Declaration Function
    private fun configurationActionBar(){
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail User"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun callingData() {
        showLoading(true)
        showLoading(false)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val image = user.avatar
        nameUser = user.name.toString()
        userName = user.username.toString()
        repository = user.repository.toString()+" Repository"
        followers = user.followers.toString()+" Followers"
        following = user.following.toString()+" Following"
        company = "Bekerja di " + user.company.toString()
        location = "Berlokasi di " + user.location.toString()

        Glide.with(this).load(image).into(binding.ivAvatarReceived)
        binding.tvItemUsername.text = userName
        binding.tvNameReceived.text = nameUser
        binding.tvItemsFollowing.text = following
        binding.tvItemsRepository.text = repository
        binding.tvItemsFollowers.text = followers
        binding.tvItemsCompany.text = company
        binding.tvItemsLocation.text = location
    }

    private fun configurationViewPager() {
        val viewPagerDetail = SectionViewPager(this)
        binding.viewPager.adapter = viewPagerDetail
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    //    Free Function
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loadingProgressUserDetail.visibility = View.VISIBLE
        } else {
            binding.loadingProgressUserDetail.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        private lateinit var nameUser: String
        private lateinit var userName: String
        private lateinit var repository: String
        private lateinit var following: String
        private lateinit var followers: String
        private lateinit var company: String
        private lateinit var location: String
    }
}
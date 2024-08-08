package com.ramaa.suitmedia_test.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramaa.suitmedia_test.R
import com.ramaa.suitmedia_test.databinding.ActivityThirdScreenBinding
import com.ramaa.suitmedia_test.remote.ApiConfig
import com.ramaa.suitmedia_test.ui.adapter.UserAdapter
import kotlinx.coroutines.launch

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var userAdapter: UserAdapter
    private var currentPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userAdapter = UserAdapter(mutableListOf()) { user ->
            val resultIntent = Intent().apply {
                putExtra("SELECTED_USER_NAME", "${user.firstName} ${user.lastName}")
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter

        setupSwipeRefresh()
        setupPagination()

        fetchUsers(currentPage)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            userAdapter.clearUsers()
            fetchUsers(currentPage)
        }
    }

    private fun setupPagination() {
        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    currentPage++
                    fetchUsers(currentPage)
                }
            }
        })
    }

    private fun fetchUsers(page: Int) {
        isLoading = true
        binding.swipeRefreshLayout.isRefreshing = true
        lifecycleScope.launch {
            try {
                val response = ApiConfig.getApiService().getUser(page, 10)
                userAdapter.addUsers(response.data ?: emptyList())
            } catch (e: Exception) {
                Toast.makeText(this@ThirdScreenActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}

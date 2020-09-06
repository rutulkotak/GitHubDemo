package com.rutulkotak.githubdemo.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rutulkotak.githubdemo.R
import com.rutulkotak.githubdemo.data.model.GitHubRepo
import com.rutulkotak.githubdemo.data.remote.Resource
import com.rutulkotak.githubdemo.data.remote.Status
import com.rutulkotak.githubdemo.ui.viewmodel.GitHubViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val gitHubViewModel: GitHubViewModel by viewModel()
    private lateinit var adapter: GitHubRepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private val observer = Observer<Resource<List<GitHubRepo>>> {
        when (it.status) {
            Status.SUCCESS -> updateUI(it.data)
            Status.ERROR -> showError(it.message!!)
            Status.LOADING -> showLoading()
        }
    }

    private fun updateUI(repoList: List<GitHubRepo>?) {
        loading_view.visibility = View.GONE
        list_error.visibility = View.GONE
        gitPostList.visibility = View.VISIBLE
        if (repoList != null) adapter.refresh(repoList)
    }

    private fun showLoading() {
        loading_view.visibility = View.VISIBLE
        gitPostList.visibility = View.GONE
        list_error.visibility = View.GONE
    }

    private fun showError(message: String) {
        gitPostList.visibility = View.GONE
        loading_view.visibility = View.GONE
        list_error.visibility = View.VISIBLE
        list_error.text = message
    }

    private fun init() {
        // Setup adapter
        gitPostList.layoutManager = LinearLayoutManager(this)
        adapter = GitHubRepoListAdapter(arrayListOf())
        gitPostList.adapter = adapter
        // Set Observer
        gitHubViewModel.orgRepos.observe(this, observer)
        // Search for the Org repos
        gitHubViewModel.setOrgGitHubSearchQuery("square")
    }
}
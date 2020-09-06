package com.rutulkotak.githubdemo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.rutulkotak.githubdemo.data.remote.Resource
import com.rutulkotak.githubdemo.data.repository.GitHubRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val gitHubViewModelModule = module {
    factory { GitHubViewModel(get()) }
}

class GitHubViewModel(
    private val repository: GitHubRepository
) : ViewModel() {

    // Organization search
    private val orgSearchQuery = MutableLiveData<String>()
    fun setOrgGitHubSearchQuery(orgName: String) {
        this.orgSearchQuery.value = orgName
    }
    var orgRepos = orgSearchQuery.switchMap { orgName ->
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(repository.getOrgRepos(orgName))
        }
    }

    // User search
    private val userSearchQuery = MutableLiveData<String>()
    fun setUSerGitHubSearchQuery(userName: String) {
        this.userSearchQuery.value = userName
    }
    var userRepos = userSearchQuery.switchMap { userName ->
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(repository.getUserRepos(userName))
        }
    }
}
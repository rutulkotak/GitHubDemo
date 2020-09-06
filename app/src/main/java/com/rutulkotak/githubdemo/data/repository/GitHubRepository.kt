package com.rutulkotak.githubdemo.data.repository

import com.rutulkotak.githubdemo.data.model.GitHubRepo
import com.rutulkotak.githubdemo.data.remote.GitHubService
import com.rutulkotak.githubdemo.data.remote.Resource
import com.rutulkotak.githubdemo.data.remote.ResponseHandler
import org.koin.dsl.module

val gitHubRepoModule = module {
    factory { GitHubRepository(get(), get()) }
}

open class GitHubRepository (
    private val gitHubService: GitHubService,
    private val responseHandler: ResponseHandler
) {
    suspend fun getOrgRepos(orgName: String) : Resource<List<GitHubRepo>> {
        return try {
            val response = gitHubService.getOrgRepos(orgName)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getUserRepos(userName: String) : Resource<List<GitHubRepo>> {
        return try {
            val response = gitHubService.getUserRepos(userName)
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
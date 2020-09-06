package com.rutulkotak.githubdemo.data.remote

import com.rutulkotak.githubdemo.data.model.GitHubRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("orgs/{org}/repos")
    suspend fun getOrgRepos(@Path("org") org: String): List<GitHubRepo>

    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") user: String): List<GitHubRepo>
}
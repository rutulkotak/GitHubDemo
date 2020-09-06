package com.rutulkotak.githubdemo.data.remote

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    factory { ResponseHandler() }
    factory { provideGitHubApi(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideGitHubApi(retrofit: Retrofit): GitHubService = retrofit.create(GitHubService::class.java)

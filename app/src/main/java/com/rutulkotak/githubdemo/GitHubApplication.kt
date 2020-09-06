package com.rutulkotak.githubdemo

import android.app.Application
import com.rutulkotak.githubdemo.data.remote.networkModule
import com.rutulkotak.githubdemo.data.repository.gitHubRepoModule
import com.rutulkotak.githubdemo.ui.viewmodel.gitHubViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@GitHubApplication)
            modules(listOf(networkModule, gitHubRepoModule, gitHubViewModelModule))
        }
    }
}
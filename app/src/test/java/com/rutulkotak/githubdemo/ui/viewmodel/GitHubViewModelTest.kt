package com.rutulkotak.githubdemo.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rutulkotak.githubdemo.data.model.GitHubRepo
import com.rutulkotak.githubdemo.data.remote.Resource
import com.rutulkotak.githubdemo.data.repository.GitHubRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GitHubViewModelTest {

    private lateinit var viewModel: GitHubViewModel
    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var gitHubObserver: Observer<Resource<List<GitHubRepo>>>
    private val validOrgName = "square"
    private val invalidOrgName = "Zzzzzzzz"
    private val successResource = Resource.success(
        listOf(
            GitHubRepo("Project-1", "Description-1"),
            GitHubRepo("Project-2", "Description-2")
        ))
    private val errorResource = Resource.error("Unauthorised", null)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        gitHubRepository = mock()
        runBlocking {
            whenever(gitHubRepository.getOrgRepos(validOrgName)).thenReturn(successResource)
            whenever(gitHubRepository.getOrgRepos(invalidOrgName)).thenReturn(errorResource)
        }
        viewModel = GitHubViewModel(gitHubRepository)
        gitHubObserver = mock()
    }

    @Test
    fun `when orgRepos is called with valid Org name, then observer is updated with success`() = runBlocking {
        viewModel.orgRepos.observeForever(gitHubObserver)
        viewModel.setOrgGitHubSearchQuery(validOrgName)
        delay(10)
        verify(gitHubRepository).getOrgRepos(validOrgName)
        verify(gitHubObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(gitHubObserver, timeout(50)).onChanged(successResource)
        viewModel.orgRepos.removeObserver(gitHubObserver)
    }

    @Test
    fun `when orgRepos is called with invalid Org name, then observer is updated with failure`() = runBlocking {
        viewModel.orgRepos.observeForever(gitHubObserver)
        viewModel.setOrgGitHubSearchQuery(invalidOrgName)
        delay(10)
        verify(gitHubRepository).getOrgRepos(invalidOrgName)
        verify(gitHubObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(gitHubObserver, timeout(50)).onChanged(errorResource)
        viewModel.orgRepos.removeObserver(gitHubObserver)
    }

}
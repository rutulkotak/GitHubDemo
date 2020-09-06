package com.rutulkotak.githubdemo.data.remote

import com.nhaarman.mockitokotlin2.mock
import com.rutulkotak.githubdemo.data.model.GitHubRepo
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

@RunWith(JUnit4::class)
class ResponseHandlerTest {
    lateinit var responseHandler: ResponseHandler

    @Before
    fun setUp() {
        responseHandler = ResponseHandler()
    }

    @Test
    fun `when exception code is 401 then return unauthorised`() {
        val httpException = HttpException(Response.error<GitHubRepo>(401, mock()))
        val result = responseHandler.handleException<GitHubRepo>(httpException)
        Assert.assertEquals("Unauthorised", result.message)
    }

    @Test
    fun `when timeout then return timeout error`() {
        val socketTimeoutException = SocketTimeoutException()
        val result = responseHandler.handleException<GitHubRepo>(socketTimeoutException)
        Assert.assertEquals("Timeout", result.message)
    }
}
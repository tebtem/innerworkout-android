package com.tech.inner_workout.repository
import com.tech.inner_workout.data.api.ApiHelper
import com.tech.inner_workout.data.model.LoginRequest
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() =  apiHelper.getUsers()
    suspend fun loginApi(request: LoginRequest) = apiHelper.loginApi(request)

}
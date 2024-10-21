package com.tech.inner_workout.data.api
import com.tech.inner_workout.data.model.LoginRequest
import com.tech.inner_workout.data.model.LoginResponse
import com.tech.inner_workout.data.model.User
import com.tech.inner_workout.data.model.UserSignUp
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<List<User>>
    suspend fun loginApi(request: LoginRequest): Response<ApiResponse<LoginResponse>>
    suspend fun userSignUp(data: HashMap<String, String>): Response<UserSignUp>
}
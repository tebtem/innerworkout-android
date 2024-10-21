package com.tech.inner_workout.data.api

import com.tech.inner_workout.data.model.LoginRequest
import com.tech.inner_workout.data.model.LoginResponse
import com.tech.inner_workout.data.model.User
import com.tech.inner_workout.data.model.UserSignUp
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
  /*  private fun getTokenFromSPref(): String {
        Log.i("fkdghfhjkjk", "getJob: "+ SharedPrefManager.getToken())
        return "Bearer ${
            SharedPrefManager().getToken()
        }"
    }*/
    override suspend fun getUsers(): Response<List<User>> = apiService.getUsers()
    override suspend fun loginApi(request: LoginRequest): Response<ApiResponse<LoginResponse>> {
        return apiService.loginApi(request)
    }
    override suspend fun userSignUp(data: HashMap<String, String>): Response<UserSignUp> {
        return apiService.userSignUp(data)
    }

}
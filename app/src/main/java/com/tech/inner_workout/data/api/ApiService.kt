package com.tech.inner_workout.data.api
import com.tech.inner_workout.data.model.LoginRequest
import com.tech.inner_workout.data.model.LoginResponse
import com.tech.inner_workout.data.model.User
import com.tech.inner_workout.data.model.UserSignUp
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
    @POST("login")
    suspend fun loginApi(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>


    @Headers("Accept:application/json")
    @FormUrlEncoded
    @POST("register_user")
    suspend fun userSignUp(@FieldMap data: HashMap<String, String>): Response<UserSignUp>

}
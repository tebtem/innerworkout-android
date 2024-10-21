
package com.tech.inner_workout.ui.main.viewmodel

import androidx.lifecycle.*
import com.tech.inner_workout.data.api.ApiHelper
import com.tech.inner_workout.data.model.Data
import com.tech.inner_workout.data.model.LoginRequest
import com.tech.inner_workout.data.model.LoginResponse

import com.tech.inner_workout.data.model.User
import com.tech.inner_workout.repository.MainRepository

import com.tech.inner_workout.ui.base.BaseViewModel
import com.tech.inner_workout.utils.NetworkHelper
import com.tech.inner_workout.utils.Resource
import com.tech.inner_workout.utils.event.NetworkErrorHandler
import com.tech.inner_workout.utils.event.SingleRequestEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val networkErrorHandler: NetworkErrorHandler
) : BaseViewModel() {
    val obrSignUp = SingleRequestEvent<Data>()

     val _users = SingleRequestEvent<List<User>>()
    val observeLogin = SingleRequestEvent<LoginResponse>()

    fun loginUser(request: LoginRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            observeLogin.postValue(Resource.loading(null))
            mainRepository.loginApi(request).let {
                if (it.isSuccessful) {
                    observeLogin.postValue(Resource.success(it.body()?.data))
                } else observeLogin.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun userSignUp(data: HashMap<String, String>) {
        obrSignUp.postValue(Resource.loading(null))
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiHelper.userSignUp(data)
                if (response.isSuccessful && response.body() != null) {
                    obrSignUp.postValue(Resource.success(response.body()?.data))
                } else {
                    obrSignUp.postValue(Resource.error(response.message(), null))
                }
            } catch (e: java.lang.Exception) {
                obrSignUp.postValue(Resource.error(e.message, null))
            }
        }
    }
     fun fetchUsers() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                _users.value = Resource.loading(null)
                mainRepository.getUsers().let {
                    if (it.isSuccessful) {

                        _users.postValue(Resource.success(it.body()))
                    } else {

                        _users.postValue(
                            Resource.error(
                                networkErrorHandler.getErrorMessage(it.errorBody()), null
                            )
                        )
                    }
                }
            }
        }
    }
}
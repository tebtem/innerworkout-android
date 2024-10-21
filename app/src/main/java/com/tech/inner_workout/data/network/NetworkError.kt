
package com.tech.inner_workout.data.network
class NetworkError(val errorCode: Int, override val message: String?) : Throwable(message)

package com.tech.inner_workout.data.local
import android.content.SharedPreferences
import com.tech.inner_workout.data.model.LoginRequest
import com.tech.inner_workout.utils.getValue
import com.tech.inner_workout.utils.saveValue
import com.google.gson.Gson

import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    object KEY {
        const val USER = "user"
        const val USER_ID = "user_id"
        const val BEARER_TOKEN = "bearer_token"
        const val PROFILE_COMPLETED = "profile_completed"
        const val APPEARANCE_KEY = "appearance_key"
        const val LOCALE = "locale_key"
        const val TODAY_RECORD = "today_record"
        const val TODAY = "today"
        const val ANS = "ans"
        const val IS_FIRST = "is_first"
        const val IS_FIRST_HOME = "is_first_home"
        const val IS_FIRST_ESTIMATE = "is_first_estimate"
    }

    fun saveIsFirst(isFirst:Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY.IS_FIRST, isFirst)
        editor.apply()
    }
    fun getIsFirst():Boolean?{
        return sharedPreferences.getValue(KEY.IS_FIRST, false)
    }
    fun saveIsFirstEstimate(isFirst:Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY.IS_FIRST, isFirst)
        editor.apply()
    }
    fun getIsFirstEstimate():Boolean?{
        return sharedPreferences.getValue(KEY.IS_FIRST, false)
    }
    fun saveIsFirstHome(isFirst:Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY.IS_FIRST_HOME, isFirst)
        editor.apply()
    }
    fun getIsFirstHome ():Boolean?{
        return sharedPreferences.getValue(KEY.IS_FIRST_HOME, false)
    }
   fun saveUser(bean: LoginRequest) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY.USER, Gson().toJson(bean))
        editor.apply()
    }

    fun getCurrentUser(): LoginRequest? {
        val s: String? = sharedPreferences.getString(KEY.USER, null)
        return Gson().fromJson(s, LoginRequest::class.java)
    }

    fun saveUserId(userId: String) {
        sharedPreferences.saveValue(KEY.USER_ID, userId)
    }

    fun getCurrentUserId(): String? {
        return sharedPreferences.getValue(KEY.USER_ID, null)
    }

    fun saveStatus(userId: String) {
        sharedPreferences.saveValue(KEY.BEARER_TOKEN, userId)
    }

    fun getStatus(): String? {
        return sharedPreferences.getValue(KEY.BEARER_TOKEN, "Open")
    }

    fun profileCompleted(isProfile: Boolean) {
        sharedPreferences.saveValue(KEY.PROFILE_COMPLETED, isProfile)
    }

    fun isProfileCompleted(): Boolean? {
        return sharedPreferences.getValue(KEY.PROFILE_COMPLETED, false)
    }

    fun setAppearance(type: Int) {
        sharedPreferences.saveValue(KEY.APPEARANCE_KEY, type)
    }

    fun getAppearance(): Int {
        return sharedPreferences.getInt(KEY.APPEARANCE_KEY, 0)
    }

    fun setLocaleType(type: String?) {
        sharedPreferences.saveValue(KEY.LOCALE, type)
    }

    fun getLocaleType(): String? {
        return sharedPreferences.getString(KEY.LOCALE, "en")
    }



    fun getToday(): Int {
        return sharedPreferences.getInt(KEY.TODAY, 0)
    }

    fun setToday(type: Int?) {
        sharedPreferences.saveValue(KEY.TODAY, type)
    }

    fun ansToday(): Int {
        return sharedPreferences.getInt(KEY.ANS, 0)
    }

    fun setAnsToday(type: Int?) {
        sharedPreferences.saveValue(KEY.ANS, type)
    }

   /* fun getToken(): String {
        return getCurrentUser()?.token?.let { token ->
            "Bearer $token"
        }.toString()
    }*/

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
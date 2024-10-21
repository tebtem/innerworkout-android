package com.tech.inner_workout.ui.base


import android.view.View
import androidx.lifecycle.ViewModel
import com.tech.inner_workout.utils.event.SingleActionEvent

open class BaseViewModel : ViewModel() {

    val onClick: SingleActionEvent<View?> = SingleActionEvent()

    override fun onCleared() {
        super.onCleared()
    }

    open fun onClick(view: View?) {
        onClick.value = view
    }
}
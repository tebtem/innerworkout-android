package com.tech.inner_workout.ui.home.fragments.lock

import android.view.View
import androidx.fragment.app.viewModels
import com.tech.inner_workout.R

import com.tech.inner_workout.databinding.FragmentLockSecondBinding
import com.tech.inner_workout.ui.base.BaseFragment
import com.tech.inner_workout.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockSecondFragment :BaseFragment<FragmentLockSecondBinding>(){
    private val viewModel:LockVM by viewModels()
    override fun onCreateView(view: View) {

    }

    override fun getLayoutResource(): Int {
       return R.layout.fragment_lock_second
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }
}
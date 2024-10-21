package com.tech.inner_workout.ui.settings.data

import androidx.activity.viewModels
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.ActivityYourDataBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YourDataActivity  : BaseActivity<ActivityYourDataBinding>() {
    private val viewModel: YourDataVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_your_data
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        initClick()
    }

    private fun initClick() {
        viewModel.onClick.observe(this){
            when(it?.id){

                R.id.ivBack->{
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

}
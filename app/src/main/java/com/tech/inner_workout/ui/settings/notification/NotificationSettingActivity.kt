package com.tech.inner_workout.ui.settings.notification


import androidx.activity.viewModels
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.ActivityNotifcationSettingBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationSettingActivity  : BaseActivity<ActivityNotifcationSettingBinding>() {
    private val viewModel: NotificationSettingVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_notifcation_setting
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
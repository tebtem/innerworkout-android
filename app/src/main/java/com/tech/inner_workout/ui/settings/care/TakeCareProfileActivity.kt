package com.tech.inner_workout.ui.settings.care


import androidx.activity.viewModels
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.ActivityTakeCareProfileBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TakeCareProfileActivity : BaseActivity<ActivityTakeCareProfileBinding>() {
    private val viewModel: TakeCareVM by viewModels()
    override fun getLayoutResource(): Int {
        return R.layout.activity_take_care_profile
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        initClick()
    }

    private fun initClick() {
        viewModel.onClick.observe(this) {
            when (it?.id) {
                R.id.ivBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }
}
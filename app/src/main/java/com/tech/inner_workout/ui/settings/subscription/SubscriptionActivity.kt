package com.tech.inner_workout.ui.settings.subscription

import androidx.activity.viewModels
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.ActivitySubscriptionBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionActivity : BaseActivity<ActivitySubscriptionBinding>() {
    private val viewModel: SubscriptionVM by viewModels()

    override fun getLayoutResource(): Int {
        return R.layout.activity_subscription
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
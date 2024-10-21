package com.tech.inner_workout.ui.settings

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.ActivitySettingsBinding
import com.tech.inner_workout.databinding.SheetLogoutBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import com.tech.inner_workout.ui.settings.care.TakeCareProfileActivity
import com.tech.inner_workout.ui.settings.customer.CustomerSupportActivity
import com.tech.inner_workout.ui.settings.customer.CustomerSupportVM
import com.tech.inner_workout.ui.settings.data.YourDataActivity
import com.tech.inner_workout.ui.settings.notification.NotificationSettingActivity
import com.tech.inner_workout.ui.settings.reflections.ReflectionsActivity
import com.tech.inner_workout.ui.settings.subscription.SubscriptionActivity
import com.tech.inner_workout.utils.BaseCustomBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {
    private val viewModel: SettingsVM by viewModels()
    private lateinit var logoutSheet: BaseCustomBottomSheet<SheetLogoutBinding>

    override fun getLayoutResource(): Int {
        return R.layout.activity_settings
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        initClick()
        initBottomSheet()
    }

    private fun initBottomSheet() {
        logoutSheet = BaseCustomBottomSheet(
            this,
            R.layout.sheet_logout
        ) { view ->
            when (view?.id) {
                R.id.btnCancel -> {
                    logoutSheet.dismiss()
                }
            }
        }
    }

    private fun initClick() {
        viewModel.onClick.observe(this) {
            when (it?.id) {
                R.id.tvSubscriptionMenu -> {
                    startActivity(Intent(this, SubscriptionActivity::class.java))
                }

                R.id.tvNotificationsMenu -> {
                    startActivity(Intent(this, NotificationSettingActivity::class.java))
                }

                R.id.tvYourData -> {
                    startActivity(Intent(this, YourDataActivity::class.java))
                }

                R.id.tvReflectionsMenu -> {
                    startActivity(Intent(this, ReflectionsActivity::class.java))
                }

                R.id.tvCustomerSupport -> {
                    startActivity(Intent(this, CustomerSupportActivity::class.java))
                }

                R.id.tvTakeCareProfile -> {
                    startActivity(Intent(this, TakeCareProfileActivity::class.java))
                }

                R.id.ivBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
                R.id.tvShareWithFriends->{
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }

                R.id.clSignOut -> {
                    logoutSheet.show()
                }
            }
        }
    }

}
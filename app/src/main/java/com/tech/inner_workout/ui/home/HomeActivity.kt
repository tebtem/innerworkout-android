package com.tech.inner_workout.ui.home


import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.ActivityHomeBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import com.tech.inner_workout.ui.home.fragments.TodayFragment
import com.tech.inner_workout.ui.home.fragments.lock.LockFragment
import com.tech.inner_workout.ui.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val viewModel: HomeVM by viewModels()
    override fun getLayoutResource(): Int {
        return R.layout.activity_home
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        changeFragment(TodayFragment())
        initClick()
        initBottomSheet()
    }

    private fun initBottomSheet() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.today -> {
                    changeFragment(TodayFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.journey -> {
                    changeFragment(TodayFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.explore -> {
                    changeFragment(TodayFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.lock -> {
                    changeFragment(LockFragment())
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    private fun initClick() {
        viewModel.onClick.observe(this) {
            when (it?.id) {
                R.id.ivSettings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flHome, fragment)
            commit()
        }

}
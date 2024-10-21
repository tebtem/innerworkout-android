package com.tech.inner_workout.ui.home.fragments.lock

import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.FragmentLockBinding
import com.tech.inner_workout.databinding.SheetLockSessionBinding
import com.tech.inner_workout.databinding.SheetModeBinding
import com.tech.inner_workout.ui.base.BaseFragment
import com.tech.inner_workout.ui.base.BaseViewModel
import com.tech.inner_workout.ui.home.fragments.TodayFragment
import com.tech.inner_workout.utils.BaseCustomBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockFragment : BaseFragment<FragmentLockBinding>(){

    private  val viewModel :LockVM by viewModels()
    private lateinit var lockPagerAdapter:LockPagerAdapter
    private lateinit var lockSheet: BaseCustomBottomSheet<SheetLockSessionBinding>
    private val tabArray = arrayOf(
        "One-off",
        "Scheduled"

    )
    private var isOne= false
    override fun onCreateView(view: View) {
        initViewPagerAdapter()
        initLockSheet()
        initClick()
    }

    private fun initClick() {
        viewModel.onClick.observe(viewLifecycleOwner){
            when(it?.id){
                R.id.tvChangebtn->{
                    lockSheet.show()
                }
            }
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_lock
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }
    private fun initViewPagerAdapter() {
        lockPagerAdapter = LockPagerAdapter(parentFragmentManager, lifecycle)
        lockPagerAdapter.addFragment(LockFirstFragment())
        lockPagerAdapter.addFragment(LockSecondFragment())
        binding.viewPager.adapter = lockPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()
    }
    private fun initLockSheet() {
        lockSheet = BaseCustomBottomSheet(
            requireContext(),
            R.layout.sheet_lock_session
        ) {

            when (it?.id) {
                R.id.ivBack -> {
                    lockSheet.dismiss()
                }

                R.id.tvChangebtn -> {

                }

                R.id.clOneSession -> {
                    isOne = true
                    lockSheet.binding.clOneSession.setBackgroundResource(R.drawable.selected_blue)
                    lockSheet.binding.clSechSession.setBackgroundResource(R.drawable.blue_button)
                }

                R.id.clSechSession -> {
                    isOne = false
                    lockSheet.binding.clSechSession.setBackgroundResource(R.drawable.selected_blue)
                    lockSheet.binding.clOneSession.setBackgroundResource(R.drawable.blue_button)
                }
            }
        }
    }


}
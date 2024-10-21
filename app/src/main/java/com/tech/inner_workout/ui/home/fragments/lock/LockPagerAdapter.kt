package com.tech.inner_workout.ui.home.fragments.lock

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tech.inner_workout.ui.home.fragments.TodayFragment


class LockPagerAdapter(fragmentManager:FragmentManager, lifecycle: Lifecycle)  : FragmentStateAdapter(fragmentManager, lifecycle) {
    private val arrayList = ArrayList<Fragment>()
    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun addFragment(fragment: Fragment?) {
        arrayList.add(fragment!!)
    }
    fun getPAgeTitle(position: Int): CharSequence? {
        var title: String? = null
        when(position)
        {
            0 -> {title = "Active Subscription"}
            1 -> {title = "Inactive Subscription"}

        }

        return title!!
    }
    override fun createFragment(position: Int): Fragment {
        return  return arrayList[position]
    }
}

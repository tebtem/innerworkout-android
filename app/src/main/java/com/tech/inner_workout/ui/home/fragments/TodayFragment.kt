package com.tech.inner_workout.ui.home.fragments

import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import com.tech.inner_workout.R
import com.tech.inner_workout.data.model.DayToday
import com.tech.inner_workout.data.model.FavDay
import com.tech.inner_workout.data.model.ProgressToday
import com.tech.inner_workout.databinding.FragmentTodayBinding
import com.tech.inner_workout.databinding.ItemBreathBinding
import com.tech.inner_workout.databinding.ItemDateBinding
import com.tech.inner_workout.databinding.ItemFavBinding
import com.tech.inner_workout.databinding.ItemProgressBinding
import com.tech.inner_workout.ui.base.BaseFragment
import com.tech.inner_workout.ui.base.BaseViewModel
import com.tech.inner_workout.ui.base.SimpleRecyclerViewAdapter

class TodayFragment :BaseFragment<FragmentTodayBinding>() {

    private val viewModel :TodayVM by viewModels()
    private  lateinit var  dayAdapter :SimpleRecyclerViewAdapter<DayToday, ItemDateBinding >
    private  lateinit var  progressAdapter :SimpleRecyclerViewAdapter<ProgressToday, ItemProgressBinding >
    private  lateinit var  breathAdapter :SimpleRecyclerViewAdapter<FavDay, ItemBreathBinding>
    private  lateinit var  favAdapter :SimpleRecyclerViewAdapter<FavDay, ItemFavBinding>
    private val dayList :ArrayList<DayToday> = ArrayList()
    private val progressList :ArrayList<ProgressToday> = ArrayList()
    private val favList :ArrayList<FavDay> = ArrayList()
    override fun onCreateView(view: View) {
        initDayList()
        initProgressList()
        initFavList()
        initDayAdapter()
        initProgressAdapter()
        initBreathAdapter()
        initFavAdapter()
        initClick()
    }

    private fun initClick() {
        viewModel.onClick.observe(viewLifecycleOwner){
            when(it?.id){
                R.id.ivArrow->{
                    binding.clProgress.visibility = View.GONE
                }
            }
        }
    }

    private fun initFavAdapter() {
       favAdapter = SimpleRecyclerViewAdapter(R.layout.item_fav,BR.bean){ v, m , pos->

       }
        favAdapter.list = favList
        binding.rvFav.adapter = favAdapter
    }

    private fun initBreathAdapter() {
        breathAdapter = SimpleRecyclerViewAdapter(R.layout.item_breath, BR.bean) { v, m, pos ->

        }
        breathAdapter.list= favList
        binding.rvBreath.adapter = breathAdapter
    }

    private fun initFavList() {
        favList.add(FavDay("Text with available audio", "5 min", R.drawable.journey))
        favList.add(FavDay("Text with available audio", "5 min", R.drawable.journey))
        favList.add(FavDay("Text with available audio", "5 min", R.drawable.journey))
        favList.add(FavDay("Text with available audio", "5 min", R.drawable.journey))
    }

    private fun initProgressList() {
        progressList.add(ProgressToday("Create an account",true))
        progressList.add(ProgressToday("Complete your first reflection",true))
        progressList.add(ProgressToday("Schedule self-care check-ins",false))
        progressList.add(ProgressToday("Set your screen time boundaries",true))
        progressList.add(ProgressToday("Redeem your free trial",false))
    }

    private fun initProgressAdapter() {
        progressAdapter = SimpleRecyclerViewAdapter(R.layout.item_progress, BR.bean){v, m , pos->

        }
        binding.rvProgress.adapter = progressAdapter
        progressAdapter.list = progressList
    }

    private fun initDayList() {
        dayList.add(DayToday("Mon", "4", isToday = true, isBlack = true))
        dayList.add(DayToday("Tue", "5", isToday = false, isBlack = false))
        dayList.add(DayToday("Wed", "6", isToday = false, isBlack = true))
        dayList.add(DayToday("Thu", "7",  isToday = false, isBlack = false))
        dayList.add(DayToday("Fri", "8",  isToday = false, isBlack = true))
        dayList.add(DayToday("Sat", "9",  isToday = false, isBlack = false))
        dayList.add(DayToday("Sun", "9",  isToday = false, isBlack = false))
        dayList.add(DayToday("Mon", "10", isToday = false, isBlack = true))
        dayList.add(DayToday("Tue", "11", isToday = false, isBlack = false))
        dayList.add(DayToday("Wed", "12", isToday = false, isBlack = true))
        dayList.add(DayToday("Thu", "13",  isToday = false, isBlack = false))
        dayList.add(DayToday("Fri", "14",  isToday = false, isBlack = true))
        dayList.add(DayToday("Sat", "15",  isToday = false, isBlack = false))
        dayList.add(DayToday("Sun", "15",  isToday = false, isBlack = false))
    }

    private fun initDayAdapter() {
        dayAdapter = SimpleRecyclerViewAdapter(R.layout.item_date, BR.bean){v ,m ,pos->

        }
        binding.rvDay.adapter = dayAdapter
        dayAdapter.list = dayList
        binding.rvDay.setItemViewCacheSize(dayList.size)
    }

    override fun getLayoutResource(): Int {
       return R.layout.fragment_today
    }

    override fun getViewModel(): BaseViewModel {
       return viewModel
    }

}
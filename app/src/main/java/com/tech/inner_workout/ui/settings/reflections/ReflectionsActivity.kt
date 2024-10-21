package com.tech.inner_workout.ui.settings.reflections


import androidx.activity.viewModels
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.ActivityReflectionsBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReflectionsActivity :BaseActivity<ActivityReflectionsBinding>(){
  private  val viewModel :ReflectionsVM by viewModels()
    override fun getLayoutResource(): Int {
        return R.layout.activity_reflections
    }

    override fun getViewModel(): BaseViewModel {
       return  viewModel
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
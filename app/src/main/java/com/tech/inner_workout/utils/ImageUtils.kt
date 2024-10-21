package com.tech.inner_workout.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.tech.inner_workout.R
import com.tech.inner_workout.data.model.DayToday

object ImageUtils {

    @BindingAdapter("imgUrl")
    @JvmStatic
    fun setProfilePicture(imageView: ImageView, imgUrl: String?) {
//        Glide.with(imageView.getContext()).load("https://dbt.teb.mybluehostin.me/laravel/storage/app/public/"+imgUrl).into(imageView)
//        Glide.with(imageView.getContext()).load("https://dbt.teb.mybluehostin.me/laravel/storage/app/public/"+imgUrl).into(imageView)
    }

    @BindingAdapter("setDateDrawable")
    @JvmStatic
    fun setDateDrawable(constraintLayout: ConstraintLayout, isToday: Boolean) {
        if(isToday){
            constraintLayout.setBackgroundResource(R.drawable.day_button)
        }
    }
    @BindingAdapter("setTextColor")
    @JvmStatic
    fun setTextColor(textView: AppCompatTextView, bean:DayToday) {
        if(bean.isToday){
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.ocean))
        }
      else  if(!bean.isBlack){
           textView.setTextColor(ContextCompat.getColor(textView.context, R.color.setting_title))
        }
    }

}
package com.tech.inner_workout.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tech.inner_workout.databinding.ViewProgressSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tech.inner_workout.BR

class ProgressSheet(val callback: BaseCallback) : BottomSheetDialogFragment() {
    private lateinit var binding: ViewProgressSheetBinding

    companion object {
        const val TAG = "ProgressSheet"

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewProgressSheetBinding.inflate(inflater)
        binding.setVariable(BR.callback, callback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callback.onBind(binding)
    }

    fun showMessage(s: String?) {
        binding.tvMessage.text = s
    }

    interface BaseCallback {
         fun onClick(view: View?)
        fun onBind(bind: ViewProgressSheetBinding)
    }

}
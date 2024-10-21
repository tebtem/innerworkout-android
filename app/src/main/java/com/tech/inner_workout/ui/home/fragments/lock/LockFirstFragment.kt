package com.tech.inner_workout.ui.home.fragments.lock

import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tech.inner_workout.R
import com.tech.inner_workout.data.model.ProgressToday
import com.tech.inner_workout.databinding.DailogNameSureBinding
import com.tech.inner_workout.databinding.DialogSureBinding
import com.tech.inner_workout.databinding.FragmentLockFirstBinding
import com.tech.inner_workout.databinding.ItemAppBinding
import com.tech.inner_workout.databinding.ItemAppSelectBinding
import com.tech.inner_workout.databinding.SheetAppBinding
import com.tech.inner_workout.databinding.SheetBreakBinding
import com.tech.inner_workout.databinding.SheetMinuteBinding
import com.tech.inner_workout.databinding.SheetModeBinding
import com.tech.inner_workout.ui.base.BaseFragment
import com.tech.inner_workout.ui.base.BaseViewModel
import com.tech.inner_workout.ui.base.SimpleRecyclerViewAdapter
import com.tech.inner_workout.utils.BaseCustomBottomSheet
import com.tech.inner_workout.utils.BaseCustomDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.meta.When
import kotlin.math.min

@AndroidEntryPoint
class LockFirstFragment : BaseFragment<FragmentLockFirstBinding>() {
    private val viewModel: LockVM by viewModels()
    private lateinit var appSheet: BaseCustomBottomSheet<SheetAppBinding>
    private lateinit var modeSheet: BaseCustomBottomSheet<SheetModeBinding>
    private lateinit var timeSheet: BaseCustomBottomSheet<SheetMinuteBinding>
    private lateinit var breakSheet: BaseCustomBottomSheet<SheetBreakBinding>
    private lateinit var sureDialog: BaseCustomDialog<DialogSureBinding>
    private lateinit var nameDialog: BaseCustomDialog<DailogNameSureBinding>
    private lateinit var itemApp: SimpleRecyclerViewAdapter<ProgressToday, ItemAppBinding>
    private lateinit var itemAppSelect: SimpleRecyclerViewAdapter<ProgressToday, ItemAppSelectBinding>
    private var isGentle = true
    private var appData: ArrayList<ProgressToday> = ArrayList()
    override fun onCreateView(view: View) {
        initClick()
        initAppSheet()
        initModeSheet()
        initTimeSheet()
        initBreakSheet()
        initSureDialog()
        initNameDialog()
    }

    private fun initSureDialog() {
        sureDialog = BaseCustomDialog(requireContext(), R.layout.dialog_sure) {
            when (it?.id) {
                R.id.ivBack -> {
                    sureDialog.dismiss()
                }

                R.id.tvSkip, R.id.viewFirst -> {
                    sureDialog.dismiss()
                    appSheet.dismiss()
                }
                R.id.tvYes -> {
                    sureDialog.dismiss()
                    nameDialog.show()

                }
            }
        }
    }
    private fun initNameDialog() {
        nameDialog = BaseCustomDialog(requireContext(), R.layout.dailog_name_sure) {
            when (it?.id) {
                R.id.ivBack -> {
                    nameDialog.dismiss()
                }

                R.id.tvSkip, R.id.viewFirst -> {
                    nameDialog.dismiss()
                    appSheet.dismiss()

                }
                R.id.tvYes -> {
                    nameDialog.dismiss()
                    appSheet.dismiss()
                    binding.tvAppsChoose.text = nameDialog.binding.etName.text
                    binding.tvAppsChoose.setBackgroundResource(R.drawable.blue_corner)

                }
            }
        }
    }

    private fun initTimeSheet() {
        timeSheet = BaseCustomBottomSheet(
            requireContext(),
            R.layout.sheet_minute
        ) {
            when (it?.id) {
                R.id.ivBack -> {
                    timeSheet.dismiss()
                }

                R.id.tvChangebtn -> {
                    binding.tvDurationChoose.text =
                        timeSheet.binding.numberPicker.value.toString() + " hours " + timeSheet.binding.numberPickerMin.value.toString() + " minutes"
                    binding.tvDurationChoose.setBackgroundResource(R.drawable.blue_corner)
                    timeSheet.dismiss()
                }
            }
        }
        initTimeDateData()
    }

    private fun initBreakSheet() {
        breakSheet = BaseCustomBottomSheet(
            requireContext(),
            R.layout.sheet_break
        ) {
            when (it?.id) {
                R.id.ivBack -> {
                    breakSheet.dismiss()
                }

                R.id.tvChangebtn -> {
                    binding.tvBreakChoose.text =
                        breakSheet.binding.numberPicker.value.toString() + " min"
                    binding.tvBreakChoose.setBackgroundResource(R.drawable.blue_corner)
                    breakSheet.dismiss()
                }
            }
        }
        initBreakDateData()
    }

    private fun initBreakDateData() {
        val min = Array(60) { it.toString() }
        breakSheet.binding.numberPicker.minValue = 0
        breakSheet.binding.numberPicker.maxValue = min.size - 1
        breakSheet.binding.numberPicker.displayedValues = min
    }

    private fun initTimeDateData() {
        val hours = Array(24) { it.toString() }
        val minutes = Array(60) { it.toString() }
        timeSheet.binding.numberPicker.minValue = 0
        timeSheet.binding.numberPicker.maxValue = hours.size - 1
        timeSheet.binding.numberPicker.displayedValues = hours
        timeSheet.binding.numberPickerMin.minValue = 0
        timeSheet.binding.numberPickerMin.maxValue = minutes.size - 1
        timeSheet.binding.numberPickerMin.displayedValues = minutes
    }

    private fun initAppSheet() {
        appSheet = BaseCustomBottomSheet(
            requireContext(),
            R.layout.sheet_app
        ) {
            when (it?.id) {
                R.id.ivBack -> {
                    appSheet.dismiss()
                }

                R.id.tvChangebtn -> {
                    if (appSheet.binding.tvChangebtn.text != "Confirm") {
                        appSheet.binding.tvChangebtn.text = "Confirm"
                        appSheet.binding.clChooseApp.visibility = View.VISIBLE
                        appSheet.binding.clGroup.visibility = View.GONE
                    } else {
                        sureDialog.show()
                    }
                }
            }
        }
        initAppData()
        initAppAdapter()
        initAppSelectAdapter()
    }

    private fun initAppData() {
        appData.add(
            ProgressToday(
                "My group (15 apps)",
                true,
                "TikTok, YouTube, Telegram and 12 more"
            )
        )
        appData.add(
            ProgressToday(
                "My group (15 apps)",
                false,
                "TikTok, YouTube, Telegram and 12 more"
            )
        )
        appData.add(
            ProgressToday(
                "My group (15 apps)",
                false,
                "TikTok, YouTube, Telegram and 12 more"
            )
        )
        appData.add(
            ProgressToday(
                "My group (15 apps)",
                false,
                "TikTok, YouTube, Telegram and 12 more"
            )
        )
    }

    private fun initAppAdapter() {
        itemApp = SimpleRecyclerViewAdapter(R.layout.item_app, BR.bean) { v, m, pos ->

        }
        appSheet.binding.clGroup.adapter = itemApp
        itemApp.list = appData
    }

    private fun initAppSelectAdapter() {
        itemAppSelect = SimpleRecyclerViewAdapter(R.layout.item_app_select, BR.bean) { v, m, pos ->
            when (v?.id) {
                R.id.ivCheck -> {
                    m.checked = !m.checked!!
                    itemAppSelect.notifyItemChanged(pos)
                }
            }
        }
        appSheet.binding.rvApp.adapter = itemAppSelect
        itemAppSelect.list = appData
    }

    private fun initModeSheet() {
        modeSheet = BaseCustomBottomSheet(
            requireContext(),
            R.layout.sheet_mode
        ) {

            when (it?.id) {
                R.id.ivBack -> {
                    modeSheet.dismiss()
                }

                R.id.tvChangebtn -> {
                    if (isGentle) {
                        binding.tvModeChoose.text = "Gentle"
                        binding.tvModeChoose.setBackgroundResource(R.drawable.blue_corner)
                    } else {
                        binding.tvModeChoose.text = "Full"
                        binding.tvModeChoose.setBackgroundResource(R.drawable.blue_corner)
                    }
                    modeSheet.dismiss()
                }

                R.id.clGentle -> {
                    isGentle = true
                    modeSheet.binding.clGentle.setBackgroundResource(R.drawable.selected_blue)
                    modeSheet.binding.clAccount.setBackgroundResource(R.drawable.blue_button)
                }

                R.id.clAccount -> {
                    isGentle = false
                    modeSheet.binding.clAccount.setBackgroundResource(R.drawable.selected_blue)
                    modeSheet.binding.clGentle.setBackgroundResource(R.drawable.blue_button)
                }
            }
        }
    }

    private fun initClick() {
        viewModel.onClick.observe(viewLifecycleOwner) {
            when (it?.id) {
                R.id.tvAppsChoose -> {
                    appSheet.show()
                }

                R.id.tvModeChoose -> {
                    modeSheet.show()
                }

                R.id.tvDurationChoose -> {
                    timeSheet.show()
                }

                R.id.tvBreakChoose -> {
                    breakSheet.show()
                }
            }
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_lock_first
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }


}
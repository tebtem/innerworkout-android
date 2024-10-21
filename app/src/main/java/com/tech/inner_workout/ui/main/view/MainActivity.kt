package com.tech.inner_workout.ui.main.view
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tech.inner_workout.BR
import com.tech.inner_workout.R
import com.tech.inner_workout.data.model.LoginRequest
import com.tech.inner_workout.data.model.User
import com.tech.inner_workout.databinding.ActivityMainBinding
import com.tech.inner_workout.databinding.BsOtherOptionsBinding
import com.tech.inner_workout.databinding.EditAddOnsPopupBinding
import com.tech.inner_workout.databinding.ItemLayoutBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import com.tech.inner_workout.ui.base.SimpleRecyclerViewAdapter
import com.tech.inner_workout.ui.base.location.LocationHandler
import com.tech.inner_workout.ui.base.location.LocationResultListener
import com.tech.inner_workout.ui.base.permission.PermissionHandler
import com.tech.inner_workout.ui.base.permission.Permissions
import com.tech.inner_workout.ui.main.viewmodel.MainViewModel
import com.tech.inner_workout.utils.BaseCustomBottomSheet
import com.tech.inner_workout.utils.BaseCustomDialog
import com.tech.inner_workout.utils.Status
import com.tech.inner_workout.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), BaseCustomDialog.Listener,
    LocationResultListener {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapterSimple: SimpleRecyclerViewAdapter<User, ItemLayoutBinding>
    private var locationHandler: LocationHandler? = null
    private var mCurrentLocation: Location? = null
    private lateinit var popup: BaseCustomDialog<EditAddOnsPopupBinding>
    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): BaseViewModel {
        return mainViewModel
    }

    private lateinit var otherOptionBs: BaseCustomBottomSheet<BsOtherOptionsBinding>

    override fun onCreateView() {
        mainViewModel.fetchUsers()
     //   selectImage()
        createLocationHandler()


        /**
        DilogBoxPop()
        selectImage()
        CustomBottomSheet()
        postRequestRawBodyLogin()
        postRequestFormData()

         */

        setupUI()
        setupObserver()
    }

    private fun CustomBottomSheet() {
        otherOptionBs = BaseCustomBottomSheet(
            this,
            R.layout.bs_other_options,
            ""
        ) {
            when (it.id) {
                R.id.ivClose -> {
                    otherOptionBs.cancel()
                }
                R.id.tvCancel -> {

                }
                R.id.tvCallRestaurant -> {

                }
                R.id.tvViewMenu -> {

                    otherOptionBs.cancel()
                }
                R.id.tvMapView -> {

                }
            }
        }
        otherOptionBs.show()
        otherOptionBs.setCancelable(false)
    }

    private fun DilogBoxPop() {
        popup = BaseCustomDialog<EditAddOnsPopupBinding>(
            this, R.layout.edit_add_ons_popup, this
        )
        popup.show()
        popup.setCanceledOnTouchOutside(false)
    }

    private fun createLocationHandler() {
        locationHandler = LocationHandler(this, this)
        locationHandler?.getUserLocation()
        locationHandler?.removeLocationUpdates()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun selectImage() {
//        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
        Permissions.check(this, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,

            Manifest.permission.CAMERA
        ), 0, Permissions.Options(),
            object : PermissionHandler() {
                @RequiresApi(Build.VERSION_CODES.R)
                override fun onGranted() {

                    try {
                        if (Environment.isExternalStorageManager()) {

//                            showDialogForSkill()
                        } else {
                            //request for the permission
                            val intent =
                                Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                    } catch (e: NoSuchMethodError) {
//                        showDialogForSkill()
                    } catch (e: Exception) {
//                        showDialogForSkill()
                    }


//                ImagePicker.with(this@ChatActivity).crop().start()
                }
            })
    }

    private fun postRequestFormData() {


        val data = HashMap<String, String>()
        data["name"] = "fjhgfh"
        data["email"] = "fsdfddsfdg@ujhh.com"
        data["password"] = "Aa1bgs23sddsf456@"
        data["country_code"] = "91"
        data["phone_number"] = "1794569872"
        data["country_id"] = "1"
        data["state_id"] = "1"
        data["city_id"] = "1"
        data["zipcode"] = "123456"
        data["user_role"] = "1"  // 1 for user
        data["device_type"] = "2"  //  2 for Android
        data["device_token"] = "1234"
        mainViewModel.userSignUp(data)
    }

    private fun postRequestRawBodyLogin() {
        val request = LoginRequest(
            "jon@yopmail.com",
            "12345678",
            "1",
            "jkhjkhjkthhhuihhhreththrehtjkrhtjkhrejkthkjrhtkjer",

            )
        mainViewModel.loginUser(request)
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun setupObserver() {
        showLoading("")
        mainViewModel.obrSignUp.observe(this, Observer {
            when (it?.status) {
                Status.LOADING -> {
                    showLoading("")
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Log.i("jkbhdsbhhks", "setupLoginData: ${it.data?.name}")

                }
                Status.ERROR -> {
                    hideLoading()
                    Log.i("jkbhdsbhhks", "setupLoginData: ${it.message.toString()}")
                    showErrorToast(it.message.toString())
                }
                else -> {}
            }
        })

        mainViewModel.observeLogin.observe(this@MainActivity, Observer {
            when (it?.status) {
                Status.LOADING -> {
                    showLoading("")
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Log.i("jkbhdsbhhks", "setupLoginData: ${it.data}")
                }
                Status.ERROR -> {
                    hideLoading()
                    showErrorToast(it.message.toString())
                }
                else -> {

                }
            }
        })
        mainViewModel._users.observe(this) {
            when (it!!.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    it!!.data?.let { users -> renderList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(users: List<User>) {
        for (element in users) {
            Log.i("jfhgjkhrtjkj", "renderList: " + element.name)
        }
        adapterSimple = SimpleRecyclerViewAdapter(R.layout.item_layout, BR.bean) { v, m, pos ->
            when (v.id) {
                R.id.container -> {
                    adapterSimple.removeItem(pos)

                }
            }
        }
        binding.recyclerView.adapter = adapterSimple
        adapterSimple.list = users
        adapterSimple.notifyDataSetChanged()

    }

    override fun onViewClick(view: View?) {

    }

    override fun getLocation(location: Location) {
        Log.i("location.latitude", "getLocation: ${location.latitude}")
    }

}

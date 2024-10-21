package com.tech.inner_workout.ui.welcome

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.PointF
import android.location.Location
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import com.tech.inner_workout.R
import com.tech.inner_workout.databinding.ActivityWelcomeBinding
import com.tech.inner_workout.ui.base.BaseActivity
import com.tech.inner_workout.ui.base.BaseViewModel
import com.tech.inner_workout.ui.base.ProgressSheet.Companion.TAG
import com.tech.inner_workout.ui.base.location.LocationHandler
import com.tech.inner_workout.ui.base.location.LocationResultListener
import com.tech.inner_workout.ui.base.permission.PermissionHandler
import com.tech.inner_workout.ui.base.permission.Permissions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(), LocationResultListener {

    private val viewModel: WelcomeActivityVM by viewModels()

    private var locationHandler: LocationHandler? = null
    private var mCurrentLocation: Location? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_welcome
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        checkLocation()
        val safeHeight = calculateSafeAreaHeight()

        val midPoint = calculateMidpoint()


        binding.ivEnd.animate()
            .x(midPoint.x)
            .y(midPoint.y)
            .alpha(1f)
            .scaleX(1.8f)
            .scaleY(1.8f)
            .setDuration(2000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {

                binding.ivInnerWork.animate()
                    .translationX(midPoint.x)
                    .translationY(midPoint.y + 40)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(800)
                    .setInterpolator(AccelerateInterpolator())
                    .start()

                binding.ivSelfCare.animate()
                    .translationX(midPoint.x)
                    .translationY(midPoint.y + 100)
                    .scaleX(1.1f)
                    .scaleY(1.1f)
                    .setDuration(800)
                    .setInterpolator(AccelerateInterpolator())
                    .start()


            }
            .withEndAction {
                binding.ivInnerWork.animate()
                    .translationX(binding.ivEnd.x)
                    .translationY(binding.ivEnd.y + 60)
                    .scaleX(0.8f)
                    .scaleY(0.8f)
                    .setDuration(1000)
                    .setInterpolator(DecelerateInterpolator())
                    .start()

                binding.ivSelfCare.animate()
                    .translationX(midPoint.x)
                    .translationY(binding.ivEnd.y + 70)
                    .scaleX(0.8f)
                    .scaleY(0.8f)
                    .alpha(0f)
                    .setDuration(1000)
                    .setInterpolator(AccelerateInterpolator())
                    .start()

                              /*  welcomeImageView.animate()
                                    .translationX(midPoint.x)
                                    .translationY(    binding.ivEnd.y + safeHeight + 25)
                                    .alpha(1f)
                                    .setDuration(1000)
                                    .setInterpolator(AccelerateInterpolator())
                                    .start()*/
            }
            .withEndAction {
               /*   mainView.animate()
                      .alpha(1f)
                      .translationY(-safeHeight * 0.8f)
                      .setDuration(1000)
                      .setInterpolator(DecelerateInterpolator())
                      .start()

                  alreadyImageView.animate()
                      .alpha(1f)
                      .setDuration(1000)
                      .start()

                  privacyImageView.animate()
                      .alpha(1f)
                      .translationY(-30 - (safeHeight * 0.8f))
                      .setDuration(1000)
                      .start()

                  signupView.animate()
                      .alpha(1f)
                      .translationY(-30 - (safeHeight * 0.8f))
                      .setDuration(1000)
                      .start()

                  passView.animate()
                      .alpha(1f)
                      .translationY(-40 - safeHeight)
                      .setDuration(1000)
                      .start()

                  emailView.animate()
                      .alpha(1f)
                      .translationY(-50 - safeHeight)
                      .setDuration(1000)
                      .start()*/
            }
            .start()

        binding.iVStart.animate()
            .x(midPoint.x)
            .y(midPoint.y)
            .alpha(1f)
            .scaleX(1.8f)
            .scaleY(1.8f)
            .setDuration(2000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                binding.iVStart.animate()
                    //  .translationX(midPoint.x - 10)
                    .translationY(midPoint.y - 100)
                    .scaleX(0.6f)
                    .scaleY(0.6f)
                    .setDuration(3000)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .withEndAction {
                        binding.iVStart.animate()
                            //  .translationX(midPoint.x - 10)
                            .translationY(88f)
                            .scaleX(0.7f)
                            .scaleY(0.7f)
                            .setDuration(1000)
                            .setInterpolator(DecelerateInterpolator())
                            .start()
                    }
                    .start()

                binding.ivEnd.animate()
                    //  .translationX(midPoint.x - 10)
                    .translationY(-midPoint.y  )
                    .scaleX(0.6f)
                    .scaleY(0.6f)
                    .setDuration(3000)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .withEndAction {
                        binding.ivEnd.animate()
                            //  .translationX(midPoint.x - 10)
                            .translationY(-88f)
                            .scaleX(0.7f)
                            .scaleY(0.7f)
                            .setDuration(1000)
                            .setInterpolator(DecelerateInterpolator())
                            .start()
                    }
            }
            .start()

        binding.ivBackground.animate()
            .alpha(0.6f)
            .setDuration(4000)
            .start()

        binding.ivSelfCare.animate()
            .alpha(1f)
            .setDuration(4000)
            .start()
    }

    private fun checkLocation() {
        Permissions.check(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION,
            0,
            object : PermissionHandler() {
                override fun onGranted() {
                    createLocationHandler()
                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                    super.onDenied(context, deniedPermissions)

                }
            })
    }

    private fun createLocationHandler() {
        locationHandler = LocationHandler(this, this)
        locationHandler?.getUserLocation()
        locationHandler?.removeLocationUpdates()
    }

    private fun openActivity() {
        /*val pointA =     binding.ivEnd.center
        val pointB =     binding.iVStart.center*/
        val startPos = IntArray(2)
        val endPos = IntArray(2)
        binding.iVStart.getLocationInWindow(startPos)
        binding.ivEnd.getLocationInWindow(endPos)
        val midPoint = FloatArray(2)
        midPoint[0] = (startPos[0] + endPos[0]).toFloat().div(2)
        midPoint[1] = (startPos[1] + endPos[1]).toFloat().div(2)
        //      500f//PointF((pointA.x + pointB.x) / 2, (pointA.y + pointB.y) / 2)
        //     binding.ivEnd animation
        binding.ivBackground.animate()
            .translationX(0f)
            .translationY(0f)
            .alpha(0.5f)
            .setDuration(2000)
            .setInterpolator(DecelerateInterpolator())


        binding.ivEnd.animate()
            .translationX(-midPoint[0] - 270)
            .translationY(-midPoint[1] + 270)
            .scaleX(1.1f).scaleY(1.1f)
            .setDuration(800)
            .setInterpolator(android.view.animation.LinearInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                }
            })
        // interWorkOutImageView animation
        binding.iVStart.animate()
            .translationX(midPoint[0] + 270)
            .translationY(midPoint[1] - 270)
            .scaleX(1.2f).scaleY(1.2f)
            .setDuration(800)
            .setInterpolator(android.view.animation.LinearInterpolator())
            .withEndAction {
                binding.ivInnerWork.animate()
                    .translationX(midPoint[0])
                    .translationY(midPoint[1] + 40)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(800)
                    .setInterpolator(AccelerateInterpolator())
                    .start()

                binding.ivSelfCare.animate()
                    .translationX(midPoint[0])
                    .translationY(midPoint[1] + 100)
                    .scaleX(1.1f)
                    .scaleY(1.1f)
                    .setDuration(800)
                    .setInterpolator(AccelerateInterpolator())
                    .start()
            }

    }

    override fun getLocation(location: Location) {
        this.mCurrentLocation = location

    }

    private fun calculateMidpoint(): PointF {
        val midPoint = getScreenMidPoint(this)
        val midX = midPoint.first
        val midY = midPoint.second
        Log.i(TAG, "calculateMidpoint: $midX : $midY")
        return PointF(midX, midY)
    }

    fun getScreenMidPoint(context: Context): Pair<Float, Float> {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels.toFloat()
        val screenHeight = displayMetrics.heightPixels.toFloat()
        val midX = screenWidth / 2
        val midY = screenHeight / 2
        return Pair(midX, midY)
    }

    private fun calculateSafeAreaHeight(): Float {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId).toFloat()
        } else {
            0f
        }
    }
}
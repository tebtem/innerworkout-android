package com.tech.inner_workout.utils

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.tech.inner_workout.ui.base.permission.PermissionHandler
import com.tech.inner_workout.ui.base.permission.Permissions
import java.net.URISyntaxException

class Permission : AppCompatActivity() {
    private fun selectImage() {
        val options = arrayOf<CharSequence>(
            "Take Photo",
            "Choose From Gallery",
            "Cancel"
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo")
        builder.setItems(
            options
        ) { dialog: DialogInterface, item: Int ->
            if (options[item] ==   "Take Photo") {
                openCamera()
            } else if (options[item] == "Choose From Gallery") {
                openGallery()
            }  else if (options[item] ==   "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }
    private fun openCamera() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Permissions.check(this, Manifest.permission.CAMERA, 0, object : PermissionHandler() {
                override fun onGranted() {
                    openMediaIntent()
                }
            })
        } else {
            openMediaIntent()
        }
    }

    // gallery intent
    private fun openMediaIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)

    }

    private var cameraLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                val   photo = result.data?.extras?.get("data") as Bitmap
                if (photo != null) {

                }
            }
        }

    private fun openGallery() {
        val permission: Array<String>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.CAMERA
            )
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED ||ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_VIDEO
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Permissions.check(this, permission, "0", null, object : PermissionHandler() {
                    override fun onGranted() {
                        openGalleryIntent()
                    }
                })
            } else {
                openGalleryIntent()
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Permissions.check(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    0,
                    object : PermissionHandler() {
                        override fun onGranted() {
                            openGalleryIntent()
                        }
                    })
            } else {
                openGalleryIntent()
            }
        }
    }

    private fun openGalleryIntent() {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.launch(i)

    }

    private var galleryIntent: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImage: Uri? = result.data?.data
                try {
                    Log.i("TAG", ": $selectedImage")
                } catch (e: URISyntaxException) {
                    e.printStackTrace()
                }
            }
        }
}
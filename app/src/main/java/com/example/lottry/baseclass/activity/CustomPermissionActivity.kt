package  com.softs.meetupfellow.components.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
//import com.softs.meetupfellow.R
//import com.softs.meetupfellow.utils.constant.Constants
//import pub.devrel.easypermissions.AfterPermissionGranted
//import pub.devrel.easypermissions.AppSettingsDialog
//import pub.devrel.easypermissions.EasyPermissions


@SuppressLint("Registered")
/**
 * Created by Jammwal on 07-03-2018.
 */
open class CustomPermissionActivity : CustomAppActivityImpl()/*, EasyPermissions.PermissionCallbacks*/ {

  /*  override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this@CustomPermissionActivity, perms)) {
            AppSettingsDialog.Builder(this@CustomPermissionActivity).build().show()
        }
//        LocationModel().apply {
//            setLatitude(Constants.Default.Latitude.toDouble())
//            setLongitude(Constants.Default.Longitude.toDouble())
//            setLocationName(Constants.Default.Location)
//        }.saveLocation()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    internal fun checkRequiredPermissions() {
        checkLocation()
//        checkRecordAudioPermission()
    }

    private fun checkRecordAudioPermission() {
        if (!EasyPermissions.hasPermissions(
                this@CustomPermissionActivity,
                *Constants.Permissions.CALLING
            )
        ) {
            EasyPermissions.requestPermissions(
                this@CustomPermissionActivity,
                String.format(
                    getString(R.string.text_rational_audio),
                    getString(R.string.app_name)
                ),
                Constants.PermissionsCode.REQUEST_PERMISSION_RECORD_AUDIO,
                *Constants.Permissions.CALLING
            )
        }
    }





    @AfterPermissionGranted(Constants.PermissionsCode.REQUEST_PERMISSION_LOCATION)
    fun checkLocation() : Boolean {
        when (isLocationEnabled(this@CustomPermissionActivity)) {
            true -> {
                hasLocationPermission()
                return true
            }

            false -> {
                AlertDialog.Builder(this@CustomPermissionActivity)
                    .setTitle("Location Disabled")  // GPS not found
                    .setMessage("Location Disabled") // Want to enable?
                    .setPositiveButton(
                        R.string.text_yes
                    ) { _, _ ->
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                    .setNegativeButton(R.string.text_no, null)
                    .show()
//                LocationModel().apply {
//                    setLatitude(Constants.Default.Latitude.toDouble())
//                    setLongitude(Constants.Default.Longitude.toDouble())
//                    setLocationName(Constants.Default.Location)
//                }.saveLocation()
            return false
            }

        }
    }

    internal fun fetchLocation() {
        if (EasyPermissions.hasPermissions(
                this@CustomPermissionActivity,
                *Constants.Permissions.LOCATION
            )
        ) {
            initializeApp()
        }
    }

    private fun hasLocationPermission() {
        if (EasyPermissions.hasPermissions(
                this@CustomPermissionActivity,
                *Constants.Permissions.LOCATION
            )
        ) {
            initializeApp()
        } else {
            EasyPermissions.requestPermissions(
                this@CustomPermissionActivity,
                String.format(
                    getString(R.string.text_rational_location),
                    getString(R.string.app_name)
                ),
                Constants.PermissionsCode.REQUEST_PERMISSION_LOCATION,
                *Constants.Permissions.LOCATION
            )
        }
    }

    protected fun cameraPermission(permission: Int): Boolean {
        return when {
            EasyPermissions.hasPermissions(
                this@CustomPermissionActivity,
                *Constants.Permissions.CAMERA_STORAGE
            ) -> true
            else -> {
                EasyPermissions.requestPermissions(
                    this@CustomPermissionActivity,
                    String.format(
                         getString(R.string.text_rational_camera),
                        getString(R.string.app_name)
                    ),
                    permission,
                    *Constants.Permissions.CAMERA_STORAGE
                )
                false
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode, permissions, grantResults, this@CustomPermissionActivity
        )
    }*/

}
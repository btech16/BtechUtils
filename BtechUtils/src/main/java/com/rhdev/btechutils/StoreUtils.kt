package com.rhdev.btechutils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import android.net.Uri
import android.os.Build
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory

object StoreUtils {

    val GOOGLE_PLAY_DEV_URL = "https://play.google.com/store/apps/dev?id=6736668226615202750"
    private val TAG = "BtechUtils : " + StoreUtils::class.simpleName

    fun openDevGooglePlayAccount(context: Context) {
        val applicationContext = context.applicationContext
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(GOOGLE_PLAY_DEV_URL)
            setPackage("com.android.vending")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK

        }
        applicationContext.startActivity(intent)
    }

    fun checkAppUpdate(context: Context, callback: (Boolean) -> Unit) {
        val applicationContext = context.applicationContext
        val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfoTask: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo

        val currentVersionCode = getAppVersionCode(applicationContext)
        Log.v(TAG, "checkAppUpdate: currentVersionCode = $currentVersionCode")

        appUpdateInfoTask
            .addOnSuccessListener { appUpdateInfo ->

                Log.v(
                    TAG,
                    "onSuccess() called with: appUpdateInfo = $appUpdateInfo"
                )

                appUpdateInfo?.let {
                    val availableVersionCode = appUpdateInfo.availableVersionCode()
                    val updateAvailability = appUpdateInfo.updateAvailability()
                    Log.v(TAG, "checkAppUpdate() called availableVersionCode = $availableVersionCode")
                    Log.v(TAG, "checkAppUpdate() called updateAvailability = $updateAvailability")

                    callback((currentVersionCode > -1 && availableVersionCode > currentVersionCode))

                } ?: kotlin.run {
                    callback(false)
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "checkAppUpdate() addOnFailureListener called", exception)
            }
            .addOnCompleteListener { task ->
                Log.v(TAG, "checkAppUpdate() addOnCompleteListener called with: task = $task")
            }
            .addOnCanceledListener {
                Log.v(TAG, "checkAppUpdate() addOnCanceledListener called")
            }
    }

    private fun getAppVersionCode(applicationContext: Context): Int {
        var versionCode = -1
        val packageName = applicationContext.packageName ?: return versionCode
        val packageManager = applicationContext.packageManager ?: return versionCode
        val packageInfo: PackageInfo = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(GET_META_DATA.toLong() or PackageManager.GET_ACTIVITIES.toLong())
                )
            } else {
                packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_ACTIVITIES
                )
            }

        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "getAppVersionCode: ", e)
            null
        } ?: return versionCode

        versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toInt()
        } else {
            @Suppress("DEPRECATION") packageInfo.versionCode
        }

        return versionCode
    }


    fun openAppPageOnGooglePlay(context: Context) {
        val applicationContext = context.applicationContext
        val appPackageName: String = context.packageName
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id=$appPackageName"
            )
            setPackage("com.android.vending")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK

        }

        Log.d(TAG, "openAppPageOnGooglePlay() called with: url = ${intent.data}")

        applicationContext.startActivity(intent)
    }
}
package com.rhdev.btechutils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DarkMode {

    private val TAG = "P_TAG : " + DarkMode::class.simpleName
    private const val APP_DARK_MODE_SP_KEY = "app_dark_mode_sp_key"

    private val uiModeMap = mapOf(
        "نهاري" to AppCompatDelegate.MODE_NIGHT_NO,
        "ليلي" to AppCompatDelegate.MODE_NIGHT_YES,
        "تلقائي" to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
    )


    private fun setAppDarkModeValue(context: Context, mode: Int) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putInt(APP_DARK_MODE_SP_KEY, mode).apply()
    }

    private fun getAppDarkModeValue(context: Context): Int {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getInt(
            APP_DARK_MODE_SP_KEY,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
    }

    fun applyAppModeToUI(context: Context) {
        AppCompatDelegate.setDefaultNightMode(getAppDarkModeValue(context))
    }

    private fun applyAppModeToUI(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    // Function to check if the current theme is dark
    fun isDarkTheme(context: Context): Boolean {
        return when (getAppDarkModeValue(context)) {
            AppCompatDelegate.MODE_NIGHT_YES -> true
            AppCompatDelegate.MODE_NIGHT_NO -> false
            else -> {
                val currentNightMode =
                    context.applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                currentNightMode == Configuration.UI_MODE_NIGHT_YES
            }
        }
    }

    fun showUiModeDialog(context: Context) {

        val currentSelectedMode = getPositionByValue(getAppDarkModeValue(context))
        var checkedItem = currentSelectedMode

        MaterialAlertDialogBuilder(context)
            .setTitle("اختيار المظهر")
            .setSingleChoiceItems(uiModeMap.keys.toTypedArray(), checkedItem) { _, which ->
                checkedItem = which
            }
            .setNeutralButton("موافق") { _, _ ->
                if (currentSelectedMode != checkedItem) {
                    val selectedMode = getValueByPosition(checkedItem)
                    setAppDarkModeValue(context, selectedMode)
                    applyAppModeToUI(context)
                }
            }
            .setPositiveButton("الغاء") { _, _ ->

            }

            .show()
    }

    private fun getValueByPosition(position: Int): Int {
        uiModeMap.values.toIntArray().forEachIndexed { index, i ->
            if (index == position) {
                return i
            }
        }
        return -1
    }

    private fun getKeyByPosition(position: Int): String {
        uiModeMap.keys.toTypedArray().forEachIndexed { index, i ->
            if (index == position) {
                return i
            }
        }
        return ""
    }

    private fun getPositionByValue(value: Int): Int {
        uiModeMap.values.forEachIndexed { index, i ->
            if (value == i) {
                return index
            }
        }
        return -1
    }

    private fun getPositionByKey(map: Map<String, Int>, key: String): Int {
        map.keys.forEachIndexed { index, i ->
            if (key == i) {
                return index
            }
        }
        return -1
    }

}



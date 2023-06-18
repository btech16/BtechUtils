package com.rhdev.btechutils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DarkMode {
    private val TAG = "P_TAG : " + DarkMode::class.simpleName
    private const val APP_DARK_MODE_SP_KEY = "app_dark_mode_sp_key"

    private lateinit var appContext: Context
    lateinit var modesList: List<Mode>
        private set

    // ...

    fun initialize(context: Context) {
        appContext = context.applicationContext
        initModesList()
    }

    private fun initModesList() {
        modesList = listOf(
            Mode(appContext.resources.getString(R.string.light), Type.LIGHT),
            Mode(appContext.resources.getString(R.string.night), Type.NIGHT),
            Mode(appContext.resources.getString(R.string.system), Type.SYSTEM)
        )
    }

    fun changeMode(type: Type) {
        setAppDarkModeValue(type)
        applyAppModeToUI()
    }

    private fun setAppDarkModeValue(type: Type) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)
        sharedPreferences.edit().putString(APP_DARK_MODE_SP_KEY, type.value).apply()
    }

    fun getCurrentModePosition(): Int {
        modesList.forEachIndexed { index, mode ->
            if (mode.type == getCurrentModeType()) {
                return index
            }
        }
        return -1
    }

    private fun getCurrentModeDelegate(): Int {
        return getModeDelegateByType(getCurrentModeType())
    }

    private fun getModeDelegateByType(type: Type): Int {
        return when (type) {
            Type.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            Type.NIGHT -> AppCompatDelegate.MODE_NIGHT_YES
            Type.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

    private fun getCurrentModeType(): Type {
        val appDarkModeValue = getCurrentModeValue()
        return getTypeByValue(appDarkModeValue)
    }

    private fun getTypeByValue(value: String): Type {
        return Type.values().find { it.value == value } ?: Type.SYSTEM
    }

    private fun getCurrentModeValue(): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)
        return sharedPreferences.getString(APP_DARK_MODE_SP_KEY, Type.SYSTEM.value)
            ?: Type.SYSTEM.value
    }

    private fun applyAppModeToUI() {
        applyAppModeToUI(getCurrentModeType())
    }

    private fun applyAppModeToUI(type: Type) {
        AppCompatDelegate.setDefaultNightMode(getModeDelegateByType(type))
    }

    // Function to check if the current theme is dark
    fun isDarkTheme(): Boolean {
        return when (getCurrentModeDelegate()) {
            AppCompatDelegate.MODE_NIGHT_YES -> true
            AppCompatDelegate.MODE_NIGHT_NO -> false
            else -> {
                val currentNightMode =
                    appContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                currentNightMode == Configuration.UI_MODE_NIGHT_YES
            }
        }
    }

    fun showUiModeDialog(context: Context) {
        val currentSelectedMode = getCurrentModePosition()
        var checkedItem = currentSelectedMode
        val items = modesList.map { it.title }.toTypedArray()

        MaterialAlertDialogBuilder(context)
            .setTitle("اختيار المظهر")
            .setSingleChoiceItems(items, checkedItem) { _, which ->
                checkedItem = which
            }
            .setNeutralButton("موافق") { _, _ ->
                if (currentSelectedMode != checkedItem) {
                    val selectedMode = modesList[checkedItem]
                    changeMode(selectedMode.type)
                }
            }
            .setPositiveButton("الغاء") { _, _ ->
            }
            .show()
    }

    data class Mode(
        var title: String,
        var type: Type,
    )

    enum class Type(var value: String) {
        LIGHT("light"),
        NIGHT("night"),
        SYSTEM("system")
    }
}

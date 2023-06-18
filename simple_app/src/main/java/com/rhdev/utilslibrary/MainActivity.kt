package com.rhdev.utilslibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rhdev.btechutils.DarkMode
import com.rhdev.btechutils.DarkMode.changeMode
import com.rhdev.btechutils.DarkMode.getCurrentModePosition
import com.rhdev.btechutils.DarkMode.modesList
import com.rhdev.btechutils.StoreUtils
import com.rhdev.btechutils.ToastyType
import com.rhdev.btechutils.ToastyUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DarkMode.initialize(context = this@MainActivity)

        isDarkModeToasty()


        val nightButton = findViewById<Button>(R.id.night_button)
        val devPageButton = findViewById<Button>(R.id.dev_page)
        val appPageButton = findViewById<Button>(R.id.app_page_button)
        val checkUpdatesButton = findViewById<Button>(R.id.checkUpdates)

        nightButton.setOnClickListener {
            DarkMode.showUiModeDialog(this@MainActivity)
        }

        nightButton.setOnLongClickListener {
            val currentSelectedMode = getCurrentModePosition()
            var checkedItem = currentSelectedMode

            val items = modesList.map { it.title }.toTypedArray()

            MaterialAlertDialogBuilder(this@MainActivity)
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

            true
        }

        devPageButton.setOnClickListener {
            StoreUtils.openDevGooglePlayAccount(this@MainActivity)
        }

        appPageButton.setOnClickListener {
            StoreUtils.openAppPageOnGooglePlay(this@MainActivity)

        }

        checkUpdatesButton.setOnClickListener {
            StoreUtils.checkAppUpdate(
                context = this@MainActivity){hasUpdate ->
                if (hasUpdate) {
                    ToastyUtils.show(
                        this@MainActivity,
                        ToastyType.SUCCESS,
                        "Has Update"
                    )
                } else {
                    ToastyUtils.show(
                        this@MainActivity,
                        ToastyType.ERROR,
                        "Not Update"
                    )
                }
            }
        }

    }

    private fun isDarkModeToasty() {
        ToastyUtils.show(
            context = this@MainActivity,
            toastyType = ToastyType.INFO,
            text = if (DarkMode.isDarkTheme()) "is night mode" else "is light mode")
    }


}
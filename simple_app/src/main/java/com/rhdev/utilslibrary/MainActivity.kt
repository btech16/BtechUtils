package com.rhdev.utilslibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.rhdev.btechutils.DarkMode
import com.rhdev.btechutils.StoreUtils
import com.rhdev.btechutils.ToastyType
import com.rhdev.btechutils.ToastyUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isDarkModeToasty()


        val nightButton = findViewById<Button>(R.id.night_button)
        val devPageButton = findViewById<Button>(R.id.dev_page)
        val appPageButton = findViewById<Button>(R.id.app_page_button)
        val checkUpdatesButton = findViewById<Button>(R.id.checkUpdates)

        nightButton.setOnClickListener {
            DarkMode.showUiModeDialog(context = this@MainActivity)
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
            text = if (DarkMode.isDarkTheme(context = this@MainActivity)) "is night mode" else "is light mode")
    }


}
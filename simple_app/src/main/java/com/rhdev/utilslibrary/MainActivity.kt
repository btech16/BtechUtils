package com.rhdev.utilslibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rhdev.btechutils.DarkMode
import com.rhdev.btechutils.DarkMode.changeMode
import com.rhdev.btechutils.DarkMode.getCurrentModePosition
import com.rhdev.btechutils.DarkMode.modesList
import com.rhdev.btechutils.StoreUtils
import com.rhdev.btechutils.ToastyType
import com.rhdev.btechutils.toasty
import com.rhdev.utilslibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DarkMode.initialize(context = this@MainActivity, true)

        binding.apply {


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

            devPageBtn.setOnClickListener {
                StoreUtils.openDevGooglePlayAccount(this@MainActivity)
            }

            appPageButton.setOnClickListener {
                StoreUtils.openAppPageOnGooglePlay(this@MainActivity)

            }



            checkUpdatesBtn.setOnClickListener {
                StoreUtils.checkAppUpdate(
                    context = this@MainActivity
                ) { hasUpdate ->
                    if (hasUpdate) {
                        toasty("has update", ToastyType.SUCCESS)
                    } else {
                        toasty("Not Update")
                    }
                }
            }

            errorToasty.setOnClickListener {
                toasty("This Test For Toasty" , ToastyType.ERROR )
            }
            infoToasty.setOnClickListener {
                toasty(R.string.app_name , ToastyType.INFO )
            }
            normalToasty.setOnClickListener {
                toasty("This Test For Toasty" , ToastyType.NORMAL )
            }
            warningToasty.setOnClickListener {
                toasty(R.string.app_name , ToastyType.WARNING )
            }
            successToasty.setOnClickListener {
                toasty("This Test For Toasty" , ToastyType.SUCCESS )
            }

        }

    }


}
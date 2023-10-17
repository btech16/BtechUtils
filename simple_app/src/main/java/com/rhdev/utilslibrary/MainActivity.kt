package com.rhdev.utilslibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rhdev.btechutils.DarkMode
import com.rhdev.btechutils.DarkMode.changeMode
import com.rhdev.btechutils.DarkMode.getCurrentModePosition
import com.rhdev.btechutils.DarkMode.modesList
import com.rhdev.btechutils.Factory
import com.rhdev.btechutils.StoreUtils
import com.rhdev.btechutils.ToastType
import com.rhdev.btechutils.ToasterUtils
import com.rhdev.utilslibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        ToasterUtils.show(text = "has update", toastyType = ToastType.SUCCESS)
                    } else {
                        ToasterUtils.show(text = "Not Update")
                    }
                }
            }

            errorToasty.setOnClickListener {
                ToasterUtils.show(text = "تجربة تجربة تجربة" , toastyType = ToastType.ERROR )
            }
            infoToasty.setOnClickListener {
                ToasterUtils.show(text = getString(R.string.app_name) , toastyType = ToastType.INFO )
            }
            normalToasty.setOnClickListener {
                ToasterUtils.show(text = "This Test For Toasty" , toastyType = ToastType.INFO )
            }
            warningToasty.setOnClickListener {
                ToasterUtils.show(text = getString(R.string.app_name) , toastyType = ToastType.WARNING )
            }
            successToasty.setOnClickListener {
                ToasterUtils.show(text = "This Test For Toasty" , toastyType = ToastType.SUCCESS )
            }

        }

    }


}
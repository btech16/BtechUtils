package com.rhdev.utilslibrary

import android.app.Application
import com.rhdev.btechutils.Factory

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Factory.create(this)
    }


    companion object {
        private lateinit var instance: MyApp

        fun getInstance(): MyApp {
            return instance
        }
    }

}
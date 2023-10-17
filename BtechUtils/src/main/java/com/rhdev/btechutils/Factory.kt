package com.rhdev.btechutils

import android.app.Application
import com.hjq.toast.Toaster

class Factory private constructor(private val application: Application) {

    companion object {
        private var instance: Factory? = null

        fun create(application: Application): Factory {
            return instance ?: synchronized(this) {
                instance ?: Factory(application).also { instance = it }
            }
        }
    }

    init {
        Toaster.init(application)
        DarkMode.initialize(application)
    }

}

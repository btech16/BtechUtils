package com.rhdev.btechutils

import android.view.Gravity
import android.widget.Toast
import com.hjq.toast.ToastParams
import com.hjq.toast.Toaster
import com.hjq.toast.style.CustomToastStyle


object ToasterUtils {

    fun show(
        text: String,
        duration: Int = Toast.LENGTH_SHORT,
        gravity: Int = Gravity.BOTTOM,
        toastyType: ToastType = ToastType.INFO
    ) {
        val params =
            getParams(text = text, duration = duration, toastyType = toastyType, gravity = gravity)
        Toaster
            .show(params)
    }

    private fun getParams(
        text: String,
        duration: Int,
        gravity: Int,
        toastyType: ToastType
    ): ToastParams {
        return ToastParams().also {
            it.text = text
            it.duration = duration
            it.style = CustomToastStyle(
                when (toastyType) {
                    ToastType.ERROR -> R.layout.toast_error
                    ToastType.SUCCESS -> R.layout.toast_success
                    ToastType.WARNING -> R.layout.toast_warn
                    ToastType.INFO -> R.layout.toast_info
                },
                gravity , 0 , 0 ,0f , 0.075f
            )
        }

    }

}
enum class ToastType {
    ERROR, INFO, SUCCESS, WARNING
}


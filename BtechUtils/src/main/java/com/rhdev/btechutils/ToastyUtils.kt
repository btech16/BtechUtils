package com.rhdev.btechutils

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.rhdev.btechutils.ToastyUtils.show
import es.dmoral.toasty.Toasty


object ToastyUtils {
    fun show(
        context: Context,
        toastyType: ToastyType,
        @StringRes resId: Int,
        duration: Int = Toasty.LENGTH_LONG
    ) {
        show(context, toastyType, context.getString(resId), duration)
    }

    fun show(
        context: Context,
        toastyType: ToastyType,
        text: String,
        duration: Int = Toasty.LENGTH_LONG
    ) {
        when (toastyType) {
            ToastyType.ERROR -> Toasty.error(context, text, duration, true).show()
            ToastyType.INFO -> Toasty.info(context, text, duration, true).show()
            ToastyType.SUCCESS -> Toasty.success(context, text, duration, true).show()
            ToastyType.WARNING -> Toasty.warning(context, text, duration, true).show()
            ToastyType.NORMAL -> Toasty.normal(context, text, duration).show()
        }
    }
}


fun Context.toasty(
    @StringRes resId: Int,
    type: ToastyType = ToastyType.NORMAL,
    duration: Int = Toasty.LENGTH_LONG
) = show(this, type, resId, duration)

fun Context.toasty(
    text: String,
    type: ToastyType = ToastyType.NORMAL,
    duration: Int = Toasty.LENGTH_LONG
) = show(this, type, text, duration)

fun Fragment.toasty(
    @StringRes resId: Int,
    type: ToastyType = ToastyType.NORMAL,
    duration: Int = Toasty.LENGTH_LONG
) = show(requireContext(), type, resId, duration)


fun Fragment.toasty(
    text: String,
    type: ToastyType = ToastyType.NORMAL,
    duration: Int = Toasty.LENGTH_LONG
) = show(requireContext(), type, text, duration)

enum class ToastyType {
    ERROR, INFO, SUCCESS, WARNING, NORMAL
}
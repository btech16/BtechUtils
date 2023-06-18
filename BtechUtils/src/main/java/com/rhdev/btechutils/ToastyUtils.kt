package com.rhdev.btechutils

import android.content.Context
import es.dmoral.toasty.Toasty


object ToastyUtils {

    fun show(context : Context, toastyType: ToastyType, resId:Int) {
        show(context , toastyType , context.getString(resId))
    }

    fun show(context : Context,toastyType: ToastyType, text: String) {
        when (toastyType) {
            ToastyType.ERROR -> Toasty.error(context , text , Toasty.LENGTH_LONG , true).show()
            ToastyType.INFO -> Toasty.info(context, text , Toasty.LENGTH_LONG , true).show()
            ToastyType.SUCCESS -> Toasty.success(context, text , Toasty.LENGTH_LONG , true).show()
            ToastyType.WARNING -> Toasty.warning(context, text , Toasty.LENGTH_LONG , true).show()
            ToastyType.NORMAL -> Toasty.normal(context, text , Toasty.LENGTH_LONG ,null, true).show()
        }
    }


}

enum class ToastyType{
    ERROR , INFO ,SUCCESS  , WARNING , NORMAL
}
package com.rhdev.btechutils

import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textview.MaterialTextView

class ProgressDialog(context: Context) {

    private lateinit var onClickCancel: () -> Unit
    private var dialog: AlertDialog
    private var progressIndicator: LinearProgressIndicator
    private var cancelButton: MaterialButton
    private var titleTv: MaterialTextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)

        cancelButton = view.findViewById<MaterialButton?>(R.id.button).apply {
            setOnClickListener {
                cancel()
            }
        }

        titleTv = view.findViewById(R.id.title_tv)

        progressIndicator = view.findViewById(R.id.progress_horizontal)

        dialog = MaterialAlertDialogBuilder(context)
            .setView(view)
            .setCancelable(false)
            .create()
    }

    fun setMaxProgress(max:Int) :ProgressDialog{
        progressIndicator.max = max
        return this
    }
    fun setOnClickCancelButton(onClickCancel: () -> Unit) :ProgressDialog{
        this@ProgressDialog.onClickCancel = onClickCancel
        return this
    }

    fun setText(text: String):ProgressDialog {
        titleTv.text = text
        return this
    }



    fun show(onShowListener: () -> Unit = {}):ProgressDialog {
        dialog.setOnShowListener {
            onShowListener()
        }
        dialog.show()
        return this
    }

    fun cancel() {
        android.os.Handler(Looper.getMainLooper()).post {
            onClickCancel()
            dialog.dismiss()
        }
    }


    fun setIndeterminate():ProgressDialog {
        progressIndicator.isIndeterminate = true
        return this
    }

    fun setProgress(progress: Int) {
        android.os.Handler(Looper.getMainLooper()).post {
            progressIndicator.apply {
                if (progress < 0) {
                    isIndeterminate = true
                } else {
                    isIndeterminate = false
                    setProgressCompat(progress, true)
                }
            }
        }
    }

}
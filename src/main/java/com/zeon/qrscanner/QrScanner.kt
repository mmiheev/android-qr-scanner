package com.zeon.qrscanner

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage

class QrScanner private constructor() {
    companion object {
        const val REQUEST_CODE = 101
        const val RESULT_TEXT = "result_text"
        const val RESULT_ERROR = "result_error"

        fun create(): QrScanner {
            return QrScanner()
        }
    }

    @OptIn(ExperimentalGetImage::class)
    fun start(context: Context) {
        val intent = Intent(context, QrScannerActivity::class.java)
        context.startActivity(intent)
    }

    class ScannerContract : ActivityResultContract<Unit, QrResult>() {
        @OptIn(ExperimentalGetImage::class)
        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent(context, QrScannerActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): QrResult {
            return when (resultCode) {
                Activity.RESULT_OK -> {
                    val text = intent?.getStringExtra(RESULT_TEXT)
                    if (text != null) {
                        QrResult.Success(text)
                    } else {
                        QrResult.Error(QrException("Scanning failed", QrException.ERROR_UNKNOWN))
                    }
                }
                Activity.RESULT_CANCELED -> QrResult.Canceled
                else -> {
                    val errorCode = intent?.getIntExtra(RESULT_ERROR, QrException.ERROR_UNKNOWN)
                        ?: QrException.ERROR_UNKNOWN
                    QrResult.Error(QrException("Error occurred", errorCode))
                }
            }
        }
    }
}
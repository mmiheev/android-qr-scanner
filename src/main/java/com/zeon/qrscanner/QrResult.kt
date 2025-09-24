package com.zeon.qrscanner

sealed class QrResult {
    data class Success(val text: String) : QrResult()
    data class Error(val exception: QrException) : QrResult()
    object Canceled : QrResult()
}
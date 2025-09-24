package com.zeon.qrscanner

class QrException(
    message: String,
    val errorCode: Int = ERROR_UNKNOWN
) : Exception(message) {
    companion object {
        const val ERROR_CAMERA_PERMISSION_DENIED = 1001
        const val ERROR_CAMERA_ACCESS = 1002
        const val ERROR_SCANNING = 1003
        const val ERROR_UNKNOWN = 1000
    }
}

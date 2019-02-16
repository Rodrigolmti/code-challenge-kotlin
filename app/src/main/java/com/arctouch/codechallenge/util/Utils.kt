package com.arctouch.codechallenge.util

import android.content.Context
import android.net.ConnectivityManager
import com.arctouch.codechallenge.App

fun isDeviceHaveNoConnection(): Boolean {
    return try {
        val cm = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && !netInfo.isConnectedOrConnecting
    } catch (error: Exception) {
        error.printStackTrace()
        false
    }
}
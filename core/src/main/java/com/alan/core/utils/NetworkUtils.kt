package com.alan.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest


fun Context.hasInternet(callback: (Boolean) -> Unit) {
    try {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    callback(true)
                }

                override fun onLost(network: Network) {
                    callback(false)
                }
            }
        )
    } catch (e: Exception) {
        callback(false)
    }
}

fun Context.hasConnection(): Boolean {
    val conMgr = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = conMgr.activeNetworkInfo ?: return false
    return if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI || activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) activeNetworkInfo.isConnected else false
}

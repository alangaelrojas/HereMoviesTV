package com.alan.core.utils.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alan.core.utils.hasConnection

class NetworkStateReceiver(
    private val onNetworkChange: OnNetworkChange
    ) : BroadcastReceiver() {

    override fun onReceive(c: Context?, i: Intent?) {
        try {
            c?.hasConnection()?.let { onNetworkChange.hasInternet(it) }
        } catch (ex: Exception){
            ex.printStackTrace()
            onNetworkChange.hasInternet(false)
        }
    }
}
package com.alan.core.utils.broadcast_receiver

interface OnNetworkChange {

    fun hasInternet(hasConnection: Boolean)
}
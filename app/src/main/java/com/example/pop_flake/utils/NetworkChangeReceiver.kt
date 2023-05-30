package com.example.pop_flake.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkChangeReceiver  @Inject constructor(@ApplicationContext private val context: Context) : BroadcastReceiver() {

    companion object {
        val isNetworkAvailable: MutableLiveData<Boolean> = MutableLiveData()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isConnected = networkInfo != null && networkInfo.isConnected
        isNetworkAvailable.postValue(isConnected)
     }

    init {
        isNetworkAvailable.postValue(Helper.isNetworkAvailable(context))
    }
}

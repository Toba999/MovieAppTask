package com.example.pop_flake.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.example.pop_flake.R
import com.google.android.material.snackbar.Snackbar

object Helper {

    var isFirst = 0

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun showTopSnackBarWithColor(view: View, message: String, status: Boolean, activity: Activity) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        var backgroundColor : Int = if (status){
            R.color.Green
        }else{
             R.color.Red
        }
        val snackBarLayout = activity.layoutInflater.inflate(R.layout.layout_custom_snackbar, null)
        val snackBarText = snackBarLayout.findViewById<TextView>(R.id.text1)
        snackBarText.text = message

        (snackBar.view as Snackbar.SnackbarLayout).setBackgroundColor(Color.TRANSPARENT)

        val layoutParams = snackBarView.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        snackBarView.layoutParams = layoutParams
        (snackBar.view as Snackbar.SnackbarLayout).addView(snackBarLayout, 0)
        snackBarLayout.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(activity,backgroundColor))
        snackBar.show()
    }

}
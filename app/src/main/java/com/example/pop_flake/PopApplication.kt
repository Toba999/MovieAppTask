package com.example.pop_flake

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.pop_flake.data.local.DataStoreManager
import com.example.pop_flake.utils.Constants
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class PopApplication: Application(){

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initDarkMode()

    }

    private fun initDarkMode(){
        GlobalScope.launch {
            dataStoreManager.getInt(Constants.DARK_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                .collect { darkMode ->
                    withContext(Dispatchers.Main) {
                        AppCompatDelegate.setDefaultNightMode(darkMode)
                    }
                }
        }
    }
}
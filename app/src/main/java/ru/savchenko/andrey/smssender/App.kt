package ru.savchenko.andrey.smssender

import android.app.Application
import io.victoralbertos.rx2_permissions_result.RxPermissionsResult

/**
 * Created by Andrey on 17.12.2017.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RxPermissionsResult.register(this);
    }
}
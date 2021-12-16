package com.caiyuanzi.utiltools

import android.app.Application
import com.caiyuanzi.utiltools.utils.di.utilModule
import com.caiyuanzi.utiltools.utils.net.HttpConfig
import com.caiyuanzi.utiltools.utils.net.RetrofitUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MyApplication : Application() {

    companion object{
        private const val BASE_URL = "https://kcwcdev.i.cacf.cn/"
    }

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(utilModule)
        }

        RetrofitUtils.getInstance().init(HttpConfig(baseUrl = BASE_URL))
    }
}
package com.caiyuanzi.utiltools.utils.net

import okhttp3.logging.HttpLoggingInterceptor
import java.lang.StringBuilder
import java.util.logging.Logger

class LogInterceptor {

    fun logInterceptor():HttpLoggingInterceptor =
        HttpLoggingInterceptor(object :HttpLoggingInterceptor.Logger{
            private val mMessage = StringBuilder()

            override fun log(message: String) {
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)

}
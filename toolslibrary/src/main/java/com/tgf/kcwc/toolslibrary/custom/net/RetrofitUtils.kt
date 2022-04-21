package com.tgf.kcwc.toolslibrary.custom.net

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.UnsupportedOperationException
import java.util.concurrent.TimeUnit

class RetrofitUtils private constructor(){

    var mRetrofit: Retrofit? = null

    companion object{
        fun getInstance() = Holder.holder
    }

    private object  Holder{
        val holder = RetrofitUtils()
    }

   fun init(httpConfig: HttpConfig,gson: Gson = Gson()){
       val okHttpClient = OkHttpClient.Builder()
           .connectTimeout(httpConfig.connectTimeOut,TimeUnit.SECONDS)
           .readTimeout(httpConfig.readTimeout,TimeUnit.SECONDS)
           .writeTimeout(httpConfig.writeTimeout,TimeUnit.SECONDS)
           .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
           .build()

       mRetrofit = Retrofit.Builder()
           .client(okHttpClient)
           .baseUrl(httpConfig.baseUrl)
           .addConverterFactory(ScalarsConverterFactory.create())
           .addConverterFactory(GsonConverterFactory.create(gson))
           .build()
   }

    /**
     * 获取自定义的api服务类实例
     */
    inline fun <reified T> getService():T{
        val retrofit = mRetrofit?:throw UnsupportedOperationException("must call init() method first")
        return retrofit.create(T::class.java)
    }

}
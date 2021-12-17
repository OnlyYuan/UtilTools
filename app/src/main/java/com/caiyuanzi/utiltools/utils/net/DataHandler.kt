package com.caiyuanzi.utiltools.utils.net

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

object DataHandler {

    suspend fun <ResultType> performCollect(
        context: Context,
        block:suspend()-> ResultType?,
        onError:(suspend (Throwable)->Unit)? =null,
        onSuccess:(suspend (ResultType?) -> Unit)?= null
    ){

        block.asFlow()
            .flowOn(Dispatchers.IO)
            .onStart {

            }
            .onCompletion {

            }
            .catch { throwable ->
                ExceptionHandler.handle(context, throwable,true)
                onError?.invoke(throwable)
                Log.i("11","--->aaaaa222")
            }
            .flowOn(Dispatchers.Main)
            .collect {
                onSuccess?.invoke(it)
                Log.i("11","--->aaaaa")
            }
    }


}
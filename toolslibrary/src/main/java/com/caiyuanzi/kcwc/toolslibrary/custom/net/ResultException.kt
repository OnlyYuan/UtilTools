package com.caiyuanzi.kcwc.toolslibrary.custom.net

data class ResultException(
    val code:Int,
    val msg:String
) :RuntimeException(msg)
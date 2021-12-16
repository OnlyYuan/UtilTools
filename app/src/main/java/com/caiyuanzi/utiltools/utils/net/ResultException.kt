package com.caiyuanzi.utiltools.utils.net

data class ResultException(
    val code:Int,
    val msg:String
) :RuntimeException(msg)
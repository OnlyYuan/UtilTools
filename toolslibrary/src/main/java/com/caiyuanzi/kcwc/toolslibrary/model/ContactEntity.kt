package com.caiyuanzi.utiltools.model

/**
 * 读取通讯录的bean
 */
data class ContactEntity(var name:String,
                         var phone:String,
                         var pic:String,
                         var userId:String,
                         var firstWord:String,
                         var isFirst:Boolean) {

    var id:Long =0

    /**
     *  是否选中
     *  Ignore 表示忽略不加入表中
     */
    var isSelector:Boolean = false
}
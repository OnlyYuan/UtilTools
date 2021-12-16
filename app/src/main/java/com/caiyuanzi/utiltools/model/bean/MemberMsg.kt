package com.caiyuanzi.utiltools.model.bean

/**
 * 左边成员列表的成员信息
 */
data class MemberMsg(var id:Int?, //成员id
                     var b_user:Int, //B端商户ID
                     var name:String, //名称
                     var tel:String, //电话
                     var c_user:String, //用户ID
                     var comment:String, //用户备注
                     var avatar:String,//头像
                     var is_state:Int,//状态 0.正常  1.未注册 2.未授权
                     var is_leader:Int//是否商户管理员：1是 0否
                     ) {

}
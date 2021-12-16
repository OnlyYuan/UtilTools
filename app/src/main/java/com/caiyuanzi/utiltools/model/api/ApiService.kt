package com.caiyuanzi.utiltools.model.api

import com.caiyuanzi.utiltools.model.bean.MemberMsg
import com.caiyuanzi.utiltools.utils.net.ResultModel
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // 获取成员列表
    @POST("/api/authorize/1.0.0/team_member/index")
    suspend fun getMemberList(@Body param: JsonObject?): ResultModel<List<MemberMsg>?>
}
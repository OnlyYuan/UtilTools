package com.caiyuanzi.utiltools.model.repository

import com.caiyuanzi.utiltools.model.bean.MemberMsg
import com.caiyuanzi.utiltools.model.datasource.GetMemberDatasource
import com.google.gson.JsonObject


class MemberRepository(
    private val getMemberDatasource: GetMemberDatasource
) {

    /**
     * 获取用户信息
     */
    suspend fun getMemberMsg(keywords:String?):List<MemberMsg>?{
        return getMemberDatasource.load(keywords)
    }
}
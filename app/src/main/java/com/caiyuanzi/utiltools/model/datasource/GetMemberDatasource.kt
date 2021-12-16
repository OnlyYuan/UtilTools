package com.caiyuanzi.utiltools.model.datasource

import com.caiyuanzi.utiltools.model.api.ApiService
import com.caiyuanzi.utiltools.model.bean.MemberMsg
import com.caiyuanzi.utiltools.utils.net.RetrofitUtils
import com.caiyuanzi.utiltools.utils.net.toJsonObjectForApi
import com.google.gson.JsonObject

class GetMemberDatasource {

    /**
     * @param keywords 关键字
     */
    suspend fun load(keywords:String?):List<MemberMsg>?{

        return  RetrofitUtils.getInstance().getService<ApiService>()
            .getMemberList(mapOf("keywords" to keywords,"page" to 1,"size" to 20).toJsonObjectForApi())
            .getDataIfSuccess()
    }
}
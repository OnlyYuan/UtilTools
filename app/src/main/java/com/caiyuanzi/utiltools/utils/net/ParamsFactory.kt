package com.caiyuanzi.utiltools.utils.net

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser


private val mGson by lazy {
    Gson()
}
private val mJsonParser by lazy {
    JsonParser()
}

/**
 * 把 [Map<String, Any?>] 类型的参数转换成 api 接口需要的 [JsonObject] 类型的参数。
 */
fun Map<String, Any?>.toJsonObjectForApi(): JsonObject {
    val params = JsonObject()
    if (this.isNotEmpty()) {
        params.add("query", mJsonParser.parse(mGson.toJson(this)))
    }
    params.add("payload", JsonObject().apply {
      //  addProperty("token", ILoginService.getInstance()?.getUserInfo()?.token)
        addProperty("token", "9b09PtOCGZ5El-HSOqgla2rO_20")
    })
    return params
}
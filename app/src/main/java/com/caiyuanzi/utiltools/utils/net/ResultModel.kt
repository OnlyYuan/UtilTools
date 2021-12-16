package com.caiyuanzi.utiltools.utils.net


data class ResultModel<T>(
    val code:Int = -1,
    val data:DataModel<T>,
    val error:ErrorMsg?
) {

    companion object {
        const val CODE_SUCCESS = 0//成功
    }

    fun isSuccess(): Boolean {
        return code == CODE_SUCCESS
    }

    private fun getDataModelSuccess():DataModel<T>?{
        if (!isSuccess()){
            val errorMsg = error?.message
            throw RuntimeException( if (errorMsg.isNullOrEmpty()){
                "unknown error"
            }else{
                errorMsg
            })
//            throw ResultException(
//                code,
//                if (errorMsg.isNullOrEmpty()){
//                    "unknown error"
//                }else{
//                    errorMsg
//                }
//            )
        }
        return data
    }

    /**
     * 成功返回的分页信息，否则抛异常。
     */
    fun getPageModelIfSuccess(): PageInfo? {
        return getDataModelSuccess()?.page_info
    }

    /**
     * 成功返回的数据，否则抛异常。
     */
    fun getDataIfSuccess():T?{

        return getDataModelSuccess()?.data
    }


    data class ErrorMsg(
        val message: String? = null,
        val pathinfo: String? = null
    )

    data class DataModel<T>(
        val data: T?,
        val page_info:PageInfo?
    )

    data class PageInfo(
        val current_page: Int?,
        val total:Int?,
        val page_size:Int?,
    )
}
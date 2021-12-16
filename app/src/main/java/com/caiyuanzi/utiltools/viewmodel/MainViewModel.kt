package com.caiyuanzi.utiltools.viewmodel

import androidx.lifecycle.ViewModel
import com.caiyuanzi.utiltools.model.bean.MemberMsg
import com.caiyuanzi.utiltools.model.repository.MemberRepository
import com.google.gson.JsonObject


class MainViewModel(
    private val memberRepository: MemberRepository
):ViewModel() {

    /**
     * 获取用户信息
     */
    suspend fun getMemberMsg(keywords:String?): List<MemberMsg>?{
        return memberRepository.getMemberMsg(keywords)
    }

}
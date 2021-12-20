package com.caiyuanzi.utiltools.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

class CustomDialog:DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        mBinding = DataBindingUtil.inflate(inflater, R.layout.auth_operate_member_dialog,container,false)
//        initView()
//        return mBinding.root
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initView(){


    }

}
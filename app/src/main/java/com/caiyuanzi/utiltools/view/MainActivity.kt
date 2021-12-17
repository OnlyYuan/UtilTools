package com.caiyuanzi.utiltools.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.caiyuanzi.utiltools.R
import com.caiyuanzi.utiltools.databinding.ActivityMainBinding
import com.caiyuanzi.utiltools.model.datasource.GetMemberDatasource
import com.caiyuanzi.utiltools.utils.net.DataHandler
import com.caiyuanzi.utiltools.utils.net.ExceptionHandler
import com.caiyuanzi.utiltools.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityMainBinding
    private val mViewModel:MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initLinsener()
    }

    private fun initLinsener() {
        mBinding.loginBtn.setOnClickListener {
                lifecycleScope.launch {
                    DataHandler.performCollect(
                        this@MainActivity,
                        block = {
                            mViewModel.getMemberMsg("")
                        },
                        onError = {
                            Log.i("111","----->${it.message.toString()}")
                        },
                        onSuccess = {
                            Log.i("111","----->成功")
                        }
                    )
                }
        }
    }


}
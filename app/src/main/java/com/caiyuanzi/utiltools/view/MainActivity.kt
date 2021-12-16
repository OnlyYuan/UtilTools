package com.caiyuanzi.utiltools.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.caiyuanzi.utiltools.R
import com.caiyuanzi.utiltools.databinding.ActivityMainBinding
import com.caiyuanzi.utiltools.model.datasource.GetMemberDatasource
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
                    mm()
                }
        }
    }

    private suspend fun mm(){
        mViewModel.getMemberMsg("")?.asFlow()
            ?.flowOn(Dispatchers.IO)
            ?.onStart {

            }
            ?.onCompletion {

            }
            ?.catch {
                //   ExceptionHandler.handle(this@MainActivity, throwable,true)
                Log.i("11","--->aaaaa222")
            }
            ?.flowOn(Dispatchers.Main)
            ?.collect {
                Log.i("11","--->aaaaa")
            }
    }
}
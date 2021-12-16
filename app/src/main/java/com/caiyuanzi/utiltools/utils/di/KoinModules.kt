package com.caiyuanzi.utiltools.utils.di

import com.caiyuanzi.utiltools.model.datasource.GetMemberDatasource
import com.caiyuanzi.utiltools.model.repository.MemberRepository
import com.caiyuanzi.utiltools.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val utilModule = module {

    factory {
        GetMemberDatasource()
    }

    factory {
        MemberRepository(get())
    }

    viewModel {
        MainViewModel(get())
    }

}
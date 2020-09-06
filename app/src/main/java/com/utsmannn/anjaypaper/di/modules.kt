package com.utsmannn.anjaypaper.di

import com.utsmannn.anjaypaper.data.LocalPhotoRepository
import com.utsmannn.anjaypaper.network.NetworkInstance
import com.utsmannn.anjaypaper.data.PhotoRepository
import com.utsmannn.anjaypaper.viewmodel.PhotoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repository = module {
    factory { NetworkInstance.create() }
    factory { PhotoRepository(get()) }
    factory { LocalPhotoRepository() }
}

val viewModel = module {
    viewModel { PhotoViewModel(get(), get()) }
}
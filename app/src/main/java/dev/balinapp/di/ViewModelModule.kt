package dev.balinapp.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.balinapp.ui.auth.AuthViewModel
import dev.balinapp.ui.auth.TokenViewModel
import dev.balinapp.ui.main.MainViewModel

@Module
interface ViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(TokenViewModel::class)]
    fun bindTokenViewModel(tokenViewModel: TokenViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(AuthViewModel::class)]
    fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}
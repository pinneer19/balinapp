package dev.balinapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.balinapp.MainActivity
import dev.balinapp.data.di.DataModule
import dev.balinapp.ui.auth.AuthFragment
import dev.balinapp.ui.auth.LoginFragment
import dev.balinapp.ui.auth.RegisterFragment
import javax.inject.Singleton

@Component(modules = [DataModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(authFragment: AuthFragment)

    fun inject(loginFragment: LoginFragment)

    fun inject(registerFragment: RegisterFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
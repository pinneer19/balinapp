package dev.balinapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.balinapp.MainActivity
import dev.balinapp.data.di.DataModule
import dev.balinapp.ui.auth.AuthFragment
import dev.balinapp.ui.auth.LoginFragment
import dev.balinapp.ui.auth.RegisterFragment
import dev.balinapp.ui.comments.CommentFragment
import dev.balinapp.ui.images.ImageFragment
import dev.balinapp.ui.main.MainFragment
import javax.inject.Singleton

@Component(modules = [DataModule::class, ViewModelModule::class, AppServiceModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(authFragment: AuthFragment)

    fun inject(loginFragment: LoginFragment)

    fun inject(registerFragment: RegisterFragment)

    fun inject(mainFragment: MainFragment)

    fun inject(imageFragment: ImageFragment)

    fun inject(commentFragment: CommentFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
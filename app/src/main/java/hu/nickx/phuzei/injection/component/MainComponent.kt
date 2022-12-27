package hu.nickx.phuzei.injection.component

import dagger.Component
import hu.nickx.phuzei.injection.module.ContextModule
import hu.nickx.phuzei.injection.module.DataModule
import hu.nickx.phuzei.injection.module.NetworkModule
import hu.nickx.phuzei.injection.module.ViewModelModule
import hu.nickx.phuzei.injection.util.ViewModelFactoryModule
import hu.nickx.phuzei.presentation.album.AlbumFragment
import hu.nickx.phuzei.presentation.login.LoginActivity
import hu.nickx.phuzei.presentation.main.MainActivity
import hu.nickx.phuzei.presentation.muzei.PhotosArtProvider
import hu.nickx.phuzei.presentation.muzei.PhotosWorker
import hu.nickx.phuzei.presentation.setting.SettingsFragment
import hu.nickx.phuzei.presentation.splash.SplashActivity
import hu.nickx.phuzei.util.TokenAuthenticator
import javax.inject.Singleton

/**
 * Created by Alireza Afkar on 16/3/2018AD.
 */
@Singleton
@Component(
    modules = [
        DataModule::class,
        ContextModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class
    ]
)
interface MainComponent {

    fun inject(photosWorker: PhotosWorker)
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(albumFragment: AlbumFragment)
    fun inject(splashActivity: SplashActivity)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(photosArtProvider: PhotosArtProvider)
    fun inject(tokenAuthenticator: TokenAuthenticator)
}

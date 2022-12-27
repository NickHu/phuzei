package uk.co.nickhu.phuzei.injection.component

import uk.co.nickhu.phuzei.injection.module.ContextModule
import uk.co.nickhu.phuzei.injection.module.DataModule
import uk.co.nickhu.phuzei.injection.module.NetworkModule
import uk.co.nickhu.phuzei.injection.module.ViewModelModule
import uk.co.nickhu.phuzei.injection.util.ViewModelFactoryModule
import uk.co.nickhu.phuzei.presentation.album.AlbumFragment
import uk.co.nickhu.phuzei.presentation.login.LoginActivity
import uk.co.nickhu.phuzei.presentation.main.MainActivity
import uk.co.nickhu.phuzei.presentation.muzei.PhotosArtProvider
import uk.co.nickhu.phuzei.presentation.muzei.PhotosWorker
import uk.co.nickhu.phuzei.presentation.setting.SettingsFragment
import uk.co.nickhu.phuzei.presentation.splash.SplashActivity
import uk.co.nickhu.phuzei.util.TokenAuthenticator
import dagger.Component
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

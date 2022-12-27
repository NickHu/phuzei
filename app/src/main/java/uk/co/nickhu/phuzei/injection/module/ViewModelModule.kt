package uk.co.nickhu.phuzei.injection.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.nickhu.phuzei.injection.util.ViewModelKey
import uk.co.nickhu.phuzei.presentation.album.AlbumViewModel
import uk.co.nickhu.phuzei.presentation.login.LoginViewModel
import uk.co.nickhu.phuzei.presentation.setting.SettingsViewModel
import uk.co.nickhu.phuzei.presentation.splash.SplashViewModel

/**
 * Created by Alireza Afkar on 5/21/20.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel::class)
    abstract fun bindAlbumViewModel(viewModel: AlbumViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}

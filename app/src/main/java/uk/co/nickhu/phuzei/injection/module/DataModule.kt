package uk.co.nickhu.phuzei.injection.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import uk.co.nickhu.phuzei.data.api.AlbumsApi
import uk.co.nickhu.phuzei.data.api.PhotosApi
import uk.co.nickhu.phuzei.data.api.TokenApi
import uk.co.nickhu.phuzei.data.pref.AppPreferences
import uk.co.nickhu.phuzei.data.repository.AlbumsRepository
import uk.co.nickhu.phuzei.data.repository.PhotosRepository
import uk.co.nickhu.phuzei.data.repository.TokenRepository
import javax.inject.Singleton

/**
 * Created by Alireza Afkar on 17/3/2018AD.
 */
@Module(includes = [ContextModule::class])
class DataModule {

    @Provides
    @Singleton
    fun provideAppPreferences(context: Context): AppPreferences {
        return AppPreferences(context)
    }

    @Provides
    @Singleton
    fun provideTokenRepository(api: TokenApi, prefs: AppPreferences): TokenRepository {
        return TokenRepository(api, prefs)
    }

    @Provides
    @Singleton
    fun provideAlbumsRepository(api: AlbumsApi): AlbumsRepository {
        return AlbumsRepository(api)
    }

    @Provides
    @Singleton
    fun providePhotosRepository(api: PhotosApi): PhotosRepository {
        return PhotosRepository(api)
    }

    @Provides
    fun provideDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}

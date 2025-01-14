package hu.nickx.phuzei.injection.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import hu.nickx.phuzei.data.api.AlbumsApi
import hu.nickx.phuzei.data.api.PhotosApi
import hu.nickx.phuzei.data.api.TokenApi
import hu.nickx.phuzei.data.pref.AppPreferences
import hu.nickx.phuzei.data.repository.AlbumsRepository
import hu.nickx.phuzei.data.repository.PhotosRepository
import hu.nickx.phuzei.data.repository.TokenRepository
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

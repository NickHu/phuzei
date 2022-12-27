package hu.nickx.phuzei.injection.module

import android.content.Context
import dagger.Module
import dagger.Provides
import hu.nickx.phuzei.App
import hu.nickx.phuzei.injection.qualifier.ApplicationContext
import hu.nickx.phuzei.injection.scope.ApplicationScope

/**
 * Created by Alireza Afkar on 16/3/2018AD.
 */
@Module
class ContextModule(val context: Context) {
    @Provides
    fun provideContext() = context

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun provideApplication(): App = context.applicationContext as App
}

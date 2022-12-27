package uk.co.nickhu.phuzei.injection.module

import android.content.Context
import uk.co.nickhu.phuzei.App
import uk.co.nickhu.phuzei.injection.qualifier.ApplicationContext
import uk.co.nickhu.phuzei.injection.scope.ApplicationScope
import dagger.Module
import dagger.Provides

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

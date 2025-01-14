package hu.nickx.phuzei.data.pref

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import hu.nickx.phuzei.R

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class AppPreferences(private val context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN, "")
        set(value) = preferences.edit { putString(ACCESS_TOKEN, value) }

    var refreshToken: String?
        get() = preferences.getString(REFRESH_TOKEN, "")
        set(value) = preferences.edit { putString(REFRESH_TOKEN, value) }

    var tokenType: String?
        get() = preferences.getString(TOKEN_TYPE, "")
        set(value) = preferences.edit { putString(TOKEN_TYPE, value) }

    var album: String?
        get() = preferences.getString(ALBUM, "")
        set(value) = preferences.edit { putString(ALBUM, value) }

    var pageToken: String?
        get() = preferences.getString(PAGE_TOKEN, "")
        set(value) = preferences.edit { putString(PAGE_TOKEN, value) }

    var shuffle: Boolean
        get() = preferences.getBoolean(SHUFFLE, false)
        set(value) = preferences.edit { putBoolean(SHUFFLE, value) }

    var categoryIndex: Int
        get() = preferences.getInt(CATEGORY, 0)
        set(value) = preferences.edit { putInt(CATEGORY, value) }

    var category: String = "All"
        get() = context.resources.getStringArray(R.array.categories)[categoryIndex]
        private set

    var imagesCount: Int
        get() = preferences.getInt(IMAGES_COUNT, 25)
        set(value) = preferences.edit { putInt(IMAGES_COUNT, value) }

    fun logout() {
        preferences.edit {
            putString(ALBUM, null)
            putBoolean(SHUFFLE, false)
            putString(TOKEN_TYPE, null)
            putString(PAGE_TOKEN, null)
            putString(ACCESS_TOKEN, null)
            putString(REFRESH_TOKEN, null)
        }
    }

    companion object {
        private const val ALBUM = "album"
        private const val SHUFFLE = "shuffle"
        private const val CATEGORY = "category"
        private const val PAGE_TOKEN = "page_token"
        private const val TOKEN_TYPE = "token_type"
        private const val IMAGES_COUNT = "images_count"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}

fun AppPreferences.token(token: String? = accessToken) =
    if (token.isNullOrBlank()) "" else "$tokenType $token"

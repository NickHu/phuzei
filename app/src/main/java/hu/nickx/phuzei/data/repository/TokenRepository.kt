package hu.nickx.phuzei.data.repository

import io.reactivex.Single
import hu.nickx.phuzei.data.api.TokenApi
import hu.nickx.phuzei.data.pref.AppPreferences
import hu.nickx.phuzei.util.applyNetworkSchedulers
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class TokenRepository @Inject constructor(
    private var api: TokenApi,
    private var prefs: AppPreferences
) {
    fun request(code: String): Single<Unit> {
        return api.request(code).map {
            prefs.tokenType = it.tokenType
            prefs.accessToken = it.accessToken
            it.refreshToken?.let { token ->
                prefs.refreshToken = token
            }
        }.compose(applyNetworkSchedulers())
    }
}

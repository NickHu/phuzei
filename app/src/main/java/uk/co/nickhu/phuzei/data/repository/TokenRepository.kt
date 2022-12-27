package uk.co.nickhu.phuzei.data.repository

import uk.co.nickhu.phuzei.data.api.TokenApi
import uk.co.nickhu.phuzei.data.pref.AppPreferences
import uk.co.nickhu.phuzei.util.applyNetworkSchedulers
import io.reactivex.Single
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

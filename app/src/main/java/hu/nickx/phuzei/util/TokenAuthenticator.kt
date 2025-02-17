package hu.nickx.phuzei.util

import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import hu.nickx.phuzei.AUTHORIZATION
import hu.nickx.phuzei.REFRESH_URL
import hu.nickx.phuzei.REQUEST_CONTENT_TYPE
import hu.nickx.phuzei.data.model.Token
import hu.nickx.phuzei.data.model.TokenRequest
import hu.nickx.phuzei.data.pref.AppPreferences
import hu.nickx.phuzei.data.pref.token
import javax.inject.Inject


/**
 * Created by Alireza Afkar on 10/8/18.
 */
class TokenAuthenticator @Inject constructor(
        private val gson: Gson,
        private val client: OkHttpClient,
        private val prefs: AppPreferences
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = refreshToken() ?: return null

        return response.request.newBuilder()
                .header(AUTHORIZATION, prefs.token(accessToken))
                .build()
    }

    private fun refreshToken(): String? {
        val token = prefs.refreshToken ?: return null

        val body = gson.toJson(
                TokenRequest(token)
        ).toRequestBody(
                REQUEST_CONTENT_TYPE.toMediaTypeOrNull()
        )

        val request = Request.Builder()
                .url(REFRESH_URL)
                .post(body)
                .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return null
        val tokenResponse = gson.fromJson(responseBody, Token::class.java) ?: return null

        prefs.accessToken = tokenResponse.accessToken
        tokenResponse.refreshToken?.let {
            prefs.refreshToken = it
        }
        return tokenResponse.accessToken
    }

}

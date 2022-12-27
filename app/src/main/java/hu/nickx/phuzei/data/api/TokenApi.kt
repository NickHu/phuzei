package hu.nickx.phuzei.data.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import hu.nickx.phuzei.*
import hu.nickx.phuzei.data.model.Token

/**
 * Created by Alireza Afkar on 14/9/2018AD.
 */
interface TokenApi {

    @FormUrlEncoded
    @POST("https://www.googleapis.com/oauth2/v4/token")
    fun request(
        @Field(CODE) code: String,
        @Field(KEY_GRANT_TYPE) grant_type: String = CODE_GRANT_TYPE,
        @Field(KEY_REDIRECT_URI) redirect_uri: String = REDIRECT_URI,
        @Field(KEY_CLIENT_ID) client_id: String = CLIENT_ID
    ): Single<Token>

    @FormUrlEncoded
    @POST("https://www.googleapis.com/oauth2/v4/token")
    fun refresh(
        @Field(REFRESH_GRANT_TYPE) refresh_token: String?,
        @Field(KEY_GRANT_TYPE) grant_type: String = REFRESH_GRANT_TYPE,
        @Field(KEY_CLIENT_ID) client_id: String = CLIENT_ID
    ): Call<Token>
}

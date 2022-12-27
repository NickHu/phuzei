package hu.nickx.phuzei.data.model

import hu.nickx.phuzei.CLIENT_ID
import hu.nickx.phuzei.REFRESH_GRANT_TYPE

/**
 * Created by Alireza Afkar on 10/16/18.
 */
data class TokenRequest(
    val refresh_token: String,
    val grant_type: String = REFRESH_GRANT_TYPE,
    val client_id: String = CLIENT_ID
)

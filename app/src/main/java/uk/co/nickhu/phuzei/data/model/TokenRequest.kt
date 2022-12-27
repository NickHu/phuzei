package uk.co.nickhu.phuzei.data.model

import uk.co.nickhu.phuzei.CLIENT_ID
import uk.co.nickhu.phuzei.REFRESH_GRANT_TYPE

/**
 * Created by Alireza Afkar on 10/16/18.
 */
data class TokenRequest(
    val refresh_token: String,
    val grant_type: String = REFRESH_GRANT_TYPE,
    val client_id: String = CLIENT_ID
)

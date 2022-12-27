package uk.co.nickhu.phuzei.data.api

import uk.co.nickhu.phuzei.data.model.Album
import uk.co.nickhu.phuzei.data.model.AlbumsResponse
import uk.co.nickhu.phuzei.data.model.SharedAlbumsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Alireza Afkar on 14/9/2018AD.
 */
interface AlbumsApi {
    @GET("albums")
    fun getAlbums(@Query("pageToken") pageToken: String? = null): Single<AlbumsResponse>

    @GET("sharedAlbums")
    fun getSharedAlbums(@Query("pageToken") pageToken: String? = null): Single<SharedAlbumsResponse>

    @GET("albums/{albumId}")
    fun getAlbum(@Path("albumId") id: String): Single<Album>
}

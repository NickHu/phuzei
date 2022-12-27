package hu.nickx.phuzei.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import hu.nickx.phuzei.data.model.Album
import hu.nickx.phuzei.data.model.AlbumsResponse
import hu.nickx.phuzei.data.model.SharedAlbumsResponse

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

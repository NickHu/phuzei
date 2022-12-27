package hu.nickx.phuzei.data.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import hu.nickx.phuzei.data.model.Search
import hu.nickx.phuzei.data.model.SearchResponse

/**
 * Created by Alireza Afkar on 14/9/2018AD.
 */
interface PhotosApi {

    @POST("mediaItems:search")
    fun getAlbumPhotos(@Body search: Search): Single<SearchResponse>

    @POST("./mediaItems:search")
    fun getAlbumPhotosCall(@Body search: Search): Call<SearchResponse>

    @GET("mediaItems")
    fun getPhotos(
        @Query("pageSize") pageSize: Int,
        @Query("pageToken") pageToken: String?
    ): Single<SearchResponse>

    @GET("mediaItems")
    fun getPhotosCall(
        @Query("pageSize") pageSize: Int,
        @Query("pageToken") pageToken: String?
    ): Call<SearchResponse>
}

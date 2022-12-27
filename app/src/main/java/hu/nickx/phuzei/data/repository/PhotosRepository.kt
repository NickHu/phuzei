package hu.nickx.phuzei.data.repository

import io.reactivex.Single
import hu.nickx.phuzei.data.api.PhotosApi
import hu.nickx.phuzei.data.model.*
import hu.nickx.phuzei.util.applyNetworkSchedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class PhotosRepository @Inject constructor(private var api: PhotosApi) {

    fun getAlbumPhotos(
        pageSize: Int,
        albumId: String?,
        category: String,
        pageToken: String? = null
    ): Single<SearchResponse> {
        val search = createSearchBody(pageSize, albumId, category, pageToken)

        return if (search == null) {
            getPhotos(pageSize, pageToken)
        } else {
            api.getAlbumPhotos(search).compose(applyNetworkSchedulers())
        }
    }

    fun getAlbumPhotosSync(
        pageSize: Int,
        albumId: String?,
        category: String,
        pageToken: String? = null
    ): SearchResponse? {
        val search = createSearchBody(pageSize, albumId, category, pageToken)

        return if (search == null) {
            getPhotosAsync(pageSize, pageToken)
        } else {
            return api.getAlbumPhotosCall(search).execute().body()
        }
    }

    private fun getPhotos(pageSize: Int, pageToken: String?): Single<SearchResponse> {
        return api.getPhotos(pageSize, pageToken).compose(applyNetworkSchedulers())
    }

    private fun getPhotosAsync(pageSize: Int, pageToken: String?): SearchResponse? {
        return api.getPhotosCall(pageSize, pageToken).execute().body()
    }

    private fun createSearchBody(
        pageSize: Int,
        albumId: String?,
        category: String,
        pageToken: String? = null
    ): Search? {
        var search = Search(pageToken = pageToken, pageSize = pageSize)

        search = if (albumId.isNullOrEmpty() && category.isEmpty()) {
            return null
        } else if (albumId.isNullOrEmpty()) {
            val filter = if (category.equals(FAVORITES, true)) {
                Filters(featureFilter = FeatureFilter(listOf(FAVORITES)))
            } else {
                Filters(contentFilter = ContentFilter(listOf(category.uppercase(Locale.US))))
            }
            search.copy(filters = filter)
        } else {
            search.copy(albumId = albumId)
        }
        return search
    }
}

package hu.nickx.phuzei.data.repository

import io.reactivex.Single
import hu.nickx.phuzei.data.api.AlbumsApi
import hu.nickx.phuzei.data.model.Album
import hu.nickx.phuzei.data.model.AlbumsResponse
import hu.nickx.phuzei.data.model.SharedAlbumsResponse
import hu.nickx.phuzei.util.applyNetworkSchedulers
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class AlbumsRepository @Inject constructor(private var api: AlbumsApi) {
    fun getAlbums(pageToken: String? = null): Single<AlbumsResponse> {
        return api.getAlbums(pageToken).compose(applyNetworkSchedulers())
    }

    fun getSharedAlbums(pageToken: String? = null): Single<SharedAlbumsResponse> {
        return api.getSharedAlbums(pageToken).compose(applyNetworkSchedulers())
    }

    fun getAlbum(albumId: String): Single<Album> {
        return api.getAlbum(albumId).compose(applyNetworkSchedulers())
    }
}

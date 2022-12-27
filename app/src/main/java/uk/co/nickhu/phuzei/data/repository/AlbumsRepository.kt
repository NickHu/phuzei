package uk.co.nickhu.phuzei.data.repository

import io.reactivex.Single
import uk.co.nickhu.phuzei.data.api.AlbumsApi
import uk.co.nickhu.phuzei.data.model.Album
import uk.co.nickhu.phuzei.data.model.AlbumsResponse
import uk.co.nickhu.phuzei.data.model.SharedAlbumsResponse
import uk.co.nickhu.phuzei.util.applyNetworkSchedulers
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

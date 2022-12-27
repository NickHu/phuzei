package uk.co.nickhu.phuzei.presentation.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import uk.co.nickhu.phuzei.data.model.Album
import uk.co.nickhu.phuzei.data.model.BaseAlbumResponse
import uk.co.nickhu.phuzei.data.pref.AppPreferences
import uk.co.nickhu.phuzei.data.repository.AlbumsRepository
import uk.co.nickhu.phuzei.util.SingleLiveEvent
import uk.co.nickhu.phuzei.util.addTo
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 6/12/2018AD.
 */
class AlbumViewModel @Inject constructor(
    private val prefs: AppPreferences,
    private val repository: AlbumsRepository,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _selectAlbumObservable = SingleLiveEvent<String>()
    val selectAlbumObservable: LiveData<String> = _selectAlbumObservable

    private val _loadingObservable = MutableLiveData<Boolean>()
    val loadingObservable: LiveData<Boolean> = _loadingObservable

    private val _albumsObservable = MutableLiveData<List<Album>>()
    val albumsObservable: LiveData<List<Album>> = _albumsObservable

    private val _errorObservable = SingleLiveEvent<String>()
    val errorObservable: LiveData<String> = _errorObservable

    private val _enqueueImages = SingleLiveEvent<Unit>()
    val enqueueImages: LiveData<Unit> = _enqueueImages

    private var pageToken: String? = null
    val currentAlbum: String? = prefs.album
    private var albumType = AlbumFragment.TYPE_ALBUMS

    fun subscribe(albumType: Int) {
        if (disposable.size() == 0) {
            this.albumType = albumType
            onRefresh()
        }
    }

    fun onSelectAlbum(album: Album) {
        prefs.album = album.id
        prefs.pageToken = null
        loadAlbumImages(album.title)
    }

    fun onRefresh() {
        pageToken = null
        getAlbums()
    }

    fun onLoadMore() {
        if (!pageToken.isNullOrBlank() && loadingObservable.value != true) {
            getAlbums()
        }
    }

    private fun getAlbums() {
        getAlbumsApi()
            .doOnSubscribe { _loadingObservable.value = true }
            .doAfterTerminate { _loadingObservable.value = false }
            .doOnSuccess { pageToken = it.nextPageToken }
            .subscribe({
                if (it.albums.isNullOrEmpty()) {
                    onLoadMore()
                } else {
                    _albumsObservable.value = it.albums
                }
            }, {
                _errorObservable.value = it.localizedMessage
            })
            .addTo(disposable)
    }

    private fun loadAlbumImages(title: String?) {
        title?.let {
            _selectAlbumObservable.value = it
        }
        _enqueueImages.call()
    }

    private fun getAlbumsApi(): Single<out BaseAlbumResponse> {
        return if (albumType == AlbumFragment.TYPE_ALBUMS) {
            repository.getAlbums(pageToken)
                .flatMap {
                    if (pageToken.isNullOrEmpty()) {
                        val items = mutableListOf<Album>().apply {
                            add(
                                Album(
                                    id = "",
                                    itemsCount = "",
                                    coverPhotoUrl = "",
                                    title = "by Category"
                                )
                            )
                            it.albums?.let { albums ->
                                addAll(albums)
                            }
                        }
                        Single.fromCallable { it.copy(albums = items) }
                    } else {
                        Single.fromCallable { it }
                    }
                }
        } else {
            repository.getSharedAlbums(pageToken)
        }
    }

    override fun onCleared() {
        disposable.clear()
    }
}

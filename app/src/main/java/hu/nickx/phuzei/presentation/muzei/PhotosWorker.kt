package hu.nickx.phuzei.presentation.muzei

import android.content.Context
import androidx.core.net.toUri
import androidx.work.*
import com.google.android.apps.muzei.api.provider.Artwork
import com.google.android.apps.muzei.api.provider.ProviderContract
import hu.nickx.phuzei.App
import hu.nickx.phuzei.BuildConfig
import hu.nickx.phuzei.data.model.Media
import hu.nickx.phuzei.data.model.isImage
import hu.nickx.phuzei.data.model.largeUrl
import hu.nickx.phuzei.data.pref.AppPreferences
import hu.nickx.phuzei.data.repository.PhotosRepository
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class PhotosWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @Inject
    lateinit var repository: PhotosRepository

    @Inject
    lateinit var prefs: AppPreferences

    init {
        App.get(context).component?.inject(this)
    }

    override fun doWork(): Result {
        val response = try {
            repository.getAlbumPhotosSync(
                albumId = prefs.album,
                category = prefs.category,
                pageToken = prefs.pageToken,
                pageSize = prefs.imagesCount
            )
        } catch (e: IOException) {
            null
        }

        response?.let {
            prefs.pageToken = it.nextPageToken
            onPhotosResult(
                if (prefs.shuffle) {
                    it.mediaItems.shuffled()
                } else {
                    it.mediaItems
                }
            )
            return Result.success()
        } ?: kotlin.run {
            prefs.pageToken = null
            return Result.failure()
        }
    }

    private fun onPhotosResult(medias: List<Media>) {
        val provider = ProviderContract.getProviderClient(
            applicationContext,
            BuildConfig.PHUZEI_AUTHORITY
        )
        medias.asSequence()
            .filter(Media::isImage)
            .map { photo ->
                Artwork(
                    token = photo.id,
                    title = photo.filename,
                    byline = photo.description,
                    webUri = photo.productUrl.toUri(),
                    persistentUri = photo.largeUrl().toUri()
                )
            }
            .toList().forEach {
                provider.addArtwork(it)
            }
    }

    companion object {
        internal fun enqueueLoad(context: Context, clearCurrentImages: Boolean) {
            if (clearCurrentImages) {
                ProviderContract.getProviderClient(context, BuildConfig.PHUZEI_AUTHORITY).run {
                    context.contentResolver.delete(contentUri, null, null)
                }
            }

            WorkManager.getInstance(context).run {
                enqueue(
                    OneTimeWorkRequestBuilder<PhotosWorker>()
                        .setConstraints(
                            Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                        )
                        .build()
                )
            }
        }
    }
}

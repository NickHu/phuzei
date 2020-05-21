package com.alirezaafkar.phuzei.presentation.setting

import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alirezaafkar.phuzei.MUZEI_PACKAGE_NAME
import com.alirezaafkar.phuzei.data.pref.AppPreferences
import com.alirezaafkar.phuzei.presentation.muzei.PhotosWorker
import com.alirezaafkar.phuzei.util.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class SettingsViewModel @Inject constructor(
    private val prefs: AppPreferences
) : ViewModel() {

    private val _logoutObservable = SingleLiveEvent<Unit>()
    val logoutObservable: LiveData<Unit> = _logoutObservable

    private val _isShuffleObservable = SingleLiveEvent<Boolean>()
    val isShuffleObservable: LiveData<Boolean> = _isShuffleObservable

    private val _categoryObservable = SingleLiveEvent<String>()
    val categoryObservable: LiveData<String> = _categoryObservable

    private val _intentObservable = SingleLiveEvent<Intent>()
    val intentObservable: LiveData<Intent> = _intentObservable

    fun subscribe() {
        _isShuffleObservable.value = prefs.shuffle
        _categoryObservable.value = prefs.category
    }

    fun onShuffleOrder(shuffle: Boolean) {
        prefs.shuffle = shuffle
        PhotosWorker.enqueueLoad()
    }

    fun onSelectCategory(category: String) {
        prefs.category = category
        _categoryObservable.value = category
        PhotosWorker.enqueueLoad()
    }

    fun onContact() {
        Intent(Intent.ACTION_SENDTO, "mailto:".toUri()).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf("pesiran@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Phuzei")
        }.also {
            _intentObservable.value = it
        }

    }

    fun onMuzeiClick(packageManager: PackageManager) {
        var intent = packageManager.getLaunchIntentForPackage(MUZEI_PACKAGE_NAME)
        if (intent == null) {
            intent = Intent(Intent.ACTION_VIEW).apply {
                data = "market://details?id=$MUZEI_PACKAGE_NAME".toUri()
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
        _intentObservable.value = intent
    }

    fun onLogout() {
        prefs.logout()
        _logoutObservable.value = null
    }
}
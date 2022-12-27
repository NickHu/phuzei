package uk.co.nickhu.phuzei.presentation.setting

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uk.co.nickhu.phuzei.R
import uk.co.nickhu.phuzei.data.pref.AppPreferences
import uk.co.nickhu.phuzei.util.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class SettingsViewModel @Inject constructor(
    private val prefs: AppPreferences
) : ViewModel() {

    private val _logoutObservable = SingleLiveEvent<Unit>()
    val logoutObservable: LiveData<Unit> = _logoutObservable

    private val _isShuffleObservable = MutableLiveData<Boolean>()
    val isShuffleObservable: LiveData<Boolean> = _isShuffleObservable

    private val _categoryObservable = MutableLiveData<Int>()
    val categoryObservable: LiveData<Int> = _categoryObservable

    private val _intentObservable = SingleLiveEvent<Intent>()
    val intentObservable: LiveData<Intent> = _intentObservable

    private val _imagesCountObservable = MutableLiveData<Int>()
    val imagesCountObservable: LiveData<Int> = _imagesCountObservable

    private val _enqueueImages = SingleLiveEvent<Unit>()
    val enqueueImages: LiveData<Unit> = _enqueueImages

    fun subscribe() {
        _isShuffleObservable.value = prefs.shuffle
        _categoryObservable.value = prefs.categoryIndex
        _imagesCountObservable.value = prefs.imagesCount
    }

    fun onShuffleOrder(shuffle: Boolean) {
        if (shuffle != prefs.shuffle) {
            prefs.shuffle = shuffle
            _enqueueImages.call()
        }
    }

    fun onSelectCategory(categoryIndex: Int) {
        if (categoryIndex != prefs.categoryIndex) {
            prefs.categoryIndex = categoryIndex
            _categoryObservable.value = prefs.categoryIndex
            _enqueueImages.call()
        }
    }

    fun onImagesCount(count: Int) {
        prefs.imagesCount = count
    }

    fun onContact() {
        Intent(Intent.ACTION_SENDTO, "mailto:".toUri()).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.support_email))
            putExtra(Intent.EXTRA_SUBJECT, "Phuzei")
        }.also {
            _intentObservable.value = it
        }

    }

    fun onLogout() {
        prefs.logout()
        _logoutObservable.call()
    }
}

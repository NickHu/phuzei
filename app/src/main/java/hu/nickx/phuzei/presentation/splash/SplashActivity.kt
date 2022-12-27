package hu.nickx.phuzei.presentation.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hu.nickx.phuzei.App
import hu.nickx.phuzei.presentation.login.LoginActivity
import hu.nickx.phuzei.presentation.main.MainActivity
import javax.inject.Inject

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SplashViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(this).component?.inject(this)
        super.onCreate(savedInstanceState)

        viewModel.loginActivityObservable.observe(this) {
            LoginActivity.start(this)
            finish()
        }

        viewModel.mainActivityObservable.observe(this) {
            MainActivity.start(this)
            finish()
        }

        viewModel.subscribe()
    }
}

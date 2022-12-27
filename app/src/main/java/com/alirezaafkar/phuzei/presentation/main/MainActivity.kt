package com.alirezaafkar.phuzei.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alirezaafkar.phuzei.App
import com.alirezaafkar.phuzei.R
import com.alirezaafkar.phuzei.databinding.ActivityMainBinding
import com.alirezaafkar.phuzei.presentation.album.AlbumFragment
import com.alirezaafkar.phuzei.presentation.setting.SettingsFragment

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(this).component?.inject(this)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.navigation.setOnNavigationItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.action_settings -> SettingsFragment.newInstance()
                R.id.action_shared_albums -> AlbumFragment.newInstance(AlbumFragment.TYPE_SHARED_ALBUMS)
                else -> AlbumFragment.newInstance(AlbumFragment.TYPE_ALBUMS)
            }
            replaceFragment(fragment)
            true
        }
        binding.navigation.selectedItemId = R.id.action_albums
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}

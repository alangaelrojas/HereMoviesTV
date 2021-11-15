package com.alan.alantv.app.browsemovie

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.alan.alantv.R
import com.alan.alantv.app.browsemovie.browermovies.BrowserMoviesFragment

/**
 loads [BrowserMoviesFragment]
 */
class BrowserMoviesActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.browser_container, BrowserMoviesFragment())
                .commitNow()
        }
    }
}
package com.arctouch.codechallenge.ui.splash

import android.content.Intent
import android.os.Bundle
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.service.TmdbApi
import com.arctouch.codechallenge.ui.base.BaseActivity
import com.arctouch.codechallenge.data.model.Cache
import com.arctouch.codechallenge.ui.home.HomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Cache.cacheGenres(it.genres)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
    }
}

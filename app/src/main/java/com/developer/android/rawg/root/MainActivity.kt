package com.developer.android.rawg.root

import android.os.Bundle
import com.developer.android.rawg.R
import com.developer.android.rawg.common.mvp.BaseActivity
import com.developer.android.rawg.main.ui.main.AllGamesFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val allGamesFragment = AllGamesFragment()
        changeFragment(allGamesFragment, R.id.fragmentContainer)
    }
}
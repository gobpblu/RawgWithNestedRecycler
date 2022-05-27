package com.developer.android.rawg.main.ui.main

import com.developer.android.rawg.common.mvp.BaseFragmentContract
import com.developer.android.rawg.common.mvp.MvpPresenter
import com.developer.android.rawg.common.mvp.MvpView
import com.developer.android.rawg.common.ui.recyclerview.PagingState
import com.developer.android.rawg.main.model.GameTypes
import com.developer.android.rawg.main.model.genres.Genres


interface MainContract : BaseFragmentContract {

    interface View : MvpView {
        fun showGenres(genres: Genres)
        fun showGames(games: List<GameTypes?>)
        fun showPagingState(state: PagingState)
        fun showRefreshing(isRefreshing: Boolean)
        fun showErrorMessage(e: Throwable)
    }

    interface Presenter : MvpPresenter<View> {
        fun getGenres()
        suspend fun getGames(page: Int = 1, genre: String): List<GameTypes?>
        fun refresh(adapterIndex: Int, page: Int = 1, genres: String)
    }

}
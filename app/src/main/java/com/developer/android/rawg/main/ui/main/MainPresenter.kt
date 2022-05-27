package com.developer.android.rawg.main.ui.main

import com.developer.android.rawg.common.mvp.BasePresenter
import com.developer.android.rawg.common.ui.recyclerview.PagingState
import com.developer.android.rawg.main.interactor.MainInteractor
import com.developer.android.rawg.main.model.GameTypes
import com.developer.android.rawg.utils.Utils
import kotlinx.coroutines.*
import timber.log.Timber

class MainPresenter(
    private val interactor: MainInteractor,
) : BasePresenter<MainContract.View>(),
    MainContract.Presenter {
    private var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    private var games = mutableListOf<GameTypes?>()
    private var paginationEnded = false

    override fun getGenres() {

        coroutineScope.launch {
            try {
                val genres = interactor.getGenres()
                view?.showGenres(genres)
            } catch (e: CancellationException) {
                Timber.e("Cancelled loading request")
            } catch (t: Throwable) {
                Timber.e(t.message)
            }
        }
    }

    override suspend fun getGames(page: Int, genre: String): List<GameTypes?> {
//        if (paginationEnded) return

        val games = coroutineScope.async {
//            try {
//                view?.showPagingState(PagingState.Loading)
                val data = interactor.getGames(page, genre, Utils.TYPE_OF_VIEW_FULL_GAMES)
                /*if (data.count == 0) {
                    paginationEnded = true
                } else {*/
                    data.games
/*//                }
            } catch (e: CancellationException) {
                Timber.e("Cancelled loading request")
            } catch (t: Throwable) {
                Timber.e(t.message)
//                view?.showPagingState(PagingState.Error(t))
            }*/
        }
        return games.await()
    }

    override fun refresh(adapterIndex: Int, page: kotlin.Int, genres: String) {
        paginationEnded = false

        coroutineScope.launch {
            view?.showRefreshing(true)
            getGames(page, genres)
            view?.showRefreshing(false)
        }
    }
}
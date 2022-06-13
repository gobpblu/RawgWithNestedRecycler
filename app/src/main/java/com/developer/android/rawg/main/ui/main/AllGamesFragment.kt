package com.developer.android.rawg.main.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.android.rawg.R
import com.developer.android.rawg.common.mvp.BaseFragmentMvp
import com.developer.android.rawg.common.ui.recyclerview.PagingState
import com.developer.android.rawg.databinding.FragmentAllGamesBinding
import com.developer.android.rawg.main.model.GameTypes
import com.developer.android.rawg.main.model.genres.GameGenre
import com.developer.android.rawg.main.model.genres.Genres
import com.developer.android.rawg.main.ui.main.adapter.MainAdapter
import org.koin.android.ext.android.inject

class AllGamesFragment :
    BaseFragmentMvp<MainContract.View, MainContract.Presenter>(R.layout.fragment_all_games),
    MainContract.View {

    override val presenter: MainContract.Presenter by inject()

    private val binding: FragmentAllGamesBinding by lazy {
        FragmentAllGamesBinding.inflate(layoutInflater)
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(binding.recyclerViewOuter.context)
    }

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(
            getGamesByGenre = { getGamesByGenre(it) },
            onGameItemClicked = { onGameItemClicked(it) },
            onFailedListener = { onFailedListener() }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

            if (recyclerViewOuter.layoutManager != linearLayoutManager)
                recyclerViewOuter.apply {
                layoutManager = linearLayoutManager
                adapter = mainAdapter
            }
        presenter.getGenres()
        /*swipeRefresh.setDistanceToTriggerSync(5)
        swipeRefresh.setProgressViewEndTarget(false, 50)
        swipeRefresh.setOnRefreshListener {
            for (index in adaptersList.indices) {
                adaptersList[index].clearItems()
                presenter.refresh(index, genres = GamesGenres.values()[index].genre)
            }
        }*/

    }

    override fun showGenres(genres: Genres) {
        mainAdapter.setGenres(genres.gamesGenres)
    }

    override fun showGames(games: List<GameTypes?>, position: Int) {
        mainAdapter.setGames(games, position)
    }

    private fun getGamesByGenre(gameGenre: GameGenre) =
        presenter.getGames(gameGenre)




    private fun onGameItemClicked(gameDetails: GameTypes.FullGame) {
        val fragment = GameDetailsFragment.newInstance(gameDetails)
        parentFragmentManager.findFragmentById(R.id.fragmentContainer)?.let {
            hideAndAddFragment(hideFragment = it,
                addFragment = fragment,
                id = R.id.fragmentContainer)
        }
    }

    private fun onFailedListener() {
        Toast.makeText(context, R.string.loading_error, Toast.LENGTH_SHORT)
            .show()
    }

    override fun showRefreshing(isRefreshing: Boolean) {
//        binding.swipeRefresh.isRefreshing = isRefreshing
    }

    override fun showErrorMessage(e: Throwable) {
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }

    override fun showPagingState(pagingState: PagingState, position: Int) {
        mainAdapter.setPagingState(pagingState, position)
    }

}
package com.developer.android.rawg.main.ui.main.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.android.rawg.common.mvp.BaseViewHolder
import com.developer.android.rawg.databinding.RecyclerGenreItemBinding
import com.developer.android.rawg.main.model.GameTypes
import com.developer.android.rawg.main.model.genres.GameGenre
import com.developer.android.rawg.main.ui.main.MainScrollListener
import timber.log.Timber

class GenreViewHolder(
    binding: RecyclerGenreItemBinding,
    onGameItemClicked: (GameTypes.FullGame) -> Unit,
    onFailedListener: () -> Unit,
    private val getGamesByGenre: (GameGenre) -> Unit,
) : BaseViewHolder<RecyclerGenreItemBinding, GameGenre>(binding) {


    constructor(
        parent: ViewGroup,
        onGameItemClicked: (GameTypes.FullGame) -> Unit,
        onFailedListener: () -> Unit,
        getGamesByGenre: (GameGenre) -> Unit,
    ) : this(
        RecyclerGenreItemBinding.inflate(LayoutInflater.from(
            parent.context), parent, false),
        onGameItemClicked, onFailedListener, getGamesByGenre)

    private val nestedAdapter = NestedAdapter(onGameItemClicked, onFailedListener)

    private val linearLayoutManager: LinearLayoutManager

    init {
        with(binding.genreRecyclerView) {
            linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = nestedAdapter
            layoutManager = linearLayoutManager
        }
        Timber.tag("%%%").i("item")
    }

    override fun onBind(item: GameGenre) = with(binding) {
        super.onBind(item)

        Timber.tag("%%%").i("$item")
        genreRecyclerView.addOnScrollListener(MainScrollListener(
            linearLayoutManager,
            loadNextPage = {getGamesByGenre(it)},
            gameGenre = item
        ))

        textViewGenreTitle.text = item.name
        if (item.position == -1) {
            item.position = layoutPosition
            getGamesByGenre(item)
        }
        genreRecyclerView.restoreState(item.state)
//        linearLayoutManager.scrollToPosition(item.latestScrollPosition)
        nestedAdapter.setItems(item.gamesList as List<GameTypes.FullGame>)
    }

    override fun onViewDetached() {
        item.state = binding.genreRecyclerView.layoutManager?.onSaveInstanceState()
    }

    private fun RecyclerView.restoreState(parcelable: Parcelable?) {
        if (parcelable == null) return
        layoutManager?.onRestoreInstanceState(parcelable)
    }

}
package com.developer.android.rawg.main.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.android.rawg.databinding.RecyclerGenreItemBinding
import com.developer.android.rawg.main.model.GameTypes
import com.developer.android.rawg.main.model.genres.GenreGame

class GenreViewHolder(
    private val binding: RecyclerGenreItemBinding,
    private val onGameItemClicked: (GameTypes.FullGame) -> Unit,
    private val onFailedListener: () -> Unit,
    private val getGamesByGenre: (GenreGame) -> List<GameTypes>
): RecyclerView.ViewHolder(binding.root) {

    private val nestedAdapter: NestedAdapter by lazy {
        NestedAdapter(getGamesByGenre, onGameItemClicked, onFailedListener)
    }

    init {
        binding.genreRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = nestedAdapter
        }
    }

    constructor(
        parent: ViewGroup,
        onGameItemClicked: (GameTypes.FullGame) -> Unit,
        onFailedListener: () -> Unit,
        getGamesByGenre: (GenreGame) -> List<GameTypes>
    ) : this(
        RecyclerGenreItemBinding.inflate(LayoutInflater.from(
            parent.context), parent, false),
    onGameItemClicked, onFailedListener, getGamesByGenre)

    fun bind(genreGame: GenreGame) = with(binding) {
        binding.textViewGenreTitle.text = genreGame.name
        val games = getGamesByGenre(genreGame)
        nestedAdapter.setItems(games)
    }
}
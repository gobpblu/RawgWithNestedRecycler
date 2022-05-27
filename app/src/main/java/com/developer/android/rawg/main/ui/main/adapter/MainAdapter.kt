package com.developer.android.rawg.main.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.android.rawg.main.model.GameTypes
import com.developer.android.rawg.main.model.genres.GenreGame
import com.developer.android.rawg.main.model.genres.Genres

class MainAdapter(
    private val getGamesByGenre: (GenreGame) -> Unit,
    private val onGameItemClicked: (GameTypes.FullGame) -> Unit,
    private val onFailedListener: () -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<GenreGame>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenreViewHolder(parent, onGameItemClicked, onFailedListener, getGamesByGenre)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GenreViewHolder -> holder.bind(genreGame = data[position])
        }
    }

    override fun getItemCount() = data.size

    fun setItems(genres: Genres) {
        data.addAll(genres.games)
    }
}
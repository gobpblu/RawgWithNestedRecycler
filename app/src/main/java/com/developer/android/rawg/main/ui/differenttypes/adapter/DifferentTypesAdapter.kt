package com.developer.android.rawg.main.ui.differenttypes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.developer.android.rawg.R
import com.developer.android.rawg.main.model.GameTypes
import com.developer.android.rawg.main.model.games.Games


class DifferentTypesAdapter(
    private val onClick: (GameTypes) -> Unit,
    private val getGames: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val gamesList = mutableListOf<GameTypes?>()
    private var next: String? = null
    private var page: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.game_item_full -> FullViewHolder(itemView.inflate(R.layout.game_item_full, parent, false))
            R.layout.game_item_image -> ImageViewHolder(itemView.inflate(R.layout.game_item_image, parent, false))
            R.layout.game_item_description -> DescriptionViewHolder(itemView.inflate(R.layout.game_item_description, parent, false))
            else -> throw IllegalStateException("Something went wrong")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FullViewHolder -> holder.bind(gamesList[position] as GameTypes.FullGame)
            is ImageViewHolder -> holder.bind(gamesList[position] as GameTypes.ImageGame)
            is DescriptionViewHolder -> holder.bind(gamesList[position] as GameTypes.DescriptionGame)
        }
        holder.itemView.setOnClickListener { gamesList[position]?.let { it1 -> onClick.invoke(it1) } }
        val anim: Animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.all_games_animation)
        holder.itemView.startAnimation(anim)
        if (gamesList.size - position < 5 && next != null) getGames.invoke(++page)
    }

    override fun getItemCount() = gamesList.size

    override fun getItemViewType(position: Int): Int {
        return when(gamesList[position]) {
            is GameTypes.FullGame -> R.layout.game_item_full
            is GameTypes.ImageGame -> R.layout.game_item_image
            is GameTypes.DescriptionGame -> R.layout.game_item_description
            else -> {throw IllegalStateException("Type not found")}
        }

    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is FullViewHolder -> holder.detach()
            is ImageViewHolder -> holder.detach()
        }
    }

    fun addData(games: Games) {
        gamesList.addAll(games.games)
        next = games.next
        notifyDataSetChanged()
    }
}
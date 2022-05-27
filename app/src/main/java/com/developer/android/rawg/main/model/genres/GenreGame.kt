package com.developer.android.rawg.main.model.genres

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreGame(
    val name: String,
    val slug: String,
    var page: Int = 1
) : Parcelable

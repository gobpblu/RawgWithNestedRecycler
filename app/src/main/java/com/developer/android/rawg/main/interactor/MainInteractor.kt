package com.developer.android.rawg.main.interactor

import com.developer.android.rawg.main.repository.MainRemoteRepository

class MainInteractor(
    private val remoteRepository: MainRemoteRepository,
) {
    suspend fun getGames(page: Int, genres: String) =
        remoteRepository.getGames(page, genres)

    suspend fun getGenres() =
        remoteRepository.getGenres()
}
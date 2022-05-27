package com.developer.android.rawg.main.interactor

import com.developer.android.rawg.main.repository.MainRemoteRepository

class MainInteractor(
    private val remoteRepository: MainRemoteRepository,
) {
    suspend fun getGames(page: Int, genres: String, typeOfView: Int) =
        remoteRepository.getGames(page, genres, typeOfView)

    suspend fun getGenres() =
        remoteRepository.getGenres()
}
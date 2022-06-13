package com.developer.android.rawg.main.model

import com.developer.android.rawg.main.api.model.games.*
import com.developer.android.rawg.main.api.model.genres.GenreDetailsResponse
import com.developer.android.rawg.main.api.model.genres.GenresResponse
import com.developer.android.rawg.main.model.games.*
import com.developer.android.rawg.main.model.genres.GameGenre
import com.developer.android.rawg.main.model.genres.Genres
import com.developer.android.rawg.utils.Utils

object MainConverter {

    fun fromNetwork(response: GamesResponse) =
        Games(
            count = response.count,
            next = response.next,
            previous = response.previous,
            games = gamesFromNetwork(response.games)
        )

    fun fromNetwork(response: GenresResponse) =
        Genres(
            count = response.count,
            gamesGenres = genreGamesFromNetwork(response.genres)
        )

    fun gamesFromNetwork(games: List<GameDetailsResponse>) =
        games.map {
            toFullGame(it, Utils.GAME_INFO_FULL)
        }

    fun fromNetworkWithDifferentTypes(response: GamesResponse) =
        Games(
            count = response.count,
            next = response.next,
            previous = response.previous,
            games = gamesFromNetworkWithDifferentTypes(response.games)
        )

    fun gamesFromNetworkWithDifferentTypes(games: List<GameDetailsResponse>) =
        games.map {
            when (val type = listOf(2, 3, 4).random()) {
                Utils.GAME_INFO_FULL -> toFullGame(it, type)
                Utils.GAME_INFO_IMAGE -> toImageGame(it, type)
                Utils.GAME_INFO_DESCRIPTION -> toDescriptionGame(it, type)
                else -> null
            }
        }

    private fun toFullGame(
        game: GameDetailsResponse,
        type: Int,
    ) = GameTypes.FullGame(
        name = game.name,
        released = game.released,
        playTime = game.playTime,
        backgroundImage = game.backgroundImage,
        rating = game.rating,
        metaCritic = game.metaCritic,
        updated = game.updated,
        shortScreenshots = shortScreenshotsFromNetwork(game.shortScreenshots),
        parentPlatforms = parentPlatformsFromNetwork(game.parentPlatforms),
        type = type
    )

    private fun toImageGame(
        game: GameDetailsResponse,
        type: Int,
    ) = GameTypes.ImageGame(
        backgroundImage = game.backgroundImage,
        type = type
    )

    private fun toDescriptionGame(
        game: GameDetailsResponse,
        type: Int,
    ) = GameTypes.DescriptionGame(
        name = game.name,
        released = game.released,
        playTime = game.playTime,
        parentPlatforms = parentPlatformsFromNetwork(game.parentPlatforms),
        type = type
    )

    private fun shortScreenshotsFromNetwork(shortScreenshots: List<ShortScreenshotResponse>) =
        shortScreenshots.map {
            ShortScreenshot(
                id = it.id,
                image = it.image
            )
        }

    private fun parentPlatformsFromNetwork(parentPlatforms: List<ParentPlatformContainerResponse>) =
        parentPlatforms.map {
            ParentPlatformContainer(
                parentPlatform = fromNetworkWithDifferentTypes(it.parentPlatform)
            )
        }

    private fun fromNetworkWithDifferentTypes(parentPlatformResponse: ParentPlatformResponse) =
        ParentPlatform(
            id = parentPlatformResponse.id,
            name = parentPlatformResponse.name,
            slug = parentPlatformResponse.slug
        )

    private fun genreGamesFromNetwork(genres: List<GenreDetailsResponse>) =
        genres.map {
            GameGenre(
                name = it.name,
                slug = it.slug
            )
        }
}

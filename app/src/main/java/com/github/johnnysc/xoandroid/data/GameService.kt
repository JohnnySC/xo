package com.github.johnnysc.xoandroid.data

import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * @author Asatryan on 18.01.20
 */
interface GameService {

    @Headers("content-type: application/json")
    @GET("/game/start/{id}")
    fun startNewGameAsync(
        @Path("id") id: Long
    ): Deferred<GameDTO>

    @Headers("content-type: application/json")
    @GET("/game")
    fun getUpdateAsync(
    ): Deferred<GameDTO>

    @Headers("content-type: application/json")
    @POST("/game/addCell")
    fun fillCellAsync(
        @Body cellDTO: CellDTO
    ): Deferred<GameDTO>
}
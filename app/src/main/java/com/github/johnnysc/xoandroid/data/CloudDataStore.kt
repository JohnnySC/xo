package com.github.johnnysc.xoandroid.data

/**
 * @author Asatryan on 18.01.20
 */
class CloudDataStore(private val service: GameService) {

    suspend fun startGame(userId: Long) = service.startNewGameAsync(userId).await()

    suspend fun getUpdate() = service.getUpdateAsync().await()

    suspend fun fillCell(cellDTO: CellDTO) = service.fillCellAsync(cellDTO).await()
}
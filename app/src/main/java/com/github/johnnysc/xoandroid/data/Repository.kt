package com.github.johnnysc.xoandroid.data

/**
 * @author Asatryan on 18.01.20
 */
class Repository(val prefManager: PrefManager, private val cloudDataStore: CloudDataStore) {

    var gameCache: GameDTO? = null

    suspend fun startNewGame(): GameDTO {
        val game = cloudDataStore.startGame(getUserId())
        gameCache = game
        return gameCache!!
    }

    suspend fun getUpdate(): GameDTO {
        val game = cloudDataStore.getUpdate()
        gameCache = game
        return gameCache!!
    }

    suspend fun fillCell(id: Int) : GameDTO {
        val game = cloudDataStore.fillCell(CellDTO(id, getUserId()))
        gameCache = game
        return gameCache!!
    }

    fun generateUserId() {
        if (getUserId() < 1)
            prefManager.writeLong(PrefManager.USER_ID, System.currentTimeMillis())
    }

    fun getUserId() = prefManager.readLong(PrefManager.USER_ID)
}
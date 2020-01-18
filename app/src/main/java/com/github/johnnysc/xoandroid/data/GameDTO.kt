package com.github.johnnysc.xoandroid.data

import com.google.gson.annotations.SerializedName

/**
 * @author Asatryan on 18.01.20
 */
data class GameDTO(
    @SerializedName("firstPlayerId") val firstPlayerId: Long,
    @SerializedName("secondPlayerId") val secondPlayerId: Long,
    @SerializedName("state") val state: String,
    @SerializedName("nowPlayingPlayerId") val nowPlayingPlayerId: Long,
    @SerializedName("winnerId") val winnderId: Long,
    @SerializedName("cells") val cells: ArrayList<CellDTO>
)

data class CellDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("playerId") val playerId: Long
)
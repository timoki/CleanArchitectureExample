package com.example.data.db.dao.live

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entity.live.LiveRemoteKey

@Dao
interface LiveRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLiveKeysAll(remoteKey: List<LiveRemoteKey>)

    @Query("SELECT * FROM liveRemoteKeys WHERE liveId = :liveId")
    suspend fun remoteKeysLiveId(liveId: Long): LiveRemoteKey?

    @Query("DELETE FROM liveRemoteKeys")
    suspend fun clearRemoteKeys()
}
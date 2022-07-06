package com.example.data.db.dao.live

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entity.live.LiveRemoteKey

@Dao
interface LiveRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLiveKeys(remoteKey: LiveRemoteKey)

    @Query("SELECT * FROM liveRemoteKeys WHERE keyId = :keyId")
    suspend fun remoteKeysWhereKeyId(keyId: Int): LiveRemoteKey?

    @Query("SELECT * FROM liveRemoteKeys WHERE liveId = :liveId")
    suspend fun remoteKeysWhereLiveId(liveId: String): LiveRemoteKey?

    @Query("DELETE FROM liveRemoteKeys")
    suspend fun clearRemoteKeys()
}
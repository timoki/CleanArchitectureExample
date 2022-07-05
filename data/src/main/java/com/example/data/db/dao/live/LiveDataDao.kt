package com.example.data.db.dao.live

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.db.entity.live.LiveListEntity

@Dao
interface LiveDataDao {
    @Query("SELECT * FROM liveData")
    fun getLiveDataAll(): PagingSource<Int, LiveListEntity>

    @Query("SELECT * FROM liveData WHERE id = :idValue")
    fun getLiveDataFromId(idValue: Int): LiveListEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLiveData(liveData: List<LiveListEntity>)

    @Delete
    suspend fun deleteLiveData(liveData: List<LiveListEntity>)

    @Query("DELETE FROM liveData")
    suspend fun deleteLiveDataAll()
}
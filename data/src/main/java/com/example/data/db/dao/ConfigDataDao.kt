package com.example.data.db.dao

import androidx.room.*
import com.example.data.db.entity.config.ConfigDataEntity

/**
 * Room 에서 작업 할 Config 관련 CRUD 를 정의한다.
 * @Query 어노테이션의 경우 query 를 작성하여야 하며 @Insert, @Delete 어노테이션은 따로 쿼리를 작성하지 않아도 동작한다.
 * Entity Data Class 는 무조건 Dao 에서만 접근해야 한다.
 * */
@Dao
interface ConfigDataDao {
    @Query("SELECT * FROM 'configData' WHERE 'id' = 1")
    suspend fun getConfigDataLocal(): ConfigDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfigDataLocal(configData: ConfigDataEntity)

    @Delete
    suspend fun deleteConfigDataLocal(configData: ConfigDataEntity)
}
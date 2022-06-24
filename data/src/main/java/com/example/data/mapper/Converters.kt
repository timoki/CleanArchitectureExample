package com.example.data.mapper

import androidx.room.TypeConverter
import com.example.data.db.entity.config.MainEntity
import com.example.domain.model.config.MainModel
import com.google.gson.Gson

/**
 * Local Database 에서 값을 가져오거나 저장할때 List 형식은 저장이 되지 않기 때문에 Converting 을 해주어야 한다.
 * Converting 이 필요한 모든 클래스를 작성하여야 한다.
 * Database Class 에서 해당 클래스를 @TypeConverters 어노테이션에 등록 해준다.
 * */
class Converters {
    @TypeConverter
    fun stringListToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun stringJsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun anyListToJson(value: List<Any>?) = Gson().toJson(value)

    @TypeConverter
    fun anyJsonToList(value: String) = Gson().fromJson(value, Array<Any>::class.java).toList()

    @TypeConverter
    fun bannerAndroidMainListToJson(value: List<MainEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun bannerAndroidMainJsonToList(value: String) = Gson().fromJson(value, Array<MainModel>::class.java).toList()
}
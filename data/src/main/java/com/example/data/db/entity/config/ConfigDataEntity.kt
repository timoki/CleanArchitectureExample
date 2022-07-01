package com.example.data.db.entity.config

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity 는 Local Database Data Class 라고 보면 된다.
 * */
@Entity(tableName = "configData")
data class ConfigDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 1,
    @ColumnInfo(name = "idx")
    val idx: Int,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "result")
    val result: Boolean,
    @ColumnInfo(name = "appMutex")
    val appMutex: Boolean,
    @ColumnInfo(name = "broadcast")
    val broadcast: Boolean,
    @ColumnInfo(name = "ivsAutoMaxQuality")
    val ivsAutoMaxQuality: Int,
    @ColumnInfo(name = "ivsStartQuality")
    val ivsStartQuality: Int,
    @ColumnInfo(name = "userIp")
    val userIp: String,
    @Embedded // Converters Class 에 Json 변환 방식을 적지 않으면 @Embedded 어노테이션을 써야 함, @ColumnInfo 어노테이션은 같이 쓸 수 없으며 configData Table 에 AdultCheckEntity Class 의 데이터 항목이 별도로 들어감(chatMessage, post, recom)
    val adultCheck: AdultCheckEntity,
    @ColumnInfo(name = "categoryNew") // Converters Class 에 Json 변환 방식을 입력하여 자동으로 Json(String) 으로 변환되어 들어가고 나올때 List<CategoryNewEntity> 형식으로 배출됨
    val categoryNew: List<CategoryNewEntity>,
    @ColumnInfo(name = "banner") // 다중 Class 의 경우에도 Json 으로 처리하면 간편함(Embedded 처리 시 하위 클래스에 모두 Embedded 처리를 해야함)
    val banner: BannerEntity,
    @ColumnInfo(name = "link")
    val link: LinkEntity,
    @ColumnInfo(name = "socialLogin")
    val socialLogin: Map<String, Boolean>

    // 위에서 다른 경우를 다 변환 시켯으니 밑의 목록은 작업 X (연습이기 때문에 의미가 없음 반복)
    /*val chatMessage: ChatMessageModel,
    val chatMessageReplace: ChatMessageReplaceModel,
    val debug: DebugModel,,
    val newChat: NewChatModel,
    val server: ServerModel,
    val socialLogin: SocialLoginModel,
    val socialServiceUrl: SocialServiceUrlModel,*/
)
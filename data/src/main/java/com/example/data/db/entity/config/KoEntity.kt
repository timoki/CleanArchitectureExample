package com.example.data.db.entity.config

import androidx.room.Embedded

data class KoEntity(
    @Embedded
    val BlindOff: ReplaceDataEntity,
    @Embedded
    val BlindOn: ReplaceDataEntity,
    @Embedded
    val FanIn: ReplaceDataEntity,
    @Embedded
    val FanUp: ReplaceDataEntity,
    @Embedded
    val FreezeOff: ReplaceDataEntity,
    @Embedded
    val FreezeOn: ReplaceDataEntity,
    @Embedded
    val Info: ReplaceDataEntity,
    @Embedded
    val ItemCoin: ReplaceDataEntity,
    @Embedded
    val KingFanIn: ReplaceDataEntity,
    @Embedded
    val KingFanUp: ReplaceDataEntity,
    @Embedded
    val ManagerIn: ReplaceDataEntity,
    @Embedded
    val ManagerOff: ReplaceDataEntity,
    @Embedded
    val ManagerOn: ReplaceDataEntity,
    @Embedded
    val Recommend: ReplaceDataEntity,
    @Embedded
    val SponCoin: ReplaceDataEntity,
    @Embedded
    val VipIn: ReplaceDataEntity
)
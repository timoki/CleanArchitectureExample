package com.example.data.mapper

import com.example.data.db.entity.config.*
import com.example.data.model.response.*
import com.example.domain.model.config.*

/**
 * Local DB DataClass, Remote DataClass, UI DataClass 3개의 타입을 서로 변환하기 위한 Mapper Class
 * Remote 처리 단(ApiService -> RemoteDataSource), Local 처리 단(Dao -> LocalDataSource), UI 적용 단(ViewModel -> View) 에서 처리하는 데이터 클래스가 다르기 때문에 Repository 단에서 매핑 처리를 한다.
 * */
object ObjectMapper {

    fun ConfigDataEntity.toConfigDataModel(): ConfigDataModel = ConfigDataModel(
        message = this.message,
        result = this.result,
        idx = this.idx,
        appMutex = this.appMutex,
        broadcast = this.broadcast,
        ivsAutoMaxQuality = this.ivsAutoMaxQuality,
        ivsStartQuality = this.ivsStartQuality,
        userIp = this.userIp,
        adultCheck = this.adultCheck.toModel(),
        categoryNew = this.categoryNew.toCategoryNewModelListFromEntity(),
        banner = this.banner.toModel()
    )

    fun ConfigData.toConfigDataModel(): ConfigDataModel = ConfigDataModel(
        message = this.message,
        result = this.result,
        idx = this.idx,
        appMutex = this.appMutex,
        broadcast = this.broadcast,
        ivsAutoMaxQuality = this.ivsAutoMaxQuality,
        ivsStartQuality = this.ivsStartQuality,
        userIp = this.userIp,
        adultCheck = this.adultCheck.toModel(),
        categoryNew = this.categoryNew.toCategoryNewModelList(),
        banner = this.banner.toModel()
    )

    fun ConfigData.toConfigDataEntity(): ConfigDataEntity = ConfigDataEntity(
        id = 1,
        message = this.message,
        result = this.result,
        idx = this.idx,
        appMutex = this.appMutex,
        broadcast = this.broadcast,
        ivsAutoMaxQuality = this.ivsAutoMaxQuality,
        ivsStartQuality = this.ivsStartQuality,
        userIp = this.userIp,
        adultCheck = this.adultCheck.toEntity(),
        categoryNew = this.categoryNew.toCategoryNewEntityList(),
        banner = this.banner.toEntity()
    )

    fun ConfigDataModel.toConfigDataEntity(): ConfigDataEntity = ConfigDataEntity(
        id = 1,
        message = this.message,
        result = this.result,
        idx = this.idx,
        appMutex = this.appMutex,
        broadcast = this.broadcast,
        ivsAutoMaxQuality = this.ivsAutoMaxQuality,
        ivsStartQuality = this.ivsStartQuality,
        userIp = this.userIp,
        adultCheck = this.adultCheck.toEntity(),
        categoryNew = this.categoryNew.toCategoryNewEntityListFromModel(),
        banner = this.banner.toEntity()
    )

    // AdultCheck 시작
    private fun AdultCheckEntity.toModel() = AdultCheckModel(
        chatMessage = this.chatMessage,
        post = this.post,
        recom = this.recom
    )

    private fun AdultCheckModel.toEntity() = AdultCheckEntity(
        chatMessage = this.chatMessage,
        post = this.post,
        recom = this.recom
    )

    private fun AdultCheck.toEntity() = AdultCheckEntity(
        chatMessage = this.chatMessage,
        post = this.post,
        recom = this.recom
    )

    private fun AdultCheck.toModel() = AdultCheckModel(
        chatMessage = this.chatMessage,
        post = this.post,
        recom = this.recom
    )
    // AdultCheck 끝

    // Banner 시작
    // BannerEntity -> BannerModel
    private fun BannerEntity.toModel() = BannerModel(
        android = this.android.toModel(),
        win = this.win.toModel()
    )

    private fun AndroidEntity.toModel() = AndroidModel(
        main = this.main.toMainModelListFromEntity()
    )

    private fun WinEntity.toModel() = WinModel(
        main = this.main.toMainModelListFromEntity(),
        newMain = this.newMain.toMainModelListFromEntity(),
        subLeft = this.subLeft.toModel(),
        subRight = this.subRight.toModel()
    )

    private fun List<MainEntity>.toMainModelListFromEntity(): List<MainModel> = map {
        it.toModel()
    }

    private fun MainEntity.toModel(): MainModel = MainModel(
        img = this.img,
        url = this.url
    )

    private fun SubLeftAndRightEntity.toModel(): SubLeftAndRightModel = SubLeftAndRightModel(
        img = this.img,
        link = this.link
    )

    // BannerModel -> BannerEntity
    private fun BannerModel.toEntity() = BannerEntity(
        android = this.android.toEntity(),
        win = this.win.toEntity()
    )

    private fun AndroidModel.toEntity() = AndroidEntity(
        main = this.main.toEntityListFromModel()
    )

    private fun WinModel.toEntity() = WinEntity(
        main = this.main.toEntityListFromModel(),
        newMain = this.newMain.toEntityListFromModel(),
        subLeft = this.subLeft.toEntity(),
        subRight = this.subRight.toEntity()
    )

    private fun List<MainModel>.toEntityListFromModel(): List<MainEntity> = map {
        it.toEntity()
    }

    private fun MainModel.toEntity(): MainEntity = MainEntity(
        img = this.img,
        url = this.url
    )

    private fun SubLeftAndRightModel.toEntity(): SubLeftAndRightEntity = SubLeftAndRightEntity(
        img = this.img,
        link = this.link
    )

    // Banner -> BannerEntity
    private fun Banner.toEntity() = BannerEntity(
        android = this.android.toEntity(),
        win = this.win.toEntity()
    )

    private fun Android.toEntity() = AndroidEntity(
        main = this.main.toEntityList()
    )

    private fun Win.toEntity() = WinEntity(
        main = this.main.toEntityList(),
        newMain = this.newMain.toEntityList(),
        subLeft = this.subLeft.toEntity(),
        subRight = this.subRight.toEntity()
    )

    private fun List<Main>.toEntityList(): List<MainEntity> = map {
        it.toEntity()
    }

    private fun Main.toEntity(): MainEntity = MainEntity(
        img = this.img,
        url = this.url
    )

    private fun SubLeftAndRight.toEntity(): SubLeftAndRightEntity = SubLeftAndRightEntity(
        img = this.img,
        link = this.link
    )

    // Banner -> BannerModel
    private fun Banner.toModel() = BannerModel(
        android = this.android.toModel(),
        win = this.win.toModel()
    )

    private fun Android.toModel() = AndroidModel(
        main = this.main.toMainModelList()
    )

    private fun Win.toModel() = WinModel(
        main = this.main.toMainModelList(),
        newMain = this.newMain.toMainModelList(),
        subLeft = this.subLeft.toModel(),
        subRight = this.subRight.toModel()
    )

    private fun List<Main>.toMainModelList(): List<MainModel> = map {
        it.toModel()
    }

    private fun Main.toModel(): MainModel = MainModel(
        img = this.img,
        url = this.url
    )

    private fun SubLeftAndRight.toModel(): SubLeftAndRightModel = SubLeftAndRightModel(
        img = this.img,
        link = this.link
    )
    // Banner 끝

    // CategoryNew 시작
    private fun List<CategoryNewEntity>.toCategoryNewModelListFromEntity(): List<CategoryNewModel> =
        map {
            it.toModel()
        }

    private fun CategoryNewEntity.toModel(): CategoryNewModel = CategoryNewModel(
        code = this.code,
        idx = this.idx,
        isList = this.isList,
        isView = this.isView,
        title = this.title,
        type = this.type
    )

    private fun List<CategoryNewModel>.toCategoryNewEntityListFromModel(): List<CategoryNewEntity> =
        map {
            it.toEntity()
        }

    private fun CategoryNewModel.toEntity(): CategoryNewEntity = CategoryNewEntity(
        code = this.code,
        idx = this.idx,
        isList = this.isList,
        isView = this.isView,
        title = this.title,
        type = this.type
    )

    private fun List<CategoryNew>.toCategoryNewModelList(): List<CategoryNewModel> = map {
        it.toModel()
    }

    private fun CategoryNew.toModel(): CategoryNewModel = CategoryNewModel(
        code = this.code,
        idx = this.idx,
        isList = this.isList,
        isView = this.isView,
        title = this.title,
        type = this.type
    )

    private fun List<CategoryNew>.toCategoryNewEntityList(): List<CategoryNewEntity> = map {
        it.toEntity()
    }

    private fun CategoryNew.toEntity(): CategoryNewEntity = CategoryNewEntity(
        code = this.code,
        idx = this.idx,
        isList = this.isList,
        isView = this.isView,
        title = this.title,
        type = this.type
    )
    // CategoryNew 끝
}
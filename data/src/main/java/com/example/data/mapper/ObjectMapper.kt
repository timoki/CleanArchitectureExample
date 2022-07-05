package com.example.data.mapper

import com.example.data.db.entity.config.*
import com.example.data.db.entity.live.LiveListEntity
import com.example.data.model.config.*
import com.example.data.model.defaultData.DefaultData
import com.example.data.model.defaultData.ErrorData
import com.example.data.model.live.LiveList
import com.example.data.model.login.*
import com.example.domain.model.config.*
import com.example.domain.model.defaultData.DefaultDataModel
import com.example.domain.model.defaultData.ErrorDataModel
import com.example.domain.model.live.LiveListModel
import com.example.domain.model.login.*

/**
 * Local DB DataClass, Remote DataClass, UI DataClass 3개의 타입을 서로 변환하기 위한 Mapper Class
 * Remote 처리 단(ApiService -> RemoteDataSource), Local 처리 단(Dao -> LocalDataSource), UI 적용 단(ViewModel -> View) 에서 처리하는 데이터 클래스가 다르기 때문에 Repository 단에서 매핑 처리를 한다.
 * */
object ObjectMapper {

    /**
     * Config 시작
     * */
    fun ConfigDataEntity.toConfigDataModel(): ConfigDataModel = ConfigDataModel(
        message = this.message,
        result = this.result,
        appMutex = this.appMutex,
        broadcast = this.broadcast,
        ivsAutoMaxQuality = this.ivsAutoMaxQuality,
        ivsStartQuality = this.ivsStartQuality,
        adultCheck = this.adultCheck.toModel(),
        categoryNew = this.categoryNew.toCategoryNewModelListFromEntity(),
        banner = this.banner.toModel(),
        link = this.link.toModel(),
        socialLogin = this.socialLogin
    )

    fun ConfigData.toConfigDataModel(): ConfigDataModel = ConfigDataModel(
        message = this.message,
        result = this.result,
        appMutex = this.appMutex,
        broadcast = this.broadcast,
        ivsAutoMaxQuality = this.ivsAutoMaxQuality,
        ivsStartQuality = this.ivsStartQuality,
        adultCheck = this.adultCheck.toModel(),
        categoryNew = this.categoryNew.toCategoryNewModelList(),
        banner = this.banner.toModel(),
        link = this.link.toModel(),
        socialLogin = this.socialLogin
    )

    fun ConfigData.toConfigDataEntity(): ConfigDataEntity = ConfigDataEntity(
        id = 1,
        message = this.message,
        result = this.result,
        appMutex = this.appMutex,
        broadcast = this.broadcast,
        ivsAutoMaxQuality = this.ivsAutoMaxQuality,
        ivsStartQuality = this.ivsStartQuality,
        adultCheck = this.adultCheck.toEntity(),
        categoryNew = this.categoryNew.toCategoryNewEntityList(),
        banner = this.banner.toEntity(),
        link = this.link.toEntity(),
        socialLogin = this.socialLogin
    )

    fun ConfigDataModel.toConfigDataEntity(): ConfigDataEntity = ConfigDataEntity(
        id = 1,
        message = this.message,
        result = this.result,
        appMutex = this.appMutex,
        broadcast = this.broadcast,
        ivsAutoMaxQuality = this.ivsAutoMaxQuality,
        ivsStartQuality = this.ivsStartQuality,
        adultCheck = this.adultCheck.toEntity(),
        categoryNew = this.categoryNew.toCategoryNewEntityListFromModel(),
        banner = this.banner.toEntity(),
        link = this.link.toEntity(),
        socialLogin = this.socialLogin
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

    // Link 시작
    private fun LinkEntity.toModel() = LinkModel(
        adultAuth = this.adultAuth,
        base = this.base,
        channel = this.channel,
        chargeFull = this.chargeFull,
        chargeHeart = this.chargeHeart,
        chargeItem = this.chargeItem,
        chargeItemInfo = this.chargeItemInfo,
        chargeItemInfoListUp = this.chargeItemInfoListUp,
        chargeItemInfoUserUp = this.chargeItemInfoUserUp,
        chargeItemInfoSaveUp = this.chargeItemInfoSaveUp,
        chargeItemInfoFireRecom = this.chargeItemInfoFireRecom,
        chargeItemInfoPushFan = this.chargeItemInfoPushFan,
        chargeItemInfoPushBookMark = this.chargeItemInfoPushBookMark,
        chargeItemInfoHeartEmo = this.chargeItemInfoHeartEmo,
        chargeItemInfoListDeco = this.chargeItemInfoListDeco,
        chargeItemInfoWorldMsg = this.chargeItemInfoWorldMsg,
        chargeItemInfoGuestLive = this.chargeItemInfoGuestLive,
        chargeItemInfoEntUnlimit = this.chargeItemInfoEntUnlimit,
        chargeItemInfoNickDeco = this.chargeItemInfoNickDeco,
        chargeItemInfoIntroEffect = this.chargeItemInfoIntroEffect,
        event = this.event,
        exchange = this.exchange,
        findId = this.findId,
        findPw = this.findPw,
        freeLawConsult = this.freeLawConsult,
        itemLog = this.itemLog,
        join = this.join,
        notice = this.notice,
        policyMarketing = this.policyMarketing,
        policyPrivacy = this.policyPrivacy,
        policyService = this.policyService,
        post = this.post,
        reportCenter = this.reportCenter,
        signatureGuide = this.signatureGuide,
        socialLoginFACEBOOK = this.socialLoginFACEBOOK,
        socialLoginGOOGLE = this.socialLoginGOOGLE,
        socialLoginKAKAO = this.socialLoginKAKAO,
        socialLoginNAVER = this.socialLoginNAVER
    )

    private fun LinkModel.toEntity() = LinkEntity(
        adultAuth = this.adultAuth,
        base = this.base,
        channel = this.channel,
        chargeFull = this.chargeFull,
        chargeHeart = this.chargeHeart,
        chargeItem = this.chargeItem,
        chargeItemInfo = this.chargeItemInfo,
        chargeItemInfoListUp = this.chargeItemInfoListUp,
        chargeItemInfoUserUp = this.chargeItemInfoUserUp,
        chargeItemInfoSaveUp = this.chargeItemInfoSaveUp,
        chargeItemInfoFireRecom = this.chargeItemInfoFireRecom,
        chargeItemInfoPushFan = this.chargeItemInfoPushFan,
        chargeItemInfoPushBookMark = this.chargeItemInfoPushBookMark,
        chargeItemInfoHeartEmo = this.chargeItemInfoHeartEmo,
        chargeItemInfoListDeco = this.chargeItemInfoListDeco,
        chargeItemInfoWorldMsg = this.chargeItemInfoWorldMsg,
        chargeItemInfoGuestLive = this.chargeItemInfoGuestLive,
        chargeItemInfoEntUnlimit = this.chargeItemInfoEntUnlimit,
        chargeItemInfoNickDeco = this.chargeItemInfoNickDeco,
        chargeItemInfoIntroEffect = this.chargeItemInfoIntroEffect,
        event = this.event,
        exchange = this.exchange,
        findId = this.findId,
        findPw = this.findPw,
        freeLawConsult = this.freeLawConsult,
        itemLog = this.itemLog,
        join = this.join,
        notice = this.notice,
        policyMarketing = this.policyMarketing,
        policyPrivacy = this.policyPrivacy,
        policyService = this.policyService,
        post = this.post,
        reportCenter = this.reportCenter,
        signatureGuide = this.signatureGuide,
        socialLoginFACEBOOK = this.socialLoginFACEBOOK,
        socialLoginGOOGLE = this.socialLoginGOOGLE,
        socialLoginKAKAO = this.socialLoginKAKAO,
        socialLoginNAVER = this.socialLoginNAVER
    )

    private fun Link.toEntity() = LinkEntity(
        adultAuth = this.adultAuth,
        base = this.base,
        channel = this.channel,
        chargeFull = this.chargeFull,
        chargeHeart = this.chargeHeart,
        chargeItem = this.chargeItem,
        chargeItemInfo = this.chargeItemInfo,
        chargeItemInfoListUp = this.chargeItemInfoListUp,
        chargeItemInfoUserUp = this.chargeItemInfoUserUp,
        chargeItemInfoSaveUp = this.chargeItemInfoSaveUp,
        chargeItemInfoFireRecom = this.chargeItemInfoFireRecom,
        chargeItemInfoPushFan = this.chargeItemInfoPushFan,
        chargeItemInfoPushBookMark = this.chargeItemInfoPushBookMark,
        chargeItemInfoHeartEmo = this.chargeItemInfoHeartEmo,
        chargeItemInfoListDeco = this.chargeItemInfoListDeco,
        chargeItemInfoWorldMsg = this.chargeItemInfoWorldMsg,
        chargeItemInfoGuestLive = this.chargeItemInfoGuestLive,
        chargeItemInfoEntUnlimit = this.chargeItemInfoEntUnlimit,
        chargeItemInfoNickDeco = this.chargeItemInfoNickDeco,
        chargeItemInfoIntroEffect = this.chargeItemInfoIntroEffect,
        event = this.event,
        exchange = this.exchange,
        findId = this.findId,
        findPw = this.findPw,
        freeLawConsult = this.freeLawConsult,
        itemLog = this.itemLog,
        join = this.join,
        notice = this.notice,
        policyMarketing = this.policyMarketing,
        policyPrivacy = this.policyPrivacy,
        policyService = this.policyService,
        post = this.post,
        reportCenter = this.reportCenter,
        signatureGuide = this.signatureGuide,
        socialLoginFACEBOOK = this.socialLoginFACEBOOK,
        socialLoginGOOGLE = this.socialLoginGOOGLE,
        socialLoginKAKAO = this.socialLoginKAKAO,
        socialLoginNAVER = this.socialLoginNAVER
    )

    private fun Link.toModel() = LinkModel(
        adultAuth = this.adultAuth,
        base = this.base,
        channel = this.channel,
        chargeFull = this.chargeFull,
        chargeHeart = this.chargeHeart,
        chargeItem = this.chargeItem,
        chargeItemInfo = this.chargeItemInfo,
        chargeItemInfoListUp = this.chargeItemInfoListUp,
        chargeItemInfoUserUp = this.chargeItemInfoUserUp,
        chargeItemInfoSaveUp = this.chargeItemInfoSaveUp,
        chargeItemInfoFireRecom = this.chargeItemInfoFireRecom,
        chargeItemInfoPushFan = this.chargeItemInfoPushFan,
        chargeItemInfoPushBookMark = this.chargeItemInfoPushBookMark,
        chargeItemInfoHeartEmo = this.chargeItemInfoHeartEmo,
        chargeItemInfoListDeco = this.chargeItemInfoListDeco,
        chargeItemInfoWorldMsg = this.chargeItemInfoWorldMsg,
        chargeItemInfoGuestLive = this.chargeItemInfoGuestLive,
        chargeItemInfoEntUnlimit = this.chargeItemInfoEntUnlimit,
        chargeItemInfoNickDeco = this.chargeItemInfoNickDeco,
        chargeItemInfoIntroEffect = this.chargeItemInfoIntroEffect,
        event = this.event,
        exchange = this.exchange,
        findId = this.findId,
        findPw = this.findPw,
        freeLawConsult = this.freeLawConsult,
        itemLog = this.itemLog,
        join = this.join,
        notice = this.notice,
        policyMarketing = this.policyMarketing,
        policyPrivacy = this.policyPrivacy,
        policyService = this.policyService,
        post = this.post,
        reportCenter = this.reportCenter,
        signatureGuide = this.signatureGuide,
        socialLoginFACEBOOK = this.socialLoginFACEBOOK,
        socialLoginGOOGLE = this.socialLoginGOOGLE,
        socialLoginKAKAO = this.socialLoginKAKAO,
        socialLoginNAVER = this.socialLoginNAVER
    )
    // Link 끝

    /**
     * Config 끝
     * */

    /**
     * Member 시작
     * */

    fun DefaultData.toDefaultDataModel(): DefaultDataModel = DefaultDataModel(
        result = this.result,
        message = this.message,
        errorData = this.errorData?.toErrorDataModel()
    )

    private fun ErrorData.toErrorDataModel(): ErrorDataModel = ErrorDataModel(
        code = this.code
    )

    fun LoginData.toLoginDataModel(): LoginDataModel = LoginDataModel(
        result = this.result,
        loginInfo = this.loginInfo.toLoginInfoModel(),
        needPwChange = this.needPwChange,
        message = this.message,
        errorData = this.errorData?.toErrorDataModel()
    )

    private fun LoginInfo.toLoginInfoModel(): LoginInfoModel = LoginInfoModel(
        deviceInfo = this.deviceInfo.toDeviceInfoModel(),
        sessionKey = this.sessionKey,
        siteMode = this.siteMode.toSiteModeModel(),
        userInfo = this.userInfo.toUserInfoModel()
    )

    private fun DeviceInfo.toDeviceInfoModel(): DeviceInfoModel = DeviceInfoModel(
        type = this.type,
        version = this.version
    )

    private fun SiteMode.toSiteModeModel(): SiteModeModel = SiteModeModel(
        mode = this.mode,
        needAuth = this.needAuth,
        type = this.type
    )

    private fun UserInfo.toUserInfoModel(): UserInfoModel = UserInfoModel(
        agreeSmsYN = this.agreeSmsYN,
        authYN = this.authYN,
        bjRank = this.bjRank,
        channelDesc = this.channelDesc,
        channelTitle = this.channelTitle,
        chatYN = this.chatYN,
        coinHave = this.coinHave,
        coinUse = this.coinUse,
        id = this.id,
        idx = this.idx,
        imgProfile = this.imgProfile,
        imgProfileYN = this.imgProfileYN,
        isAdult = this.isAdult,
        isBJ = this.isBJ,
        isLogin = this.isLogin,
        nick = this.nick,
        postCountReadN = this.postCountReadN,
        postYN = this.postYN,
        purchaseUser = this.purchaseUser,
        recomYN = this.recomYN,
        socialYN = this.socialYN
    )

    /**
     * User 끝
     * */

    /**
     * Live 시작
     * */

    fun List<LiveList>.toLiveListEntityFromList(): List<LiveListEntity> = map {
        it.toLiveListEntity()
    }

    private fun LiveList.toLiveListEntity(): LiveListEntity = LiveListEntity(
        id = 0,
        bookmarkCnt = this.bookmarkCnt,
        browser = this.browser,
        category = this.category,
        code = this.code,
        endTime = this.endTime,
        fanCnt = this.fanCnt,
        heart = this.heart,
        isAdult = this.isAdult,
        isGuestLive = this.isGuestLive,
        isLive = this.isLive,
        isPw = this.isPw,
        ivsThumbnail = this.ivsThumbnail,
        likeCnt = this.likeCnt,
        listDeco = this.listDeco,
        listUp = this.listUp,
        liveType = this.liveType,
        playCnt = this.playCnt,
        sizeHeight = this.sizeHeight,
        sizeWidth = this.sizeWidth,
        startTime = this.startTime,
        storage = this.storage,
        thumbUrl = this.thumbUrl,
        thumbUrlOrigin = this.thumbUrlOrigin,
        title = this.title,
        totalScoreCnt = this.totalScoreCnt,
        type = this.type,
        user = this.user,
        userId = this.userId,
        userIdx = this.userIdx,
        userImg = this.userImg,
        userLimit = this.userLimit,
        userNick = this.userNick,
        userUp = this.userUp,
    )

    fun LiveListEntity.toModel(): LiveListModel = LiveListModel(
        bookmarkCnt = this.bookmarkCnt,
        browser = this.browser,
        category = this.category,
        code = this.code,
        endTime = this.endTime,
        fanCnt = this.fanCnt,
        heart = this.heart,
        isAdult = this.isAdult,
        isGuestLive = this.isGuestLive,
        isLive = this.isLive,
        isPw = this.isPw,
        ivsThumbnail = this.ivsThumbnail,
        likeCnt = this.likeCnt,
        listDeco = this.listDeco,
        listUp = this.listUp,
        liveType = this.liveType,
        playCnt = this.playCnt,
        sizeHeight = this.sizeHeight,
        sizeWidth = this.sizeWidth,
        startTime = this.startTime,
        storage = this.storage,
        thumbUrl = this.thumbUrl,
        thumbUrlOrigin = this.thumbUrlOrigin,
        title = this.title,
        totalScoreCnt = this.totalScoreCnt,
        type = this.type,
        user = this.user,
        userId = this.userId,
        userIdx = this.userIdx,
        userImg = this.userImg,
        userLimit = this.userLimit,
        userNick = this.userNick,
        userUp = this.userUp
    )

    /**
     * Live 끝
     * */
}
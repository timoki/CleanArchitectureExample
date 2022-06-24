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
        result = this.result
    )

    fun ConfigData.toConfigDataModel(): ConfigDataModel = ConfigDataModel(
        message = this.message,
        result = this.result
    )

    fun ConfigData.toConfigDataEntity(): ConfigDataEntity = ConfigDataEntity(
        id = 1,
        message = this.message,
        result = this.result
    )

    fun ConfigDataModel.toConfigDataEntity(): ConfigDataEntity = ConfigDataEntity(
        id = 1,
        message = this.message,
        result = this.result
    )

    fun ConfigDataModel.toConfigData(): ConfigData = ConfigData(
        message = this.message,
        result = this.result, idx = 0, adultCheck = AdultCheck(
            chatMessage = false,
            post = false,
            recom = false
        ), banner = Banner(
            android = Android(main = listOf()),
            win = Win(
                main = listOf(),
                newMain = listOf(),
                subLeft = SubLeftAndRight(img = "", link = ""),
                subRight = SubLeftAndRight(img = "", link = "")
            )
        ), broadcast = false, categoryNew = listOf(), chatMessage = ChatMessage(intro = ""), debug = Debug(
            REQUEST_URI = "",
            device = Device(token = "", type = "", version = ""),
            name = "",
            query = "",
            queryCnt = 0,
            queryErrorLib = listOf(),
            queryLib = listOf(),
            redis = listOf(),
            xhprofLink = ""
        ), ivsAutoMaxQuality = 0, ivsStartQuality = 0, link = Link(
            adultAuth = "",
            base = "",
            channel = "",
            chargeFull = "",
            chargeHeart = "",
            chargeItem = "",
            chargeItemInfo = "",
            chargeItemInfoListUp = "",
            chargeItemInfoUserUp = "",
            chargeItemInfoSaveUp = "",
            chargeItemInfoFireRecom = "",
            chargeItemInfoPushFan = "",
            chargeItemInfoPushBookMark = "",
            chargeItemInfoHeartEmo = "",
            chargeItemInfoListDeco = "",
            chargeItemInfoWorldMsg = "",
            chargeItemInfoGuestLive = "",
            chargeItemInfoEntUnlimit = "",
            chargeItemInfoNickDeco = "",
            chargeItemInfoIntroEffect = "",
            event = "",
            exchange = "",
            findId = "",
            findPw = "",
            freeLawConsult = "",
            itemLog = "",
            join = "",
            notice = "",
            policyMarketing = "",
            policyPrivacy = "",
            policyService = "",
            post = "",
            reportCenter = "",
            signatureGuide = "",
            socialLoginFACEBOOK = "",
            socialLoginGOOGLE = "",
            socialLoginKAKAO = "",
            socialLoginNAVER = ""
        ), newChat = NewChat(
            api = "",
            node = "",
            roomUserCount = "",
            status = ""
        ), server = Server(api = "", node = listOf()), socialLogin = SocialLogin(
            FACEBOOK = false,
            GOOGLE = false,
            KAKAO = false,
            NAVER = false
        ), socialServiceUrl = SocialServiceUrl(
            facebook = "",
            instagram = "",
            naverBlog = "",
            naverTV = "",
            tiktok = "",
            twitter = "",
            youtube = ""
        ), update = listOf(), userIp = ""
    )

    fun AdultCheckEntity.toAdutCheckModel() = AdultCheckModel(
        chatMessage = this.chatMessage,
        post = this.post,
        recom = this.recom
    )

    // List 를 -> List로 바꾸는 것
    fun List<CategoryNewEntity>.toCategoryNewModelList(): List<CategoryNewModel> = map {
        it.toCategoryNewModel()
    }

    fun CategoryNewEntity.toCategoryNewModel(): CategoryNewModel = CategoryNewModel(
        code = this.code,
        idx = this.idx,
        isList = this.isList,
        isView = this.isView,
        title = this.title,
        type = this.type
    )
}
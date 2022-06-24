package com.example.domain.model.config

/**
 * Domain Layer(Module)의 Model 은 UI 에 적용하기 위한 Data Class 라고 보면 된다.
 * */
data class ConfigDataModel(
    /*val idx: Int,
    val adultCheck: AdultCheckModel,
    val appMutex: Boolean,
    val banner: BannerModel,
    val broadcast: Boolean,
    val categoryNew: List<CategoryNewModel>,
    val chatMessage: ChatMessageModel,
    val chatMessageReplace: ChatMessageReplaceModel,
    val debug: DebugModel,
    val ivsAutoMaxQuality: Int,
    val ivsStartQuality: Int,
    val link: LinkModel,*/
    val message: String,
    //val newChat: NewChatModel,
    val result: Boolean,
    /*val server: ServerModel,
    val socialLogin: SocialLoginModel,
    val socialServiceUrl: SocialServiceUrlModel,
    val update: List<Any>,
    val userIp: String*/
)
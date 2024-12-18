package com.example.domain.model.config

/**
 * Domain Layer(Module)의 Model 은 UI 에 적용하기 위한 Data Class 라고 보면 된다.
 * */
data class ConfigDataModel(
    val message: String,
    val result: Boolean,
    val appMutex: Boolean,
    val broadcast: Boolean,
    val ivsAutoMaxQuality: Int,
    val ivsStartQuality: Int,
    val adultCheck: AdultCheckModel,
    val categoryNew: List<CategoryNewModel>,
    val banner: BannerModel,
    val link: LinkModel,
    val socialLogin: Map<String, Boolean>

    /*,
    val broadcast: Boolean,
    val chatMessage: ChatMessageModel,
    val chatMessageReplace: ChatMessageReplaceModel,
    val debug: DebugModel,
    val newChat: NewChatModel,
    val server: ServerModel,
    val socialLogin: SocialLoginModel,
    val socialServiceUrl: SocialServiceUrlModel*/
)
package com.umc.approval.data.dto.approval.post

import com.google.gson.annotations.SerializedName

/**
 * 동의 및 거절 수 API
 * API 명세서 Check 완료
 * */
data class AgreePostDto(

    @SerializedName("isApprove")
    val isApprove: Boolean,

)
package com.umc.approval.data.repository.approval

import com.umc.approval.data.dto.approval.get.ApprovalPaperDto
import com.umc.approval.data.dto.approval.get.DocumentDto
import com.umc.approval.data.retrofit.instance.RetrofitInstance.ApprovalApi
import retrofit2.Call

/**
 * Approval Fragment Repository
 */
class ApprovalFragmentRepository {

    /**
     * 결재서류 목록 조회 API
     */
    fun getDocuments(page: String, category: String): Call<ApprovalPaperDto> {
        return ApprovalApi.getDocuments(page, category)
    }

    /**
     * 관심부서 결재서류 목록 조회 API
     */
    fun getInterestingCategoryDocuments(accessToken: String, page: String, category: String): Call<ApprovalPaperDto> {
        return ApprovalApi.getInterestingCategoryDocuments(accessToken, page, category)
    }

    /**
     * 결재서류 상세 조회 API
     */
    fun getDocumentDetail(documentId: String): Call<DocumentDto> {
        return ApprovalApi.getDocumentDetail(documentId)
    }
}
package com.umc.approval.ui.activity

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.approval.databinding.ActivityLikeBinding
import com.umc.approval.ui.adapter.like_activity.LikeRVAdapter
import com.umc.approval.ui.adapter.participant_activity.ParticipantRVAdapter
import com.umc.approval.util.Participant

class LikeActivity : AppCompatActivity() {
    lateinit var binding: ActivityLikeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setLikeList()  // 리사이클러뷰 아이템 & 어댑터 설정

        binding.btnGoBack.setOnClickListener {
            Log.d("로그", "뒤로가기 버튼 클릭")
        }
    }

    private fun setLikeList() {
        val like: ArrayList<Participant> = arrayListOf()

        like.apply {
            add(Participant("", "부장", "닉네임1", true))
            add(Participant("", "차장", "닉네임2", false))
            add(Participant("", "부장", "닉네임3", false))
            add(Participant("", "부장", "닉네임4", true))
            add(Participant("", "부장", "닉네임5", false))
            add(Participant("", "부장", "닉네임6", true))
            add(Participant("", "부장", "닉네임7", true))
            add(Participant("", "차장", "닉네임8", false))
        }

        val participantRVAdapter = ParticipantRVAdapter(like)
        val spaceDecoration = VerticalSpaceItemDecoration(90)
        binding.rvLike.addItemDecoration(spaceDecoration)
        binding.rvLike.adapter = participantRVAdapter
        binding.rvLike.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    // 아이템 간 간격 조절 기능
    inner class VerticalSpaceItemDecoration(private val height: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = height
        }
    }
}
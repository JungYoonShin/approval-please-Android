package com.umc.approval.ui.fragment.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.approval.R
import com.umc.approval.databinding.FragmentSearchCommunityTabBinding
import com.umc.approval.ui.activity.CommunityTokActivity
import com.umc.approval.ui.adapter.community_fragment.CommunityTalkItemRVAdapter
import com.umc.approval.ui.fragment.approval.ApprovalBottomSheetDialogSortFragment
import com.umc.approval.ui.viewmodel.search.SearchTokViewModel
import com.umc.approval.util.Utils

class CommunityTabFragment: Fragment() {

    private var _binding : FragmentSearchCommunityTabBinding? = null
    private val binding get() = _binding!!

    private lateinit var communityTalkItemRVAdapter: CommunityTalkItemRVAdapter

    private val viewModel by viewModels<SearchTokViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchCommunityTabBinding.inflate(inflater, container, false)
        val view = binding.root

        live_data()

        binding.categorySelect.setOnClickListener {
            val bottomSheetDialog = SearchBottomSheetDialogCategoryFragment(binding.categoryText.text.toString())
            bottomSheetDialog.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheetDialog.show(childFragmentManager, bottomSheetDialog.tag)
        }

        binding.sortSelect.setOnClickListener {
            val bottomSheetDialog = ApprovalBottomSheetDialogSortFragment(binding.sortText.text.toString())
            bottomSheetDialog.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.RoundCornerBottomSheetDialogTheme
            )
            bottomSheetDialog.show(childFragmentManager, bottomSheetDialog.tag)
        }

        childFragmentManager
            .setFragmentResultListener("category", this) { requestKey, bundle ->
                val result = bundle.getString("result")
                binding.categoryText.text = result
                if (result == "부서 전체") {
                    viewModel.setCategory(18)
                } else {
                    viewModel.setCategory(Utils.categoryMapReverse[result]!!)
                }
            }

        childFragmentManager
            .setFragmentResultListener("sort", this) { requestKey, bundle ->
                val result = bundle.getString("result")
                binding.sortText.text = result
                viewModel.setSort(Utils.searchSortMap[result]!!)
            }

        return view
    }

    override fun onStart() {
        super.onStart()

        viewModel.setQuery("#query")
        viewModel.setTag(viewModel.query.value!!)
        viewModel.setSort(0)

        viewModel.get_toktok(viewModel.query.value!!, viewModel.tag.value!!, viewModel.category.value, viewModel.sort.value!!)
    }

    override fun onResume() {
        super.onResume()

        viewModel.get_toktok(viewModel.query.value!!, viewModel.tag.value!!, viewModel.category.value, viewModel.sort.value!!)
    }

    private fun live_data() {

        // category 상태 변화시
        viewModel.category.observe(viewLifecycleOwner) {
            viewModel.get_toktok(viewModel.query.value!!, viewModel.tag.value!!, viewModel.category.value, viewModel.sort.value!!)
        }

        // sortBy(정렬) 상태 변화시
        viewModel.sort.observe(viewLifecycleOwner) {
            viewModel.get_toktok(viewModel.query.value!!, viewModel.tag.value!!, viewModel.category.value, viewModel.sort.value!!)
        }

        // 서버에서 데이터를 받아오면 뷰에 적용하는 라이브 데이터
        viewModel.toktok.observe(viewLifecycleOwner) {
            communityTalkItemRVAdapter = CommunityTalkItemRVAdapter(it)

            val community_item_rv: RecyclerView = binding.rvSearchResultToktok

            community_item_rv.adapter = communityTalkItemRVAdapter
            community_item_rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            communityTalkItemRVAdapter.itemClick = object : CommunityTalkItemRVAdapter.ItemClick {
                override fun move_to_tok_activity() {
                    startActivity(Intent(requireContext(), CommunityTokActivity::class.java))
                }
            }
        }
    }
}
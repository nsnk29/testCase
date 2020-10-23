package com.nsnk.testcase.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nsnk.testcase.R
import com.nsnk.testcase.data.model.BaseResponseImgur
import com.nsnk.testcase.databinding.FragmentImgurListBinding
import io.reactivex.rxjava3.disposables.Disposable

class ImgurListFragment : Fragment() {

    var subscribe: Disposable? = null
        set(value) {
            field?.dispose()
            field = value
        }

    private lateinit var binding: FragmentImgurListBinding
    private val viewModel by viewModels<ImgurViewModel>()
    private val recyclerAdapter = ImgurRecyclerAdapter()
    private var page = 1
    private var isLoadMore = false
    private var isFinished = false


    private val dataObserver = Observer<LiveDataResult<BaseResponseImgur>> { result ->
        when (result?.status) {
            LiveDataResult.Status.ERROR -> {
                result.response?.data
            }

            LiveDataResult.Status.SUCCESS -> {
                isLoadMore = false
                recyclerAdapter.updateList(result.response!!.data)
            }

            else -> {
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: 23.10.2020 правильный ЖЦ
        viewModel.apiLiveData.observe(viewLifecycleOwner, this.dataObserver)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_imgur_list, container, false
        )
        val view = binding.root
        binding.postsRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
        binding.viewModel = viewModel
        binding.recyclerAdapter = recyclerAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (recyclerAdapter.isEmpty()) {
            viewModel.loadImages()
        }
    }

    override fun onPause() {
        super.onPause()
//        binding.postsRecyclerView.removeOnScrollListener(onScrollChangeListener)
    }

    override fun onResume() {
        super.onResume()
//        binding.postsRecyclerView.addOnScrollListener(onScrollChangeListener)
    }

//    private val onScrollChangeListener = object : RecyclerView.OnScrollListener() {
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            val layManager = binding.postsRecyclerView.layoutManager!! as StaggeredGridLayoutManager
//            val visibleItemCount = layManager.childCount
//            val totalItemCount = layManager.itemCount
//            val visibleItems = layManager.findFirstVisibleItemPositions(null)
//            val firstVisibleItem = if (visibleItems.isNotEmpty()) visibleItems.first() else 0
//            if (!isLoadMore && !isFinished) {
//                if (visibleItemCount + firstVisibleItem >= totalItemCount
//                    && firstVisibleItem >= 0
//                ) {
//                    page++
//                    isLoadMore = true
//                    viewModel.loadImages(page = page)
//                }
//            }
//        }
//    }
}
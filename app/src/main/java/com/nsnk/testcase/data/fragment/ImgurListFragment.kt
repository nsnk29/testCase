package com.nsnk.testcase.data.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nsnk.testcase.ImgurTestApp
import com.nsnk.testcase.R
import com.nsnk.testcase.data.LiveDataResult
import com.nsnk.testcase.data.adapter.ImgurGalleryAdapter
import com.nsnk.testcase.data.model.GalleryResponseImgur
import com.nsnk.testcase.data.viewmodel.ImgurGalleryViewModel
import com.nsnk.testcase.databinding.FragmentImgurListBinding
import io.reactivex.rxjava3.disposables.Disposable

class ImgurListFragment : Fragment() {

    // переход к другому фрагменту по клику на recyclerview item
    private var subscribe: Disposable? = null
        set(value) {
            field?.dispose()
            field = value
        }

    private lateinit var binding: FragmentImgurListBinding
    private val viewModel by viewModels<ImgurGalleryViewModel>()
    private val recyclerAdapter = ImgurGalleryAdapter()
    private var page = 1
    private var isLoadMore = false
    private var isFinished = false

    // обработка результата запроса к API
    private val dataObserver = Observer<LiveDataResult<GalleryResponseImgur>> { result ->
        when (result?.status) {
            LiveDataResult.Status.ERROR -> {
                result.response?.data
            }

            LiveDataResult.Status.SUCCESS -> {
                isLoadMore = false
                recyclerAdapter.updateList(result.response!!.data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.galleryApiLiveData.observe(viewLifecycleOwner, this.dataObserver)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_imgur_list, container, false
        )
        with(binding) {
            postsRecyclerView.layoutManager =
                StaggeredGridLayoutManager(SPAN_COUNT, RecyclerView.VERTICAL).apply {
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                }
            mViewModel = viewModel
            mRecyclerAdapter = recyclerAdapter
        }

        subscribe = recyclerAdapter.clickEvent.subscribe { model ->
            findNavController().navigate(
                ImgurListFragmentDirections.actionImgurListFragmentToDetailsFragment(
                    model, model.title ?: ImgurTestApp.res.getString(R.string.no_title)
                )
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (recyclerAdapter.isEmpty()) {
            viewModel.loadImages()
        }
    }


    // "пагинация"; дозагрузка к подходу конца RV, без индикатора загрузки
    private val onScrollChangeListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layManager = binding.postsRecyclerView.layoutManager!! as StaggeredGridLayoutManager
            val visibleItemCount = layManager.childCount
            val totalItemCount = layManager.itemCount
            val visibleItems = layManager.findFirstVisibleItemPositions(null)
            val firstVisibleItem = if (visibleItems.isNotEmpty()) visibleItems.first() else 0
            if (!isLoadMore && !isFinished) {
                if (visibleItemCount + firstVisibleItem + visibleThreshold >= totalItemCount
                    && firstVisibleItem >= 0
                ) {
                    page++
                    isLoadMore = true
                    viewModel.loadImages(page = page)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.postsRecyclerView.removeOnScrollListener(onScrollChangeListener)
    }

    override fun onResume() {
        super.onResume()
        binding.postsRecyclerView.addOnScrollListener(onScrollChangeListener)
    }

    // для корректного отображения изображений
    companion object {
        const val SPAN_COUNT = 2
        val screenWidth: Int = android.content.res.Resources.getSystem().displayMetrics.widthPixels
        private val screenHeight: Int =
            android.content.res.Resources.getSystem().displayMetrics.heightPixels
        val columnScreenWidth: Int = screenWidth / SPAN_COUNT
        val columnScreenHeight: Int = screenHeight / SPAN_COUNT
        const val visibleThreshold = 15
    }
}
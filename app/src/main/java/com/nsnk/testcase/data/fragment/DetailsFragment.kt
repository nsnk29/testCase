package com.nsnk.testcase.data.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.nsnk.testcase.R
import com.nsnk.testcase.data.LiveDataResult
import com.nsnk.testcase.data.adapter.ImgurCommentsAdapter
import com.nsnk.testcase.data.fragment.ImgurListFragment.Companion.screenWidth
import com.nsnk.testcase.data.model.CommentsResponseImgur
import com.nsnk.testcase.data.viewmodel.ImgurCommentViewModel
import com.nsnk.testcase.databinding.FragmentImgurImageDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentImgurImageDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<ImgurCommentViewModel>()
    private val recyclerAdapter = ImgurCommentsAdapter()


    private val dataObserver = Observer<LiveDataResult<CommentsResponseImgur>> { result ->
        when (result?.status) {
            LiveDataResult.Status.ERROR -> {
                result.response?.data
            }
            LiveDataResult.Status.SUCCESS -> {
                recyclerAdapter.updateList(result.response!!.data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.commentsApiLiveData.observe(viewLifecycleOwner, this.dataObserver)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_imgur_image_details, container, false
        )
        with(binding) {
            model = args.model
            mRecyclerAdapter = recyclerAdapter
            commentsRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    commentsRecyclerView.context,
                    LinearLayout.VERTICAL
                )
            )
            mViewModel = viewModel
        }
        setUpImageViewParams()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (recyclerAdapter.isEmpty()) {
            viewModel.loadComments(args.model.id)
        }
    }

    // подготовка ImageView для загрузки изображения
    // (изображение должно быть во всю ширину экрана)
    private fun setUpImageViewParams() {
        val imageWidth: Int = args.model.getImageWidth()
        val imageHeight: Int = args.model.getImageHeight()
        val realWidth = screenWidth
        val realHeight = imageHeight * realWidth / imageWidth
        binding.imageView.layoutParams = LinearLayout.LayoutParams(realWidth, realHeight).apply {
            gravity = Gravity.CENTER
        }
    }

}
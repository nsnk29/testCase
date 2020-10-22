package com.nsnk.testcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
    }

    private fun initRecycler() {
        val images = ArrayList<ImageModel>()
        val glide = GlideApp.with(this)
        fakeURLS.forEach {
            images.add(
                ImageModel(
                    url = it,
                    title = it,
                    ups = Random.nextInt(1000),
                    downs = Random.nextInt(100),
                    comments = Random.nextInt(250),
                    views = Random.nextInt(5000)
                )
            )
        }
        adapter = RecyclerAdapter(images, glide)
        postsRecyclerView.adapter = adapter
        postsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    companion object {
        val fakeURLS = listOf(
            "https://i.imgur.com/XVjrLZQ.jpeg",
            "https://i.imgur.com/FCBZ60L.png",
            "https://i.imgur.com/ICAfPvw.png",
            "https://i.imgur.com/M6bcx4X.jpeg",
            "https://i.imgur.com/pAfgOqf.jpeg",
            "https://i.imgur.com/UdTtXjZ.jpeg"
        )
    }
}
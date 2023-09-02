package com.criticalgnome.cleanarchitecturedemo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.criticalgnome.cleanarchitecturedemo.databinding.ActivityMainBinding
import com.criticalgnome.cleanarchitecturedemo.databinding.ItemMainBinding
import com.criticalgnome.cleanarchitecturedemo.ui.adapter.dsl.model
import com.criticalgnome.cleanarchitecturedemo.ui.adapter.dsl.recyclerViewAdapter
import com.criticalgnome.cleanarchitecturedemo.ui.main.MainContract.ViewState.*
import com.criticalgnome.domain.entity.PostModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRecycler.adapter = recyclerViewAdapter {
            define()
                .viewHolderOf(ItemMainBinding::inflate)
                .couldBeBoundTo<PostModel> { post ->
                    postTitle.text = post.title
                    postBody.text = post.body
                    root.setOnClickListener { Toast.makeText(this@MainActivity, post.title, Toast.LENGTH_SHORT).show() }
                }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collectLatest { state ->
                when (state) {
                    is PostsLoaded -> binding.mainRecycler.adapter?.model = state.posts
                    is ErrorHandled -> {}
                    is ExceptionHandled -> {}
                    null -> {}
                }
            }
        }

        viewModel.getPosts()
    }
}

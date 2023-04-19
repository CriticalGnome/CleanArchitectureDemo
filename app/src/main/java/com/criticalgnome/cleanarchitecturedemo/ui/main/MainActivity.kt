package com.criticalgnome.cleanarchitecturedemo.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.criticalgnome.cleanarchitecturedemo.databinding.ActivityMainBinding
import com.criticalgnome.cleanarchitecturedemo.ui.main.MainContract.ViewState.*
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

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collectLatest { state ->
                when (state) {
                    is PostsLoaded -> Toast.makeText(this@MainActivity, state.posts[0].body, Toast.LENGTH_SHORT).show()
                    is ErrorHandled -> {}
                    is ExceptionHandled -> {}
                    null -> {}
                }
            }
        }

        viewModel.getPosts()
    }
    // TEST
}

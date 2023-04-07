package com.criticalgnome.cleanarchitecturedemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.criticalgnome.cleanarchitecturedemo.dispatcher.DispatcherProvider
import com.criticalgnome.cleanarchitecturedemo.ui.main.MainContract.ViewState
import com.criticalgnome.cleanarchitecturedemo.ui.main.MainContract.ViewState.*
import com.criticalgnome.domain.entity.Result.*
import com.criticalgnome.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState?>(null)
    val viewState: StateFlow<ViewState?> get() = _viewState

    fun getPosts() {
        viewModelScope.launch(dispatcherProvider.io) {
            getPostsUseCase().collect { result ->
                 _viewState.value = when (result) {
                     is Success -> PostsLoaded(result.data)
                     is Error -> ErrorHandled(result.code, result.message)
                     is Exception -> ExceptionHandled(result.throwable)
                 }
            }
        }
    }
}

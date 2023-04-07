package com.criticalgnome.cleanarchitecturedemo.ui.main

import com.criticalgnome.domain.entity.PostModel

interface MainContract {
    sealed interface ViewState {
        data class PostsLoaded(val posts: List<PostModel>) : ViewState
        data class ErrorHandled(val code: Int, val message: String?) : ViewState
        data class ExceptionHandled(val throwable: Throwable) : ViewState
    }
}

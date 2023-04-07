package com.criticalgnome.cleanarchitecturedemo.ui.main

import com.criticalgnome.domain.entity.PostModel

interface MainContract {
    sealed interface ViewState {
        class PostsLoaded(val posts: List<PostModel>) : ViewState
        class ErrorHandled(val code: Int, val message: String?) : ViewState
        class ExceptionHandled(val throwable: Throwable) : ViewState
    }
}

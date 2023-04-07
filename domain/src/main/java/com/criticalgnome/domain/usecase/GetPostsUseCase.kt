package com.criticalgnome.domain.usecase

import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface GetPostsUseCase {
    suspend operator fun invoke() : Flow<Result<List<PostModel>>>
}

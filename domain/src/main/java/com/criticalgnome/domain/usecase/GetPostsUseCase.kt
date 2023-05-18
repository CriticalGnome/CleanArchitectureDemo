package com.criticalgnome.domain.usecase

import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result

interface GetPostsUseCase {
    suspend operator fun invoke() : Result<List<PostModel>>
}

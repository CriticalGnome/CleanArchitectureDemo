package com.criticalgnome.domain.usecase

import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetPostsUseCaseDefault @Inject constructor(
    private val postRepository: PostRepository
) : GetPostsUseCase {

    override suspend operator fun invoke(): Flow<Result<List<PostModel>>> {
        return flowOf(postRepository.getPosts())
    }
}

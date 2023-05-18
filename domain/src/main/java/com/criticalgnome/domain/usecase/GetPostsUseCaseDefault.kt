package com.criticalgnome.domain.usecase

import com.criticalgnome.domain.entity.PostModel
import com.criticalgnome.domain.entity.Result
import com.criticalgnome.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCaseDefault @Inject constructor(
    private val postRepository: PostRepository
) : GetPostsUseCase {

    override suspend operator fun invoke(): Result<List<PostModel>> {
        return postRepository.getPosts()
    }
}

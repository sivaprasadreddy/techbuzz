package com.sivalabs.techbuzz.posts.usecases.deletepost;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletePostHandler {

    private final PostRepository postRepository;

    private final VoteRepository voteRepository;

    public void deletePost(Long postId) {
        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        voteRepository.deleteVotesForPost(postId);
        postRepository.delete(post);
    }
}

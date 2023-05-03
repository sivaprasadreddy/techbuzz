package com.sivalabs.techbuzz.posts.usecases.deletepost;

import com.sivalabs.techbuzz.common.exceptions.ResourceNotFoundException;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.repositories.PostRepository;
import com.sivalabs.techbuzz.posts.domain.repositories.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeletePostHandler {
    private static final Logger log = LoggerFactory.getLogger(DeletePostHandler.class);

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;

    public DeletePostHandler(final PostRepository postRepository, final VoteRepository voteRepository) {
        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
    }

    public void deletePost(Long postId) {
        log.debug("Deleting post with id: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        voteRepository.deleteVotesForPost(post.getId());
        postRepository.delete(post.getId());
    }
}

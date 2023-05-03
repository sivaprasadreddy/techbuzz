package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.posts.usecases.createvote.CreateVoteRequest;
import com.sivalabs.techbuzz.posts.usecases.createvote.VoteHandler;
import com.sivalabs.techbuzz.users.domain.models.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
class AddVoteController {
    private final VoteHandler voteHandler;

    public AddVoteController(final VoteHandler voteHandler) {
        this.voteHandler = voteHandler;
    }

    @PostMapping("/api/votes")
    @ResponseStatus
    @AnyAuthenticatedUser
    public ResponseEntity<Void> createVote(@Valid @RequestBody CreateVoteRequest request, @CurrentUser User loginUser) {
        var createVoteRequest = new CreateVoteRequest(request.postId(), loginUser.getId(), request.value());
        voteHandler.addVote(createVoteRequest);
        return ResponseEntity.ok().build();
    }
}

package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.posts.domain.dtos.CreateVoteRequest;
import com.sivalabs.techbuzz.posts.domain.dtos.PostViewDTO;
import com.sivalabs.techbuzz.posts.domain.mappers.PostMapper;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.models.Vote;
import com.sivalabs.techbuzz.posts.domain.services.PostService;
import com.sivalabs.techbuzz.users.domain.models.User;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.MediaType;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Controller
class AddVoteController {
    private final PostService postService;

    @Resource(name = "postMapper")
    PostMapper postMapper;

    AddVoteController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/votes")
    @ResponseStatus
    @AnyAuthenticatedUser
    public ResponseEntity<Void> createVote(@Valid @RequestBody CreateVoteRequest request, @CurrentUser User loginUser) {
        var createVoteRequest = new CreateVoteRequest(request.postId(), loginUser.getId(), request.value());
        postService.addVote(createVoteRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/api/hx/votes/update/{voteType}/{postId}", produces = "text/html")
    @ResponseBody
    public String createVoteHTMX(@PathVariable String postId, @PathVariable String voteType, @CurrentUser User loginUser) {
        CreateVoteRequest createVoteRequest = new CreateVoteRequest(Long.valueOf(postId), loginUser.getId(), Integer.valueOf(voteType));
        postService.addVote(createVoteRequest);
        Post updatedPost = postService.getPost(Long.valueOf(postId));
        PostViewDTO postViewDTO = postMapper.toPostViewDTO(loginUser, updatedPost);
        long downVoteCount = postViewDTO.getDownVoteCount();
        long upVoteCount = postViewDTO.getUpVoteCount();

        StringBuilder returnHtml = new StringBuilder();
        returnHtml.append("<div id=\"vote-container-" + postId + "\">");
        returnHtml.append("<button class=\"btn btn-success" + (Integer.valueOf(voteType) == 1 ? " disabled" : "") + "\"");
        returnHtml.append(" id=\"upVoteButton\"");
        returnHtml.append(" hx-trigger=\"click\"");
        returnHtml.append(" hx-get=\"/api/hx/votes/update/1/" + postId + "\"");
        returnHtml.append(" hx-target=\"#vote-container-" + postId + "\"");
        returnHtml.append(" hx-swap=\"outerHTML\"");
        returnHtml.append(">");
        returnHtml.append("<span class=\"p-2 successCount\">" + upVoteCount + "</span>");
        returnHtml.append("<i class=\"fa-solid fa-thumbs-up\"></i>");
        returnHtml.append("</button>");
        returnHtml.append("<button class=\"btn btn-danger" + (Integer.valueOf(voteType) == -1 ? " disabled" : "") + "\"");
        returnHtml.append(" id=\"downVoteButton\"");
        returnHtml.append(" hx-trigger=\"click\"");
        returnHtml.append(" hx-get=\"/api/hx/votes/update/-1/" + postId + "\"");
        returnHtml.append(" hx-target=\"#vote-container-" + postId + "\"");
        returnHtml.append(" hx-swap=\"outerHTML\"");
        returnHtml.append(">");
        returnHtml.append("<span class=\"p-2 downvoteCount\">" + downVoteCount + "</span>");
        returnHtml.append("<i class=\"fa-solid fa-thumbs-down\"></i>");
        returnHtml.append("</button>");
        returnHtml.append("</div>");

        return returnHtml.toString();
    }

}






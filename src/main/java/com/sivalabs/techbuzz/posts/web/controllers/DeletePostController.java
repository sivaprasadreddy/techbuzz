package com.sivalabs.techbuzz.posts.web.controllers;

import com.sivalabs.techbuzz.common.exceptions.UnauthorisedAccessException;
import com.sivalabs.techbuzz.config.annotations.AnyAuthenticatedUser;
import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.posts.usecases.deletepost.DeletePostHandler;
import com.sivalabs.techbuzz.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class DeletePostController {

	private final DeletePostHandler deletePostHandler;

	@DeleteMapping("/posts/{id}")
	@ResponseStatus
	@AnyAuthenticatedUser
	public ResponseEntity<Void> deletePost(@PathVariable Long id, @CurrentUser User loginUser) {
		Post post = deletePostHandler.getPost(id);
		this.checkPrivilege(post, loginUser);
		deletePostHandler.deletePost(id);
		return ResponseEntity.ok().build();
	}

	private void checkPrivilege(Post post, User loginUser) {
		if (!(Objects.equals(post.getCreatedBy().getId(), loginUser.getId()) || loginUser.isAdminOrModerator())) {
			throw new UnauthorisedAccessException("Permission Denied");
		}
	}

}
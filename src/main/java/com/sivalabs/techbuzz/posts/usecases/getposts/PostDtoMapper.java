package com.sivalabs.techbuzz.posts.usecases.getposts;

import com.sivalabs.techbuzz.posts.domain.entities.Post;
import com.sivalabs.techbuzz.users.domain.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PostDtoMapper {

	public PostDTO toDTO(User loginUser, Post post) {
		String category = null;
		if (post.getCategory() != null) {
			category = post.getCategory().getName();
		}
		boolean editable = this.canCurrentUserEditPost(loginUser, post);
		return new PostDTO(post.getId(), post.getTitle(), post.getUrl(), post.getContent(), category,
				post.getCreatedBy().getId(), post.getCreatedBy().getName(), post.getCreatedAt(), post.getUpdatedAt(),
				editable);
	}

	public boolean canCurrentUserEditPost(User loginUser, Post post) {
		return loginUser != null && post != null
				&& (Objects.equals(post.getCreatedBy().getId(), loginUser.getId()) || loginUser.isAdminOrModerator());
	}

}

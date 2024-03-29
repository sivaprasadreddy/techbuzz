package com.sivalabs.techbuzz.posts.jobs;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.services.PostService;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewPostsNotificationJob {

    private static final Logger log = LoggerFactory.getLogger(NewPostsNotificationJob.class);

    private final PostService postService;
    private final UserService userService;
    private final ApplicationProperties properties;

    public NewPostsNotificationJob(PostService postService, UserService userService, ApplicationProperties properties) {
        this.postService = postService;
        this.userService = userService;
        this.properties = properties;
    }

    @Scheduled(cron = "${techbuzz.new-posts-notification-frequency}")
    public void notifyUsersAboutNewPosts() {
        LocalDateTime createdDateFrom =
                LocalDateTime.now().with(LocalTime.MIDNIGHT).minusDays(properties.newPostsAgeInDays());
        List<Post> posts = postService.findPostCreatedFrom(createdDateFrom);
        if (posts.size() > 0) {
            List<String> emailIds = userService.findVerifiedUsersMailIds();
            if (emailIds.size() > 0) {
                postService.sendNewPostsNotification(posts, emailIds.stream().collect(Collectors.joining(",")));
            }
        }
    }
}

package com.sivalabs.techbuzz.posts.jobs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import com.sivalabs.techbuzz.posts.domain.models.Post;
import com.sivalabs.techbuzz.posts.domain.services.PostService;
import com.sivalabs.techbuzz.users.domain.services.UserService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;

public class NewPostNotificationJobTest extends AbstractIntegrationTest {

    private NewPostsNotificationJob job;

    @SpyBean
    UserService userService;

    @SpyBean
    PostService postService;

    @Autowired
    ApplicationProperties properties;

    @Captor
    private ArgumentCaptor<String> emailIds;

    @Captor
    private ArgumentCaptor<List<Post>> posts;

    @BeforeEach
    void setUp() {
        job = new NewPostsNotificationJob(postService, userService, properties);
    }

    @Test
    @Sql("/posts_created_before_a_month.sql")
    void shouldNotSendNotificationWhenNoNewPosts() {
        job = new NewPostsNotificationJob(postService, userService, properties);
        job.notifyUsersAboutNewPosts();
        verify(postService, never()).sendNewPostsNotification(null, null);
        /* verify(postService)
                .sendNewPostsNotification(
                        argThat(posts ->  posts.size()==2), argThat(emailIds -> emailIds.contains(",")));
        */
    }

    @Test
    @Sql("/posts_with_mixed_date_range.sql")
    void shouldFetchOnlyPostsWithinTimelineDefined() {
        job = new NewPostsNotificationJob(postService, userService, properties);
        job.notifyUsersAboutNewPosts();
        verify(postService).sendNewPostsNotification(posts.capture(), emailIds.capture());
        String emailCaptorValue = emailIds.getValue();
        assertThat(emailCaptorValue.contains(","));
        List<Post> postsCaptorValue = posts.getValue();
        assertThat(postsCaptorValue).extracting(Post::getCreatedAt).allSatisfy(date -> assertThat(date)
                .isAfterOrEqualTo(
                        LocalDateTime.now().with(LocalTime.MIDNIGHT).minusDays(properties.newPostsAgeInDays())));
    }
}

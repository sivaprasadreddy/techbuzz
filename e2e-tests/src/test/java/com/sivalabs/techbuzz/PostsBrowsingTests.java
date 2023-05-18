package com.sivalabs.techbuzz;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PostsBrowsingTests extends BaseTest {

    @Test
    void shouldViewCategoriesOnHomePage() {
        page.navigate(rootUrl);
        int postsCount = page.locator(".category-card").count();
        assertThat(postsCount).isGreaterThan(0);
    }

    @ParameterizedTest
    @CsvSource({"java", "webdev", "go", "python", "nodejs", "devops", "testing", "career", "general"})
    void shouldViewPostsOnCategoryPage(String category) {
        page.navigate(rootUrl + "/c/" + category);
        int postsCount = page.locator(".post").count();
        assertThat(postsCount).isGreaterThan(0);
    }

    @ParameterizedTest
    @CsvSource({"java"})
    void shouldNavigateBetweenPostPagesUsingPaginator(String category) {
        page.navigate(rootUrl + "/c/" + category);
        int postsCount = page.locator(".post").count();
        assertThat(postsCount).isGreaterThan(0);
        page.locator("text='Next'").first().click();
        page.locator("text='Previous'").first().click();
        page.locator("text='Last'").first().click();
        page.locator("text='First'").first().click();
    }
}

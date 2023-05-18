package com.sivalabs.techbuzz;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Test;

class AuthenticatedUserActionsTests extends BaseTest {

    @Test
    void shouldViewHomePageAsLoggedInUser() {
        doLogin(configuration.getNormalUserEmail(), configuration.getNormalUserPassword());
    }

    @Test
    void shouldAddNewPost() {
        doLogin(configuration.getNormalUserEmail(), configuration.getNormalUserPassword());
        page.locator("a:has-text('Add')").click();
        page.locator("#title").fill("SivaLabs");
        page.locator("#url").fill("https://sivalabs.in");
        page.locator("#categoryId-selectized").fill("java");
        page.locator("#categoryId-selectized").press("Enter");
        page.locator("#content").fill("My experiments with technology");
        page.locator("button:has-text('Submit')").click();
        page.waitForURL(rootUrl + "/c/java");

        assertEquals(rootUrl + "/c/java", page.url());
        assertEquals("TechBuzz - Java", page.title());
    }

    @Test
    void shouldEditPost() {
        doLogin(configuration.getAdminUserEmail(), configuration.getAdminUserPassword());
        page.navigate(rootUrl + "/c/java");
        page.locator("a:has-text('Edit')").first().click();
        page.locator("#url").fill("https://sivalabs.in");
        page.locator("#title").fill("SivaLabs");
        page.locator("#categoryId-selectized").fill("general");
        page.locator("#categoryId-selectized").press("Enter");
        page.locator("#content").fill("Learn, Practice, Teach");
        page.locator("button:has-text('Submit')").click();

        Locator locator = page.getByText("Post updated successfully");
        assertThat(locator).isVisible();
    }

    @Test
    void shouldDeletePost() {
        doLogin(configuration.getAdminUserEmail(), configuration.getAdminUserPassword());
        page.navigate(rootUrl + "/c/java");
        page.onDialog(Dialog::accept);
        page.locator("button:has-text('Delete')").first().click();
        page.waitForURL(rootUrl + "/c/java");
    }

    private void doLogin(String email, String password) {
        page.navigate(rootUrl + "/login");
        page.locator("#username").fill(email);
        page.locator("#password").fill(password);
        page.locator("button:has-text('Login')").click();
        page.waitForURL(rootUrl + "/");
        assertEquals(rootUrl + "/", page.url());
    }
}

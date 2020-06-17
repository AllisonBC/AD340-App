package com.example.ad340app_a1;

import android.os.SystemClock;
import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class TabsTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void hasTabTest() throws InterruptedException {

        Matcher<View> matcher = allOf(withText("Profile"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800); // Wait a little until the content is loaded

        onView(withId(R.id.tabs)).check(matches(isCompletelyDisplayed()));

    }

////////////////////PROFILE//////////////////////////////////////////////////////////////

    public void hasTextOnScreen_Profile() {
        onView(withId(R.id.profile_name))
                .check(matches(withText("Allison")));
    }


////////////////////SETTINGS//////////////////////////////////////////////////////////////
        public void hasTextOnScreen_Settings() {
            onView(withId(R.id.tvReminder))
                    .check(matches(withText(R.string.daily_match_reminder)));
            onView(withId(R.id.tvGender))
                    .check(matches(withText(R.string.gender)));
            onView(withId(R.id.tvDistance))
                    .check(matches(withText(R.string.max_search_dist)));
            onView(withId(R.id.tvPrivacy))
                    .check(matches(withText(R.string.privacy)));
            onView(withId(R.id.tvInterestRange))
                    .check(matches(withText(R.string.age_interest)));
            onView(withId(R.id.save_button))
                    .check(matches(withText(R.string.save)));
        }

        @Test
        public void canClickPrivacyButton() {
            onView(withId(R.id.radio_private)).perform(click());
        }

////////////////////MATCHES//////////////////////////////////////////////////////////////

        public void hasTextOnScreen_Matches()
        {
            //click like buttons BEWARE distance value
            onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions
                    .actionOnItemAtPosition(0, click()));
        }

        // Check toasts given R.id.String as int param for Settings
        public void checkToastMessage(int text) {
            onView(withText(text))
                    .inRoot(withDecorView(not(activityTestRule.getActivity()
                            .getWindow().getDecorView()))).check(matches(isDisplayed()));
        }
    }
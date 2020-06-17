package com.example.ad340app_a1;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void hasTextOnScreen() {
        onView(withId(R.id.welcome))
                .check(matches(withText(R.string.welcome)));

        onView(withId(R.id.textViewDOB))
                .check(matches(withText(R.string.dob_entry)));
    }

    @Test
    public void hasTextOnScreenClick() {
        onView(withId(R.id.welcome))
                .check(matches(withText(R.string.welcome)));

        onView(withId(R.id.textViewDOB))
                .check(matches(withText(R.string.dob_entry)));
    }

    @Test
    public void hasTextOnScreenRotation() {
        onView(withId(R.id.user_email))
                .check(matches(withText(R.string.emailTest)));
    }

    @Test
    public void canEnterNameAndLoginWithRotate() {
        onView(withId(R.id.user_name)).perform(typeText("Allison"));

        onView(withId(R.id.login_btn)).perform(click());

        onView(withId(R.id.profile_name))
                .check(matches(withText("Allison")));

        TestUtils.rotateScreen(activityTestRule.getActivity());

        onView(withId(R.id.profile_name))
                .check(matches(withText("Allison")));
    }

    @Test
    public void retainsStateAfterRotate() {
        onView(withId(R.id.login_btn)).perform(scrollTo(), click());
        onView(withId(R.id.login_btn)).check(matches(withText(R.string.submit)));
        TestUtils.rotateScreen(activityTestRule.getActivity());

        onView(withId(R.id.login_btn)).check(matches(withText(R.string.submit)));
    }

    @Test
    public void signUp_TestPass() {
        onView(withId(R.id.user_name)).perform(typeText("Allison"));
        onView(withId(R.id.user_dob_picker)).perform(PickerActions
                .setDate(1989, 6, 30));
    }

    @Test
    public void signUpAge_TestFail() {
        underage_TestFail();
        onView(withText(R.string.age_invalid))
                .inRoot(withDecorView(not(activityTestRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
    @Test
    private void underage_TestFail(){
        onView(withId(R.id.user_name)).perform(typeText("Allison"));
        onView(withId(R.id.user_dob_picker)).perform(PickerActions
                .setDate(2019, 6, 30));
    }

    @Test
    public void signUpNoName_TestFail() {
        noName_TestFail();
        onView(withText(R.string.age_invalid))
                .inRoot(withDecorView(not(activityTestRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
    @Test
    private void noName_TestFail(){
        onView(withId(R.id.user_dob_picker)).perform(PickerActions
                .setDate(1989, 6, 30));
    }
}
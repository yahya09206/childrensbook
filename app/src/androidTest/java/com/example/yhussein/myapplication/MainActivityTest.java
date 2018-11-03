package com.example.yhussein.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.yhussein.myapplication.MyViewAction.clickChildViewWithId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.yhussein.myapplication", appContext.getPackageName());
    }

    @Test
    public void testInfoDisplay() {
        onView(withIndex(withId(R.id.book_title_id), 0))
                .check(matches(withText("Great Book Title 1")));
        onView(withIndex(withId(R.id.book_author_id), 0))
                .check(matches(withText("by Mekone Tolrom")));
        onView(withIndex(withId(R.id.book_img_id), 0)).perform(click());
    }

    @Test
    public void testNavigateToReading() {
        onView(withIndex(withId(R.id.book_img_id), 0)).perform(click());
        onView(withId(R.id.book_img_id)).check(matches(isDisplayed()));
        onView(withId(R.id.lang)).check(matches(isDisplayed()));
        onView(withId(R.id.close)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));
        onView(withId(R.id.prev)).check(matches(isDisplayed()));
        onView(withId(R.id.play)).check(matches(isDisplayed()));
        onView(withId(R.id.play)).perform(click());
        onView(withId(R.id.book_img_id)).perform(click());
        onView(withId(R.id.next)).perform(click());
        onView(withId(R.id.prev)).perform(click());
        onView(withId(R.id.close)).perform(click());
    }

    @Test
    public void testCardViewDisplay() {
        onView(withIndex(withId(R.id.card_view), 0)).check(matches(isDisplayed()));
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    public class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("You liked");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    //means this window isn't contained by any other windows.
                }
            }
            return false;
        }
    }
}

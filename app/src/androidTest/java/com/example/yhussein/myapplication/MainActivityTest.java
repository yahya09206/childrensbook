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
        onView(withId(R.id.action_button))
                .check(matches(withText("Read")));
        onView(withId(R.id.book_title_id))
                .check(matches(withText("Great Book Title 1")));
        onView(withId(R.id.book_author_id))
                .check(matches(withText("by Mekone Tolrom")));
    }

    @Test
    public void testNavigateToReading() {
        onView(withId(R.id.action_button)).perform(click());
        onView(withId(R.id.txttitle))
                .check(matches(withText("Great Book Title 1 by Mekone Tolrom")));
        onView(withId(R.id.txtCat))
                .check(matches(withText("[0/26]")));
        onView(withId(R.id.txtDesc))
                .check(matches(withText("")));
        onView(withIndex(withId(R.id.next_button), 0)).perform(click());
        onView(withIndex(withId(R.id.previous_button), 0)).perform(click());
        onView(withIndex(withId(R.id.profile_button), 0)).perform(click());
    }

    @Test
    public void testSoundDisplay() {
        //onView(withId(R.id.book_img_id))
        //.check(matches(withDrawable(R.drawable.image1)));
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

    @Test
    public void testMatches() {
        onView(withIndex(withId(R.id.card_view), 0)).check(matches(isDisplayed()));
    }
}

package com.example.yhussein.myapplication;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.yhussein.myapplication.MyViewAction.clickChildViewWithId;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
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

    /*@Test
    public void TestToastDisplay(){
        onView(withText("Welcome back! You have 2 books!")).inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }*/

    @Test
    public void TestLibrary(){
        onView(withIndex(withId(R.id.book_title_id), 0))
                .check(matches(withText("Great Book Title 1")));
        onView(withIndex(withId(R.id.book_author_id), 0))
                .check(matches(withText("by Mekone Tolrom")));
        onView(withIndex(withId(R.id.book_title_id), 0)).check(matches(isDisplayed()));
    }

    @Test
    public void TestStateIntent(){
        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Bookmark", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Sound", "On");
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(MainActivity.RESULT_OK, intent);
    }

    @Test
    public void TestLanguageChange(){
        onView(withIndex(withId(R.id.book_img_id), 0)).perform(click());
        onView(withId(R.id.lang)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);

        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(MainActivity.RESULT_OK, intent);

        onView(withId(R.id.book_img_id)).check(matches(isDisplayed()));
    }

    @Test
    public void TestSoundChange(){
        onView(withIndex(withId(R.id.book_img_id), 0)).perform(click());
        onView(withId(R.id.son)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(0).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);

        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(MainActivity.RESULT_OK, intent);

        onView(withId(R.id.book_img_id)).check(matches(isDisplayed()));

    }

    @Test
    public void TestPreviewChange(){
        onView(withIndex(withId(R.id.book_img_id), 0)).perform(click());
        onView(withIndex(withId(R.id.prev), 0)).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);

        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(MainActivity.RESULT_OK, intent);

        onView(withId(R.id.book_img_id)).check(matches(isDisplayed()));

    }

    @Test
    public void TestBookEntity() throws Exception {
        Setting setting = new Setting(1, "Great Book Title 1", "Mekone Tolrom", "mekone",
                "0", "english", "On",
                "1", "audio1_english.mp3", "book1_english.txt", 1);
        assertThat(1, equalTo(setting.getBookId()));
        assertThat("Great Book Title 1", equalTo(setting.getBookTitle().toString()));
        assertThat("Mekone Tolrom", equalTo(setting.getBookAuthor().toString()));
        assertThat("mekone", equalTo(setting.getReaderName().toString()));
        assertThat("0", equalTo(setting.getBookMark().toString()));
        assertThat("english", equalTo(setting.getReadingLanguage().toString()));
        assertThat("On", equalTo(setting.getSoundStatus().toString()));
        assertThat("audio1_english.mp3", equalTo(setting.getAudioUrl().toString()));
        assertThat("book1_english.txt", equalTo(setting.getContentUrl().toString()));
        assertThat(1, equalTo(setting.getLikeCount()));
    }

    @Test
    public void TestSetBookEntity() throws Exception {
        Setting setting = new Setting(1, "Great Book Title 1", "Mekone Tolrom", "mekone",
                "0", "english", "On",
                "1", "audio1_english.mp3", "book1_english.txt", 1);
        setting.setAudioUrl("");
        setting.setContentUrl("");
        setting.setSoundStatus("");
        setting.setReadingLanguage("");
        setting.setBookMark("");
        setting.setReaderName("");
        setting.setBookAuthor("");
        setting.setBookTitle("");
        setting.setBookId(1);
        setting.setPhotoId("");
        setting.setLikeCount(0);

        assertThat(1, equalTo(setting.getBookId()));
        assertThat("", equalTo(setting.getBookTitle().toString()));
        assertThat("", equalTo(setting.getBookAuthor().toString()));
        assertThat("", equalTo(setting.getReaderName().toString()));
        assertThat("", equalTo(setting.getBookMark().toString()));
        assertThat("", equalTo(setting.getReadingLanguage().toString()));
        assertThat("", equalTo(setting.getSoundStatus().toString()));
        assertThat("", equalTo(setting.getAudioUrl().toString()));
        assertThat("", equalTo(setting.getContentUrl().toString()));
        assertThat(0, equalTo(setting.getLikeCount()));
    }

    @Test
    public void testNavigateToReading() {
        onView(withIndex(withId(R.id.book_img_id), 0)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.book_title_id), 0)).perform(closeSoftKeyboard());
        onView(withIndex(withId(R.id.book_img_id), 0)).perform(click());
        onView(withId(R.id.book_img_id)).check(matches(isDisplayed()));
        onView(withId(R.id.lang)).check(matches(isDisplayed()));
        onView(withId(R.id.close)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));
        onView(withId(R.id.prev)).check(matches(isDisplayed()));
        onView(withId(R.id.play)).check(matches(isDisplayed()));;
        onView(withId(R.id.play)).perform(click());
        onView(withId(R.id.book_img_id)).perform(click());
        onView(withId(R.id.next)).perform(click());
        onView(withId(R.id.prev)).perform(click());
        onView(withId(R.id.close)).perform(click());
    }

    @Test
    public void testPOJO() {
        onView(withId(R.id.recyclerView_id)).check(new Matchers(2));
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
            description.appendText("Welcome");
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

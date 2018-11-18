package com.example.yhussein.myapplication;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static com.example.yhussein.myapplication.MainActivityTest.withIndex;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ReadActivityTest {

    private static Intent intent;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    public IntentsTestRule<ReadActivity> mIntentsRule =
            new IntentsTestRule<>(ReadActivity.class);

    @Rule
    public ActivityTestRule<ReadActivity> activityTestRule
            = new ActivityTestRule<ReadActivity>(ReadActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            intent = new Intent();
            intent.putExtra("Id", 1);
            intent.putExtra("Sound", "On");
            intent.putExtra("Section", 0);
            intent.putExtra("Language", "english");
            intent.putExtra("Bookmark", 1);
            return intent;
        }
    };

    @Test
    public void TestBundleLang(){
        onView(withId(R.id.lang)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        Bundle b = intent.getExtras();
        String language = b.getString("Language");
        assertEquals(language, "english");
        intended(hasComponent(ReadActivity.class.getName()));
        onView(withId(R.id.book_img_id_read)).check(matches(isDisplayed()));
    }

    @Test
    public void TestBundleSound(){
        onView(withId(R.id.son)).perform(click());
        Bundle b = intent.getExtras();
        String language = b.getString("Sound");
        assertEquals(language, "On");
        intended(hasComponent(ReadActivity.class.getName()));
        onView(withId(R.id.book_img_id_read)).check(matches(isDisplayed()));
    }

    @Test
    public void TestNextChange(){
        onView(withIndex(withId(R.id.book_img_id_read), 0)).perform(click());
        onView(withIndex(withId(R.id.next), 0)).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);

        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(ReadActivity.RESULT_OK, intent);

        onView(withId(R.id.book_img_id_read)).check(matches(isDisplayed()));

    }

    @Test
    public void TestPreviousChange(){
        onView(withIndex(withId(R.id.book_img_id_read), 0)).perform(click());
        onView(withIndex(withId(R.id.prev), 0)).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);

        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(ReadActivity.RESULT_OK, intent);

        onView(withId(R.id.book_img_id_read)).check(matches(isDisplayed()));

    }

    @Test
    public void TestPlayChange(){
        onView(withIndex(withId(R.id.book_img_id_read), 0)).perform(click());
        onView(withIndex(withId(R.id.play), 0)).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);

        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(ReadActivity.RESULT_OK, intent);

        onView(withId(R.id.book_img_id_read)).check(matches(isDisplayed()));

    }

    @Test
    public void TestImagebutton(){
        onView(withIndex(withId(R.id.book_img_id_read), 0)).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);

        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(ReadActivity.RESULT_OK, intent);

        onView(withId(R.id.book_img_id_read)).check(matches(isDisplayed()));

    }

    @Test
    public void TestRecIntent(){
        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);
        Bundle extras = intent.getExtras();
        int id = extras.getInt(Intent.EXTRA_TEXT);
    }

    @Test
    public void TestImageclick(){
        onView(withId(R.id.book_img_id_read)).perform(click());
        onView(withId(R.id.play)).check(matches(isDisplayed()));
        onView(withId(R.id.son)).check(matches(isDisplayed()));
        onView(withId(R.id.prev)).check(matches(isDisplayed()));
        onView(withId(R.id.lang)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));
        onView(withId(R.id.close)).check(matches(isDisplayed()));
    }

    @Test
    public void testIntentsSoundOn() {
        //from ActivityA, click the button which starts the ActivityB
        onView(withId(R.id.son)).perform(click());

        //validate intent and check its data
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Sound", "On")
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Language", "english")
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Id", 1)
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Section", 0)
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Bookmark", 1)
        ));
    }

    /*@Test
    public void testIntentsSoundOff() {
        //from ActivityA, click the button which starts the ActivityB
        onView(withId(R.id.son)).perform(click());

        //validate intent and check its data
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Sound", "On")
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Language", "english")
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Id", 1)
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Section", 0)
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Bookmark", 1)
        ));
    }*/

    @Test
    public void testIntentsLang() {
        //from ActivityA, click the button which starts the ActivityB
        onView(withId(R.id.lang)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("english"))).perform(click());

        //validate intent and check its data
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Sound", "On")
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Language", "english")
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Id", 1)
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Section", 0)
        ));
        intended(allOf(
                toPackage("com.example.yhussein.myapplication"),
                hasExtra("Bookmark", 1)
        ));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.yhussein.myapplication", appContext.getPackageName());
    }

    @Test
    public void testReadingPane() {
        onView(withId(R.id.book_img_id_read)).check(matches(isDisplayed()));
        onView(withId(R.id.lang)).check(matches(isDisplayed()));
        onView(withId(R.id.close)).check(matches(isDisplayed()));
        onView(withId(R.id.next)).check(matches(isDisplayed()));
        onView(withId(R.id.prev)).check(matches(isDisplayed()));
        onView(withId(R.id.play)).check(matches(isDisplayed()));
        onView(withId(R.id.play)).perform(click());
        onView(withId(R.id.book_img_id_read)).perform(click());
        onView(withId(R.id.next)).perform(click());
        onView(withId(R.id.prev)).perform(click());
        onView(withId(R.id.close)).perform(click());
    }

    @Test
    public void TestPlay(){
        onView(withId(R.id.play)).perform(click());
        onView(withId(R.id.book_img_id_read)).perform(click());
        onView(withId(R.id.book_img_id_read)).check(matches(isDisplayed()));
        onView(withId(R.id.close)).perform(click());
    }

    @Test
    public void TestLanguageIntents() {
        onView(withId(R.id.lang)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "french");
        intent.putExtra("Bookmark", 1);

        //validate intent and check its data
        int id = intent.getExtras().getInt("Id");
        String sound = intent.getExtras().getString("Sound");
        int section = intent.getExtras().getInt("Section");
        String language = intent.getExtras().getString("Language");
        int bookmark = intent.getExtras().getInt("Bookmark");

        assertEquals(id, 1);
        assertEquals(sound, "On");
        assertEquals(section, 0);
        assertEquals(language, "french");
        assertEquals(bookmark, 1);
    }

    @Test
    public void TestSoundIntents() {
        onView(withId(R.id.son)).perform(click());
        //onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "french");
        intent.putExtra("Bookmark", 1);

        //validate intent and check its data
        int id = intent.getExtras().getInt("Id");
        String sound = intent.getExtras().getString("Sound");
        int section = intent.getExtras().getInt("Section");
        String language = intent.getExtras().getString("Language");
        int bookmark = intent.getExtras().getInt("Bookmark");

        assertEquals(id, 1);
        assertEquals(sound, "On");
        assertEquals(section, 0);
        assertEquals(language, "french");
        assertEquals(bookmark, 1);
    }

    @Test
    public void TestNextIntents() {
        onView(withId(R.id.next)).perform(click());
        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "french");
        intent.putExtra("Bookmark", 1);

        //validate intent and check its data
        int id = intent.getExtras().getInt("Id");
        String sound = intent.getExtras().getString("Sound");
        int section = intent.getExtras().getInt("Section");
        String language = intent.getExtras().getString("Language");
        int bookmark = intent.getExtras().getInt("Bookmark");

        assertEquals(id, 1);
        assertEquals(sound, "On");
        assertEquals(section, 0);
        assertEquals(language, "french");
        assertEquals(bookmark, 1);
    }

    @Test
    public void TestPreviousIntents() {
        onView(withId(R.id.prev)).perform(click());
        Intent intent = new Intent();
        intent.putExtra("Id", 1);
        intent.putExtra("Sound", "On");
        intent.putExtra("Section", 0);
        intent.putExtra("Language", "english");
        intent.putExtra("Bookmark", 1);

        //validate intent and check its data
        int id = intent.getExtras().getInt("Id");
        String sound = intent.getExtras().getString("Sound");
        int section = intent.getExtras().getInt("Section");
        String language = intent.getExtras().getString("Language");
        int bookmark = intent.getExtras().getInt("Bookmark");

        assertEquals(id, 1);
        assertEquals(sound, "On");
        assertEquals(section, 0);
        assertEquals(language, "english");
        assertEquals(bookmark, 1);
    }

    @Test
    public void TestUpdateStateEntity() throws Exception {
        State state = new State();
        state.setSoundStatus("On");
        state.setReaderLanguage("french");
        state.setReaderIp("");
        state.setStateId(1);
        state.setBookMark(0);

        Assert.assertThat(1, equalTo(state.getStateId()));
        Assert.assertThat("On", equalTo(state.getSoundStatus()));
        Assert.assertThat("french", equalTo(state.getReaderLanguage()));
        Assert.assertThat("", equalTo(state.getReaderIp()));
        Assert.assertThat(0, equalTo(state.getBookMark()));
    }
}

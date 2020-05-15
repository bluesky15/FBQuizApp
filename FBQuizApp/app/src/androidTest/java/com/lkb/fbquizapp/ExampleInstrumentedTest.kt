package com.lkb.fbquizapp

import android.content.ComponentName
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.lkb.fbquizapp.view.main.MainActivity
import com.lkb.fbquizapp.view.quiz.QuizActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Rule
    @JvmField
    val mainActivityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    @JvmField
    val intentTestRule = IntentsTestRule(MainActivity::class.java)

    @get:Rule
    @JvmField
    val intentTestRuleQuiz = IntentsTestRule(QuizActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.lkb.fbquizapp", appContext.packageName)
    }

    @Test
    fun enter_name_in_name_field() {
        onView(withId(R.id.userName)).perform(typeText("John Doe"))
        //onView(withId(R.id.userAge)).perform(typeText("22"))
    }

    @Test
    fun enter_age_in_age_field() {
        onView(withId(R.id.userAge)).perform(typeText("22"))
    }

    @Test
    fun enter_gender_in_gender_field() {
        onView(withId(R.id.userGender)).perform(typeText("male"))
    }

    @Test
    fun button_click_check_correct_activity_launched() {
        Intents.init()
        onView(withId(R.id.button)).perform(click())
        intended(
            hasComponent(
                ComponentName(
                    InstrumentationRegistry.getInstrumentation().targetContext,
                    QuizActivity::class.java
                )
            )
        )
        Intents.release();
    }
}
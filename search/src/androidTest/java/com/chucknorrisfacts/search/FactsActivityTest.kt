package com.chucknorrisfacts.search

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.chucknorrisfacts.jokeModelValueLessThan80
import com.chucknorrisfacts.jokeModelValueMoreThan80
import com.chucknorrisfacts.jokeModelValueWithOutCategories
import com.chucknorrisfacts.search.data.model.JokeModel
import com.chucknorrisfacts.search.presentation.facts.FactsActivity
import com.chucknorrisfacts.search.presentation.search.SearchActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FactsActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(FactsActivity::class.java)


    @Test
    fun whenActivityIsLaunched_shouldDisplayInitialState() {
        onView(withId(R.id.emptyState)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenSelectMenu_shouldOpenSearchActivity() {
        Intents.init()

        val matcher = hasComponent(SearchActivity::class.java.name)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        intending(matcher).respondWith(result)

        onView(withId(R.id.menu_search)).perform(click())
        intended(matcher)
        Intents.release()
    }

    @Test
    fun whenOnActivityResult_showListJoker() {
        configResultIntent(jokeModelValueLessThan80)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun whenOnActivityResult_configListJoker_valueLessThan80() {
        configResultIntent(jokeModelValueLessThan80)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        onView(
            allOf(withId(R.id.title), hasSibling(withText("Chuck Norris`s test cases cover your code too.")))
        ).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenOnActivityResult_configListJoker_valueMoreThan80() {
        configResultIntent(jokeModelValueMoreThan80)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.titleLong), hasSibling(
                    withText(
                        "Chuck Norris`s test cases cover your code too. " +
                                "Chuck Norris`s test cases cover your code too. Chuck Norris`s test cases cover your code too.\" +\n" +
                                "            \"Chuck Norris`s test cases cover your code too. Chuck Norris`s test cases cover your code too."
                    )
                )
            )
        ).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenOnActivityResult_showCategoriesUncategorized() {
        configResultIntent(jokeModelValueWithOutCategories)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.categoryTitle),
                hasSibling(withText("UNCATEGORIZED"))
            )
        ).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenOnActivityResult_showCategories() {
        configResultIntent(jokeModelValueMoreThan80)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        onView(allOf(withId(R.id.titleLong), hasSibling(withText("MOVIE")))).check(matches(not(isDisplayed())))
    }

    private fun configResultIntent(jokeModel: JokeModel) {
        val resultData = Intent()
        resultData.putExtra(FactsActivity.FACTS_EXTRA, jokeModel)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

        val expectedIntent = allOf(hasAction(Intent.ACTION_PICK))
        Intents.init()
        intending(expectedIntent).respondWith(result)
    }
}

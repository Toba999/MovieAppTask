package com.example.pop_flake.searchScreen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pop_flake.MainActivity
import com.example.pop_flake.R
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)



    @Test
    fun searchFragment_DisplaySearchScreen() {
        // Perform a click to navigate to the SearchFragment
        onView(withId(R.id.navigation_search)).perform(click())

        onView(withId(R.id.parent_search_fragment)).check(matches(isDisplayed()))
        // Type a search query in the search view
        onView(withId(R.id.searchView)).perform(click())

    }

}

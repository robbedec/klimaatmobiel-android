package com.example.projecten3android

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.klimaatmobiel.ui.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import org.hamcrest.Matchers.anything
import org.junit.matchers.JUnitMatchers.containsString


/**
 * Test the behavior of the RecyclerView.
 * When asserting the amount of products in the list, include the category labels.
 */
@RunWith(AndroidJUnit4::class)
class WebshopResults {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.project_code_text))
            .perform(ViewActions.clearText())

        onView(withId(R.id.project_code_text))
            .perform(ViewActions.typeText("212345"))
        onView(withId(R.id.webshop_button)).perform(click())

        // Wait while the shop is loading
        SystemClock.sleep(2000)
    }

    @Test
    fun testListContainsProducts() {
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.products_list)
        val amountOfItems = recyclerView.adapter!!.itemCount

        assertEquals(6, amountOfItems)
    }

    @Test
    fun testListContainsFilteredProducts() {
        onView(withId(R.id.filter_text))
            .perform(ViewActions.typeText("pl"))

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.products_list)
        val amountOfItems = recyclerView.adapter!!.itemCount

        assertEquals(4, amountOfItems)
    }

    @Test
    fun testListContainsProductsFilterRemoved() {
        onView(withId(R.id.filter_text))
            .perform(ViewActions.typeText("pl"))

        onView(withId(R.id.filter_text))
            .perform(ViewActions.clearText())

        SystemClock.sleep(1500)

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.products_list)
        val amountOfItems = recyclerView.adapter!!.itemCount

        assertEquals(6, amountOfItems)
    }

    @Test
    fun testListCombineFilters() {
        onView(withId(R.id.filter_text))
            .perform(ViewActions.typeText("pl"))

        onView(withId(R.id.positionSpinner)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.positionSpinner)).check(matches(withSpinnerText(containsString("Bouwmaterialen"))))

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.products_list)
        val amountOfItems = recyclerView.adapter!!.itemCount

        assertEquals(2, amountOfItems)
    }
}
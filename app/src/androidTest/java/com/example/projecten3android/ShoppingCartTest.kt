package com.example.projecten3android


import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.klimaatmobiel.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ShoppingCartTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun shoppingCartTest() {
        onView(withId(R.id.project_code_text))
            .perform(ViewActions.clearText())

        onView(withId(R.id.project_code_text))
            .perform(ViewActions.typeText("212345"))
        onView(withId(R.id.webshop_button)).perform(click())

        // Wait while the shop is loading
        SystemClock.sleep(3000)


        val appCompatImageView = onView(
            allOf(
                withIndex(withId(R.id.add_to_cart_image), 0),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val appCompatImageView2 = onView(
            allOf(
                withIndex(withId(R.id.add_to_cart_image), 1),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.nav_order), withContentDescription("Winkelmandje"),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())


        val appCompatImageButton = onView(
            allOf(
                withIndex(withId(R.id.btn_plus), 0),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withIndex(withId(R.id.btn_plus), 0),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val appCompatImageView3 = onView(
            allOf(
                withIndex(withId(R.id.cart_remove_order_item), 1),
                isDisplayed()
            )
        )
        appCompatImageView3.perform(click())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.complete_order_button),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())
    }

    private fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var currentIndex = 0

            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}

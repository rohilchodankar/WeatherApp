package com.kuliza.weatherapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.kuliza.weatherapp.ui.home.HomeActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class WeatherAppUITest {

  @Rule  @JvmField
  val mActivityRule = ActivityTestRule(HomeActivity::class.java)

  @Before
  fun setUp() {
    mActivityRule.activity.viewModel.viewType.postValue(2)
  }

  @Test
  fun retryButtonClick() {
    onView(withId(R.id.btn_retry)).perform(click())
  }


}

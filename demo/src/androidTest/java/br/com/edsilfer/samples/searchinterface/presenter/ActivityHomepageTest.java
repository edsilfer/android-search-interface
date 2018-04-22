package sinterface.demo.presenter;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.edsilfer.android.sinterface.demo.R;
import br.com.edsilfer.samples.searchinterface.presenter.ActivityHomepage;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityHomepageTest {

    @Rule
    public ActivityTestRule<ActivityHomepage> mActivityTestRule = new ActivityTestRule<>(ActivityHomepage.class);

    @Test
    public void activityHomepageTest() {
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.btn_sample_03), withText("SAMPLE 03"), isDisplayed()));
        appCompatButton.perform(click());

        onView(withId(R.id.input)).perform(typeText("d"));

        ViewInteraction recyclerView = onView(allOf(withId(R.id.list), withParent(allOf(withId(R.id.replaceable), withParent(withId(R.id.container)))), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.input)).perform(typeText("c"));

        recyclerView.perform(actionOnItemAtPosition(0, click()));

       /* ViewInteraction chipEditText4 = onView(allOf(withId(R.id.input), withText("d c b "), isDisplayed()));
        chipEditText4.perform(click());*/

        ViewInteraction appCompatImageButton = onView(allOf(withId(R.id.close), withParent(withId(R.id.wrapper)), isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withId(R.id.input)).perform(typeText("a"));
    }

}

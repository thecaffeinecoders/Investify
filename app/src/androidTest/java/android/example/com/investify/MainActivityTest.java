package android.example.com.investify;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import static android.support.test.InstrumentationRegistry.getContext;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    /*public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);*/

    private MainActivity mainActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(SecondActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void testLunch()
    {
        View view = mainActivity.findViewById(R.id.tvAppName);
        assertNotNull(view);
        View view2 = mainActivity.findViewById(R.id.teAmountEnteredToInvest);
        assertNotNull(view2);
        View view3 = mainActivity.findViewById(R.id.tvAskAmountToInvest);
        assertNotNull(view3);
    }

    @Test
    public void moveNext() {
        View view1 = mainActivity.findViewById(R.id.btnConfirmAmountEnteredToInvest);
        assertNotNull(view1);
        onView(withId(R.id.btnConfirmAmountEnteredToInvest)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,7000);
        assertNotNull(secondActivity);
        secondActivity.finish();


            /*intended(allOf(
                    hasAction(Intent.ACTION_SEND),
                    hasData("Principal:25000"),
                    toPackage("android.example.com.investify")));*/

        }



    @Test
    public void readPrincipal() {
    }

    @Test
    public void getPrincipal() {
    }

    @Test
    public void revenue() {
    }
}
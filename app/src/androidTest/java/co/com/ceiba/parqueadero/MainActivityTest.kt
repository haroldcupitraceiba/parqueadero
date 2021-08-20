package co.com.ceiba.parqueadero

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.allOf
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun car_saveAndDeleteCarFromDatabase_successful(){
        onView(
            withId(R.id.entryOptionButton)
        ).perform(click())

        onView(
            allOf(
                withId(R.id.radioCarEntry)
            )
        ).perform(click())

        val licensePlateTextInput = onView(
            withId(R.id.entryLicensePlate)
        ).perform(click())

        licensePlateTextInput.perform(typeText("BBC123"))

        onView(
            withId(R.id.entryButton)
        ).perform(click())

        val textView = onView(
            allOf(
                withId(R.id.infoMessage),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(withText("Vehículo registrado.")))

        onView(
            withId(R.id.entryOptionButton)
        ).perform(click())

        onView(
            allOf(
                withId(R.id.radioCarEntry)
            )
        ).perform(click())

        licensePlateTextInput.perform(typeText("BBC123"))

        onView(
            withId(R.id.entryButton)
        ).perform(click())

        textView.check(ViewAssertions.matches(withText("El vehículo ya se encuentra registrado.")))

        onView(
            withId(R.id.exitOptionButton)
        ).perform(click())

        onView(
            allOf(
                withId(R.id.radioCarExit)
            )
        ).perform(click())

        onView(
            withId(R.id.exitLicensePlate)
        ).perform(typeText("BBC123"))

        onView(
            ViewMatchers.withId(R.id.exitLicensePlate)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(
            withId(R.id.searchButton)
        ).perform(click())

        onView(
            withId(R.id.exitButton)
        ).perform(click())

        textView.check(ViewAssertions.matches(withText("Vehículo eliminado.")))
    }

    @Test
    fun car_saveCarEmptyLicensePlateErrorMessage_exception(){
        onView(
            withId(R.id.entryOptionButton)
        ).perform(click())

        onView(
            allOf(
                withId(R.id.radioCarEntry)
            )
        ).perform(click())

        onView(
            withId(R.id.entryButton)
        ).perform(click())

        val textView = onView(
            allOf(
                withId(R.id.infoMessage),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(withText("Placa del vehículo no valida.")))
    }

    @Test
    fun car_exitCarWithEmptyLicencePlate_exception(){
        onView(
            withId(R.id.exitOptionButton)
        ).perform(click())

        onView(
            allOf(
                withId(R.id.radioCarExit)
            )
        ).perform(click())

        onView(
            withId(R.id.searchButton)
        ).perform(click())


        val textView = onView(
            allOf(
                withId(R.id.infoMessage),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(withText("Placa del vehículo no valida.")))
    }

    @Test
    fun car_carNotFoundToDelete_successful(){
        onView(
            withId(R.id.exitOptionButton)
        ).perform(click())

        onView(
            allOf(
                withId(R.id.radioCarExit)
            )
        ).perform(click())

        val licensePlateTextInput = onView(
            withId(R.id.exitLicensePlate)
        ).perform(click())

        licensePlateTextInput.perform(typeText("UJN852"))

        onView(
            withId(R.id.searchButton)
        ).perform(click())

        val textView = onView(
            allOf(
                withId(R.id.infoMessage),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(withText("Vehículo no encontrado.")))
    }

}
package co.com.ceiba.parqueadero

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityMotorcycleTest {
    @Rule
    @JvmField
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun motorcycle_saveAndDeleteMotorcycleFromDatabase_successful(){
        onView(
            withId(R.id.entryOptionButton)
        ).perform(ViewActions.click())

        onView(
            Matchers.allOf(
                withId(R.id.radioMotorcycleEntry)
            )
        ).perform(ViewActions.click())

        val licensePlateTextInput = onView(
            withId(R.id.entryLicensePlate)
        ).perform(typeText("HAC96H"))

        onView(
            withId(R.id.cylinderCapacityEntry)
        ).perform(typeText("100"))

        onView(
            withId(R.id.entryButton)
        ).perform(ViewActions.click())

        val textView = onView(
            Matchers.allOf(
                withId(R.id.infoMessage),
                ViewMatchers.isDisplayed()
            )
        )

        textView.check(ViewAssertions.matches(ViewMatchers.withText("Vehículo registrado.")))


        Espresso.onView(
            ViewMatchers.withId(R.id.exitOptionButton)
        ).perform(ViewActions.click())

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.radioMotorcycleExit)
            )
        ).perform(ViewActions.click())

        onView(
            ViewMatchers.withId(R.id.exitLicensePlate)
        ).perform(typeText("HAC96H"))

        Espresso.onView(
            ViewMatchers.withId(R.id.searchButton)
        ).perform(ViewActions.click())

        Espresso.onView(
            ViewMatchers.withId(R.id.exitButton)
        ).perform(ViewActions.click())

        textView.check(ViewAssertions.matches(ViewMatchers.withText("Vehículo eliminado.")))
    }

    @Test
    fun motorcycle_saveMotorcycleEmptyLicensePlateErrorMessage_exception(){
        Espresso.onView(
            ViewMatchers.withId(R.id.entryOptionButton)
        ).perform(ViewActions.click())

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.radioMotorcycleEntry)
            )
        ).perform(ViewActions.click())

        Espresso.onView(
            ViewMatchers.withId(R.id.entryButton)
        ).perform(ViewActions.click())

        val textView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.infoMessage),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Placa del vehículo no valida.")))
    }

    @Test
    fun motorcycle_saveMotorcycleEmptyCylinderCapacityZero_messageException(){
        Espresso.onView(
            ViewMatchers.withId(R.id.entryOptionButton)
        ).perform(ViewActions.click())

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.radioMotorcycleEntry)
            )
        ).perform(ViewActions.click())

        onView(
            withId(R.id.entryLicensePlate)
        ).perform(typeText("HAC96H"))

        onView(
            withId(R.id.cylinderCapacityEntry)
        ).perform(typeText("0"))

        Espresso.onView(
            ViewMatchers.withId(R.id.entryButton)
        ).perform(ViewActions.click())

        val textView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.infoMessage),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("El cilindraje debe ser mayor a 0.")))
    }

    @Test
    fun motorcycle_exitMotorcycleWithEmptyLicencePlate_exception(){
        Espresso.onView(
            ViewMatchers.withId(R.id.exitOptionButton)
        ).perform(ViewActions.click())

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.radioMotorcycleExit)
            )
        ).perform(ViewActions.click())

        Espresso.onView(
            ViewMatchers.withId(R.id.searchButton)
        ).perform(ViewActions.click())


        val textView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.infoMessage),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Placa del vehículo no valida.")))
    }

    @Test
    fun motorcycle_motorcycleNotFoundToDelete_successful(){
        Espresso.onView(
            ViewMatchers.withId(R.id.exitOptionButton)
        ).perform(ViewActions.click())

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.radioMotorcycleExit)
            )
        ).perform(ViewActions.click())

        val licensePlateTextInput = Espresso.onView(
            ViewMatchers.withId(R.id.exitLicensePlate)
        ).perform(ViewActions.click())

        licensePlateTextInput.perform(ViewActions.typeText("UJN85A"))

        Espresso.onView(
            ViewMatchers.withId(R.id.searchButton)
        ).perform(ViewActions.click())

        val textView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.infoMessage),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Vehículo no encontrado.")))
    }
}
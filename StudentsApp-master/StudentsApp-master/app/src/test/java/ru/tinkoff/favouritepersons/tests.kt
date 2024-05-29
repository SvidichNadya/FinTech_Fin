package ru.tinkoff.favouritepersons

import org.junit.Test
import org.junit.Assert.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class FavouritePersonsTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    private val wireMockServer = WireMockServer(8080)

    @Before
    fun setUp() {
        wireMockServer.start()
        configureFor("localhost", 8080)
        stubFor(get(urlEqualTo("StudentsApp-master/app/src/test/java/ru/tinkoff/favouritepersons/person.json"))
            .willReturn(aResponse()
                .withStatus(HttpURLConnection.HTTP_OK)
                .withHeader("Content-Type", "application/json")
                .withBodyFile("person.json")))
    }

    @After
    fun tearDown() {
        wireMockServer.stop()
    }

    @Test
    fun checkSortingByName() {
        onView(withId(R.id.action_item_sort)).perform(click())
        onView(withId(R.id.bsd_rb_name)).perform(click())
        onView(withId(R.id.rv_person_list)).check(matches(isDisplayed()))
    }

    @Test
    fun checkSortingByAge() {
        onView(withId(R.id.action_item_sort)).perform(click())
        onView(withId(R.id.bsd_rb_age)).perform(click())
        onView(withId(R.id.rv_person_list)).check(matches(isDisplayed()))
    }

    @Test
    fun checkSortingByScore() {
        onView(withId(R.id.action_item_sort)).perform(click())
        onView(withId(R.id.bsd_rb_rating)).perform(click())
        onView(withId(R.id.rv_person_list)).check(matches(isDisplayed()))
    }

    @Test
    fun checkDeletion() {
        onView(withId(R.id.rv_person_list)).perform(actionOnItemAtPosition<recyclerviewadapter.personviewholder>(0, swipeLeft()))
    }

    @Test
    fun checkOpenAddPersonForm() {
        onView(withId(R.id.fab_add_person)).perform(click())
        onView(withId(R.id.bottom_sheet)).check(matches(isDisplayed()))
    }

    @Test
    fun checkAddPerson() {
        onView(withId(R.id.fab_add_person)).perform(click())
        onView(withId(R.id.et_name)).perform(typeText("Иван"))
        onView(withId(R.id.et_surname)).perform(typeText("Иванов"))
        onView(withId(R.id.et_email)).perform(typeText("ivan@ivan.ru"))
        onView(withId(R.id.et_phone)).perform(typeText("89999999999"))
        onView(withId(R.id.et_address)).perform(typeText("г. Иваново"))
        onView(withId(R.id.et_birthdate)).perform(typeText("1990-10-10"))
        onView(withId(R.id.et_image)).perform(typeText("https://example.com/image.jpg"))
        onView(withId(R.id.et_score)).perform(typeText("100"))
        onView(withId(R.id.submit_button)).perform(click())
        onView(withId(R.id.rv_person_list)).check(matches(isDisplayed()))
    }

    @Test
    fun checkIncorrectAddPerson() {
        onView(withId(R.id.fab_add_person)).perform(click())
        onView(withId(R.id.et_name)).perform(typeText("Иван"))
        onView(withId(R.id.et_surname)).perform(typeText("Иванов"))
        onView(withId(R.id.et_email)).perform(typeText("ivan@ivan.ru"))
        onView(withId(R.id.et_phone)).perform(typeText("89999999999"))
        onView(withId(R.id.et_address)).perform(typeText("г. Иваново"))
        onView(withId(R.id.et_birthdate)).perform(typeText("        "))
        onView(withId(R.id.et_image)).perform(typeText("https://example.com/image.jpg"))
        onView(withId(R.id.et_score)).perform(typeText("100"))
        onView(withId(R.id.submit_button)).perform(click())
        onView(withId(R.id.bottom_sheet)).check(matches(isDisplayed()))
    }

    @Test
    fun checkOpenPersonDetailsScreen() {
        onView(withId(R.id.rv_person_list)).perform(actionOnItemAtPosition<recyclerviewadapter.personviewholder>(0, click()))
        onView(withId(R.id.person_info_constraint)).check(matches(isDisplayed()))
    }

    @Test
    fun checkEditPerson() {
        onView(withId(R.id.rv_person_list)).perform(actionOnItemAtPosition<recyclerviewadapter.personviewholder>(0, click()))
        onView(withId(R.id.et_name)).perform(clearText(), typeText("Иванка"))
        onView(withId(R.id.submit_button)).perform(click())
        onView(withId(R.id.person_info_constraint)).check(matches(isDisplayed()))
    }
}
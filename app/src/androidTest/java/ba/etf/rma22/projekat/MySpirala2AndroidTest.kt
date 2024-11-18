package ba.etf.rma22.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {
    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    //Da li se ispiše poruka nakon upisa u neku grupu
    @Test
    fun testZadatak1(){
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))
        onView(withId(R.id.odabirGodina)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("2"))).perform(click())
        onView(withId(R.id.odabirIstrazivanja)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Istraživanje 2"))).perform(click())
        onView(withId(R.id.odabirGrupa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Grupa 3"))).perform(click())
        onView(withId(R.id.dodajIstrazivanjeDugme)).perform(click())
        onView(allOf(isDisplayed(), withId(R.id.tvPoruka))).check(matches(withText("Uspješno ste upisani u grupu Grupa 3 istraživanja Istraživanje 2!")))
    }


    //Da li se uspešno vrati na početak (FragmentAnkete) nakon klika na dugmeZaustavi
    @Test
    fun testZadatak2(){
        /*onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).perform(click())
        Espresso.onData(CoreMatchers.allOf(CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)), CoreMatchers.`is`("Sve moje ankete"))).perform(click())
        val ankete = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(CoreMatchers.allOf(hasDescendant(withText(ankete[0].naziv)),
            hasDescendant(withText(ankete[0].nazivIstrazivanja))), click()))
        onData(anything())
            .inAdapterView(allOf(withId(R.id.odgovoriLista), isCompletelyDisplayed()))
            .atPosition(0).perform(click())
        onView(allOf(withId(R.id.dugmeZaustavi),isDisplayed())).perform(click())
        onView(withSubstring("Sve moje ankete")).check(matches(isDisplayed()))*/
    }
}
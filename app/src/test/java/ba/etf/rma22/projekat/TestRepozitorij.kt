package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanja
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjaRepository
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.beans.HasPropertyWithValue.hasProperty
import org.junit.Test
import java.util.*
import org.hamcrest.CoreMatchers.`is` as Is


class TestRepozitorij {

    /*getFuture*/
    @Test
    fun testMetodeZaBuduceAnkete() {
        val ankete = AnketaRepository.getFuture()
        assertEquals(ankete.size, 2)
        assertThat(ankete, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 4"))))
        assertThat(ankete, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 7"))))
    }
    /*getDone*/
    @Test
    fun testMetodeZaAnketeKojeTrebajuBitiUradjene() {
        val umfrage = AnketaRepository.getDone()
        assertEquals(umfrage.size, 3)
        assertThat(umfrage, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 1"))))
        assertThat(umfrage, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 3"))))
        assertThat(umfrage, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 9"))))
    }
    /*getNotTaken*/
    @Test
    fun testMetodaNeuradjenihAnketa() {
        val umfrage = AnketaRepository.getNotTaken()
        assertEquals(umfrage.size, 2)
        assertThat(umfrage, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 5"))))
        assertThat(umfrage, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 10"))))
    }
    /*getAll*/
    @Test
    fun testMetodeZaVracanjeSvihAnketa() {
        val umfrage = AnketaRepository.getAll()
        assertEquals(umfrage.size, 10)
        assertThat(umfrage, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 7"))))
    }
    /*Ovaj test testira metodu vracanja svih istrazivanja za datu godinu :*/
    /*getIstrazivanjaByGodina*/
    @Test
    fun testMetodeIstrazivanjePoGodini() {
        val umfrageProJahr = IstrazivanjaRepository.getIstrazivanjaByGodina(5)
        assertEquals(umfrageProJahr.size, 2)
        assertThat(umfrageProJahr, hasItem<Istrazivanja>(hasProperty("naziv", Is("Istraživanje 9"))))
        assertThat(umfrageProJahr, hasItem<Istrazivanja>(hasProperty("naziv", Is("Istraživanje 10"))))
    }
    /*myAnkete*/
    @Test
    fun testMetodeVratiMojeAnkete() {
        val umfrage = AnketaRepository.getMyAnkete()
        assertEquals(umfrage.size, 5)
        assertThat(umfrage, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 1"))))
        assertThat(umfrage, hasItem<Anketa>(hasProperty("nazivGrupe", Is("Grupa2"))))
    }

    /*Ovaj test testira metodu vracnja grupa po istrazivanju :*/
    /*getGruoupsByIstrazivanja*/
    @Test
    fun testMetodeGrupePoIstrazivanju() {
        val gruppen = GrupaRepository.getGroupsByIstrazivanja("Istraživanje 2")
        assertEquals(gruppen.size, 2)
        assertThat(gruppen, hasItem<Grupa>(hasProperty("naziv", Is("A1"))))
        assertThat(gruppen, hasItem<Grupa>(hasProperty("naziv", Is("B1"))))
    }

    /*meineHinzufugen*/
    @Test
    fun testMetodeDodajNovuAnketu() {
        val ankete = AnketaRepository.getMyAnkete()
        assertEquals(ankete.size, 5)
        val istrazivanje = Anketa(
            "Istraživanje 11", "Anketa 11", Date(2022, 6, 21),
            Date(2022, 6, 30), Date(0, 0, 0),
            6, "IS-11", 0.0f
        )
        AnketaRepository.meineHinzufugen(istrazivanje)
        assertEquals(ankete.size, 6)
        assertThat(ankete, hasItem<Anketa>(hasProperty("naziv", Is("Istraživanje 11"))))
    }

    /*getUpisani & Einschreibungsforschung*/
    @Test
    fun testMetoda() {
        val ovoMiJeUpisanoIstrazivanje = IstrazivanjaRepository.getUpisani()
        assertEquals(ovoMiJeUpisanoIstrazivanje.size, 4)
        val ovoIstrazivanjeUpisujem = Istrazivanja("Istraživanje 8", 4)
        IstrazivanjaRepository.Einschreibungsforschung(ovoIstrazivanjeUpisujem)
        assertEquals(ovoMiJeUpisanoIstrazivanje.size, 5)
        assertThat(ovoMiJeUpisanoIstrazivanje, hasItem<Istrazivanja>(hasProperty("naziv", Is("Istraživanje 8"))))
        assertThat(ovoMiJeUpisanoIstrazivanje, hasItem<Istrazivanja>(hasProperty("naziv", Is("Istraživanje 3"))))
    }
    /*pokrivenost testova 83% */
}
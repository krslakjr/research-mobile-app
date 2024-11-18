package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.all
import ba.etf.rma22.projekat.data.istrazivanjaByGodina
import ba.etf.rma22.projekat.data.models.Istrazivanja
import ba.etf.rma22.projekat.data.eingesungen
import ba.etf.rma22.projekat.data.registrieren

class IstrazivanjaRepository {
    companion object {
        fun getUpisani(): List<Istrazivanja> {
            return eingesungen()
        }

        fun getAll(): List<Istrazivanja> {
            return all()
        }
        fun getIstrazivanjaByGodina(godina : Int) : List<Istrazivanja> {
            return istrazivanjaByGodina(godina)
        }
        //Koristim za upisivanje istrazivanja :
        fun Einschreibungsforschung(istrazivanja : Istrazivanja) {
            registrieren(istrazivanja)
        }
    }

}
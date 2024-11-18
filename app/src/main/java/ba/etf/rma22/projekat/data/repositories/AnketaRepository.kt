package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.*
import ba.etf.rma22.projekat.data.models.Anketa

class AnketaRepository {

    //potrebno implementirati:
    companion object {
        init {
        }

        fun getMyAnkete(): List<Anketa> {
            return myAnkete()
        }

        fun getAll(): List<Anketa> {
            return allAnkete()
        }

        fun getDone(): List<Anketa> {
            return done()
        }

        fun getFuture(): List<Anketa> {
            return future()
        }

        fun getNotTaken(): List<Anketa> {
            return notTaken()
        }
        //Koristim da bih dodala anketu u moje ankete
        fun meineHinzufugen(anketa : Anketa) {
            fugeMeineHinzu(anketa)
        }
    }
}
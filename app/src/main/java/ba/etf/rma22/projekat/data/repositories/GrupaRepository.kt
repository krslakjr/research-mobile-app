package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.groupsByIstrazivanja
import ba.etf.rma22.projekat.data.models.Grupa

class GrupaRepository {
    //potrebno implementirati :
    companion object {
        init {
        }

        fun getGroupsByIstrazivanja(nazivIstrazivanja: String): List<Grupa> {
            return groupsByIstrazivanja(nazivIstrazivanja)
        }
    }
}
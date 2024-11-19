package ba.etf.rma22.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.RMA22DB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")

object PitanjeAnketaRepository {
    private lateinit var context: Context

    suspend fun getPitanja(idAnkete:Int): List<Pitanje>{
        return withContext(Dispatchers.IO) {
            val odgovor = ApiConfig.retrofit.getPitanja(idAnkete)
            val tijeloOdgovora = odgovor.body()
            val baza = RMA22DB.getInstance(context)
            baza.pitanjeDao().izbrisiSve()
            baza.pitanjeDao().insertAll(tijeloOdgovora!!)
            return@withContext tijeloOdgovora
        }
    }

    fun setContext(kontekst: Context) {
        context = kontekst
    }

}
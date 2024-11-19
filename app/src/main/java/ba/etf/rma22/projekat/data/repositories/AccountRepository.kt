package ba.etf.rma22.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma22.projekat.data.models.RMA22DB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")

object AccountRepository {


    fun getHash():String {
        return acHash
    }

    private lateinit var context: Context
    fun setContext(kontekst: Context) {
        context = kontekst
    }

    var acHash : String = "0ef00c09-4194-40cd-b324-04fae89977ab"

    suspend fun postaviHash(hashLocal: String): Boolean {
        return withContext(Dispatchers.IO) {
            var baza = RMA22DB.getInstance(context)
            var bazniHash = baza.accountDao().getHash()
            if(hashLocal != bazniHash) {
                if(bazniHash!=null) {
                    ApiConfig.retrofit.obrisiSveZaKorisnika(bazniHash)
                }

                baza.anketaTakenDao().izbrisiSve()
                baza.pitanjeDao().izbrisiSve()
                baza.accountDao().izbrisiSve()
                baza.grupaDao().izbrisiSve()
                baza.anketaDao().izbrisiSve()
                baza.odgovorDao().izbrisiSve()
                baza.istrazivanjeDao().izbrisiSve()


                baza.accountDao().upisi(hashLocal)
                acHash = hashLocal
            }
            return@withContext true
        }
    }

}
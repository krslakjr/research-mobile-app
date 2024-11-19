package ba.etf.rma22.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.RMA22DB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")

object IstrazivanjeIGrupaRepository {


    suspend fun getIstrazivanjeZaId(idIstrazivanja: Int) : Istrazivanje{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getIstrazivanjeZaId(idIstrazivanja)
            val antwortBody = antwort.body()
            return@withContext antwortBody!!
        }
    }


    suspend fun getIstrazivanja(offset:Int):List<Istrazivanje>{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getIstrazivanja(offset)
            val antworteBody = antwort.body()
            val baza = RMA22DB.getInstance(context)
            baza.istrazivanjeDao().izbrisiSve()
            baza.istrazivanjeDao().insertAll(antworteBody!!)
            return@withContext antworteBody
        }
    }

    suspend fun getIstrazivanjaZaGrupu(idGrupa:Int) : List<Istrazivanje>{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getIstrazivanjaZaGrupu(idGrupa)
            val antwortBody = antwort.body()
            return@withContext antwortBody!!
        }
    }

    suspend fun getIstrazivanja():List<Istrazivanje>{
        return withContext(Dispatchers.IO) {
            var counter = 1
            var listen = mutableListOf<Istrazivanje>()
            while (true){
                var antwort = ApiConfig.retrofit.getIstrazivanja(counter)
                listen.addAll(antwort.body()!!)

                if(antwort.body()!!.size<5) break

                counter++
            }
            val baza = RMA22DB.getInstance(context)
            baza.istrazivanjeDao().izbrisiSve()
            baza.istrazivanjeDao().insertAll(listen)
            return@withContext listen
        }
    }
    suspend fun getUpisaneGrupe():List<Grupa>{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getUpisaneGrupe(AccountRepository.getHash())
            val antwortBody = antwort.body()
            if(antwortBody==null)  return@withContext emptyList()

            return@withContext antwortBody
        }
    }
    suspend fun getGrupe():List<Grupa>{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getGrupe()
            val antwortBody = antwort.body()
            val baza = RMA22DB.getInstance(context)
            baza.grupaDao().izbrisiSve()
            baza.grupaDao().insertAll(antwortBody!!)
            return@withContext antwortBody!!
        }
    }
    suspend fun getGrupeZaIstrazivanje(idIstrazivanja:Int) : List<Grupa>{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getGrupe()
            val antwortBody = antwort.body()
            return@withContext antwortBody!!.filter { gruppe -> gruppe.istrazivanjeId==idIstrazivanja }
        }
    }
    suspend fun upisiUGrupu(idGrupa:Int):Boolean{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.upisiUGrupu(idGrupa, AccountRepository.getHash())
            val antwortBody = antwort.body()
            if(antwortBody!!.message.contains("not found", ignoreCase = true)) return@withContext false

            return@withContext true
        }
    }


    suspend fun getGrupeZaAnketu(idAnketa : Int) : List<Grupa>{
        return withContext(Dispatchers.IO) {
            var antwoer = ApiConfig.retrofit.getGrupeZaAnketu(idAnketa)
            val antwortBody = antwoer.body()
            return@withContext antwortBody!!
        }
    }

    private lateinit var context: Context
    fun setContext(kontekst: Context) {
        context = kontekst
    }

}
package ba.etf.rma22.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma22.projekat.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")

object AnketaRepository {

    suspend fun getSveAnkete() : List<MojaAnketa>{
        return withContext(Dispatchers.IO){
            var einigeList = mutableListOf<Anketa>()
            var counter = 1
            while(true){
                var antwort = ApiConfig.retrofit.getAll(counter)
                einigeList.addAll(antwort.body()!!)
                if(antwort.body()!!.size < 5)  break

                counter++
            }
            var zweiteList = mutableListOf<MojaAnketa>()
            for(umfragliste in einigeList){
                var antwort = ApiConfig.retrofit.getGrupeZaAnketu(umfragliste.id)
                for(gruppe in antwort.body()!!){
                    zweiteList.add(MojaAnketa(umfragliste.id,gruppe.id, gruppe.istrazivanjeId, false))
                }
            }
            return@withContext zweiteList
        }
    }


    suspend fun getById(id : Int) : Anketa {
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getById(id)
            val hild = antwort.body()
            return@withContext hild!!
        }
    }

    suspend fun getAll(offset : Int) : List<Anketa>{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getAll(offset)
            val antworten = antwort.body()
            return@withContext antworten!!
        }
    }

    suspend fun getAll() : List<Anketa>{
        return withContext(Dispatchers.IO) {
            var einigeliste = mutableListOf<Anketa>()
            var counter = 1
            while(true){
                var antwort = ApiConfig.retrofit.getAll(counter)
                einigeliste.addAll(antwort.body()!!)
                if(antwort.body()!!.size < 5){
                    break
                }
                counter++
            }
            val baza = RMA22DB.getInstance(context)
            baza.anketaDao().izbrisiSve()
            baza.anketaDao().insertAll(einigeliste)
            return@withContext einigeliste
        }
    }


    suspend fun getUpisane() : List<Anketa> {
        return withContext(Dispatchers.IO) {
            val eingeList = mutableListOf<Anketa>()
            var antwort = ApiConfig.retrofit.getGrupeStudenta(AccountRepository.getHash())
            val hilf = antwort.body()
            for(gruppe in hilf!!){
                var antworten = ApiConfig.retrofit.getUpisane(gruppe.id)
                val hilfe  = antworten.body()!!
                eingeList.addAll(hilfe)
            }
            return@withContext eingeList
        }
    }

    suspend fun getUpisaneDB() : List<Anketa> {
        return withContext(Dispatchers.IO){
            val baza = RMA22DB.getInstance(context)
            val umfragliste = baza.anketaDao().getAll()
            return@withContext umfragliste
        }
    }

    private lateinit var context: Context
    fun setContext(cont: Context) {
        context = cont
    }
}
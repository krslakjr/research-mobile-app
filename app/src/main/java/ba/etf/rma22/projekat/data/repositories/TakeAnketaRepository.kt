package ba.etf.rma22.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")

object TakeAnketaRepository {

    suspend fun getPoceteAnkete() : List<AnketaTaken>?{
        return withContext(Dispatchers.IO) {
            var odgovor = ApiConfig.retrofit.getPoceteAnkete(AccountRepository.getHash())
            val tijeloOdgovora = odgovor.body()
            if(tijeloOdgovora!!.isEmpty()){
                return@withContext null
            }
            return@withContext tijeloOdgovora
        }
    }

    private lateinit var context: Context
    fun setContext(kontekst: Context) {
        context = kontekst
    }

    suspend fun zapocniAnketu(anketaId : Int) : AnketaTaken?{
        return withContext(Dispatchers.IO) {
            val odgovor1 = ApiConfig.retrofit.getPoceteAnkete(AccountRepository.getHash())
            val tijeloOdgovora1 = odgovor1.body()
            for(anketaTaken in tijeloOdgovora1!!){
                if(anketaTaken.AnketumId==anketaId){
                    return@withContext null
                }
            }
            var odgovorMain = ApiConfig.retrofit.zapocniAnketu(AccountRepository.getHash(),anketaId)
            val tijeloOdgovoraMain = odgovorMain.body()
            return@withContext tijeloOdgovoraMain!!
        }
    }

}
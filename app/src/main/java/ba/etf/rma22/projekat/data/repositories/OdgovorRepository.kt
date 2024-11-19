package ba.etf.rma22.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.OdgovorPitanjeBodovi
import ba.etf.rma22.projekat.data.models.RMA22DB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt
import kotlin.random.Random

@SuppressLint("StaticFieldLeak")

object OdgovorRepository {




    suspend fun getOdgovoriAnketa(anketaId : Int) : List<Odgovor>{
        return withContext(Dispatchers.IO) {
            var antwort = ApiConfig.retrofit.getPoceteAnkete(AccountRepository.getHash())
            val antworten = antwort.body()
            var form = -1
            for(anketaTaken in antworten!!){
                if(anketaTaken.AnketumId==anketaId)  form = anketaTaken.id

            }
            var richtigeantwort = ApiConfig.retrofit.getOdgovoriAnketa(AccountRepository.getHash(),form)
            val richtigeantworten = richtigeantwort.body()
            return@withContext richtigeantworten!!
        }
    }


    suspend fun postaviOdgovorAnketa(idAnketaTaken:Int,idPitanje:Int,odgovor:Int) : Int{
        return withContext(Dispatchers.IO) {
            var AnketaId : Int = -1
            val hilf = ApiConfig.retrofit.getPoceteAnkete(AccountRepository.getHash())
            val anderehilf = hilf.body()
            for(anketaTaken in anderehilf!!){
                if(anketaTaken.id==idAnketaTaken){
                    AnketaId=anketaTaken.AnketumId
                    break
                }
            }
            val zweiteHilf = ApiConfig.retrofit.getPitanja(AnketaId)
            val zweiteanderehilf = zweiteHilf.body()
            val frageNumm = zweiteanderehilf!!.size
            val dritteHilf = ApiConfig.retrofit.getOdgovoriAnketa(AccountRepository.getHash(),idAnketaTaken)
            val dritteanderehilf = dritteHilf.body()
            val antwortNumm = dritteanderehilf!!.size + 1
            val besteHilf: Float = antwortNumm.toFloat() / frageNumm.toFloat()
            val progress = prozenBestimmen((besteHilf * 100).roundToInt())
            val antwortFrageNum = OdgovorPitanjeBodovi(odgovor, idPitanje, progress)
            val response = ApiConfig.retrofit.postaviOdgovorAnketa(AccountRepository.getHash(),idAnketaTaken,antwortFrageNum)
            val responseBody = response.body()

            if(responseBody==null)  return@withContext -1

            val baza = RMA22DB.getInstance(context)
            var trust = true
            for(antwort in baza.odgovorDao().getAll()){
                if(antwort.pitanjeId==idPitanje)  trust = false
            }
            if(trust) {
                val antwort = Odgovor(Random.nextInt(), odgovor, idAnketaTaken, idPitanje)
                baza.odgovorDao().insertAll(listOf(antwort))
            }
            baza.anketaTakenDao().setProgress(progress, idAnketaTaken)
            return@withContext antwortFrageNum.progres
        }
    }

    private fun prozenBestimmen(x : Int) : Int {
        var progres = x
        if(progres>=90 && progres<=100)  progres = 100
        else if(progres>=70 && progres<=89)  progres = 80
        else if(progres>=50 && progres<=69)  progres=60
        else if(progres>=30 && progres<=49)  progres=40
        else if(progres>=10 && progres<=29)  progres=20
        else if(progres >= 0 && progres<=9) progres = 0

        return progres
    }

    private lateinit var context: Context
    fun setContext(kontekst: Context) {
        context = kontekst
    }
}
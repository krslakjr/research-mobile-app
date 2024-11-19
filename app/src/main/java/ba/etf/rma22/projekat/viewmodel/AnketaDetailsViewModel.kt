package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AnketaDetailsViewModel {

    var grupeZaAnketu = MutableLiveData<List<Grupa>>()
    var istrazivanjeZaId = MutableLiveData<Istrazivanje>()
    var istrazivanja = MutableLiveData<List<Istrazivanje>>()
    var grupeZaIstrazivanje = MutableLiveData<List<Grupa>>()
    var grupeStudenta = MutableLiveData<List<Grupa>>()
    var pitanja = MutableLiveData<List<Pitanje>>()
    var anketaTkn = MutableLiveData<AnketaTaken>()
    var anketeTaken = MutableLiveData<List<AnketaTaken>>()
    var odgovori = MutableLiveData<List<Odgovor>>()
    var anketeTakenAdapter = MutableLiveData<List<AnketaTaken>>()
    var odgovoriZaPitanje = MutableLiveData<List<Odgovor>>()
    var upisaneAnkete = MutableLiveData<List<Anketa>>()
    var prog = 0
    private val scope = MainScope()

    fun getPitanja(idAnkete : Int,onSuccess: (brojPitanja : Int) -> Unit,
                    onError: () -> Unit){
        scope.launch{
            val result = PitanjeAnketaRepository.getPitanja(idAnkete)
            when(result){
                is List<Pitanje> -> onSuccess.invoke(result.size)
                else -> onError.invoke()
            }
        }
    }

    fun zapocniAnketu(idAnkete : Int,onSuccess: (ankTkn : AnketaTaken) -> Unit,
                   onError: () -> Unit){
        scope.launch{
            val result = TakeAnketaRepository.zapocniAnketu(idAnkete)
            when(result){
                is AnketaTaken -> {
                    onSuccess.invoke(result)
                    anketaTkn.postValue(result!!)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getPoceteAnkete(onSuccess: () -> Unit,
                   onError: () -> Unit){
        scope.launch{
            val result = TakeAnketaRepository.getPoceteAnkete()
            when(result){
                is List<AnketaTaken> -> {
                    onSuccess.invoke()
                    anketeTaken.postValue(result!!)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getOdgovoriAnketa(idAnkete: Int,onSuccess: () -> Unit,
                        onError: () -> Unit){
        scope.launch{
            val result = OdgovorRepository.getOdgovoriAnketa(idAnkete)
            when(result){
                is List<Odgovor> -> {
                    onSuccess.invoke()
                    odgovoriZaPitanje.postValue(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun postaviOdgovorAnketa(idAnketaTaken : Int,idPitanje: Int, odgovor: Int,onSuccess: (prog : Int) -> Unit,
                          onError: (prog : Int) -> Unit){
        scope.launch{
            val result = OdgovorRepository.postaviOdgovorAnketa(idAnketaTaken,idPitanje,odgovor)
            if(result!=-1){
                onSuccess.invoke(result)
                prog = result
            }
            else{
                onError.invoke(result)
                prog = -1
            }
        }
    }

    fun getAll(onSuccess: (list : List<Anketa>) -> Unit,
                          onError: () -> Unit){
        scope.launch{
            var i = 1
            var lista = mutableListOf<Anketa>()
            while(true) {
                val result = AnketaRepository.getAll(i)
                lista.addAll(result)
                if(result.size<5) {
                    break
                }
                i++
            }
            when(lista){
                is List<Anketa> -> onSuccess.invoke(lista)
                else -> onError.invoke()
            }
        }
    }

    fun getById(id: Int,onSuccess: () -> Unit,
               onError: () -> Unit){
        scope.launch{
            val result = AnketaRepository.getById(id)
            when(result){
                is Anketa -> onSuccess.invoke()
                else -> onError.invoke()
            }
        }
    }

    fun getUpisane(onSuccess: (lista: List<Anketa>) -> Unit,
               onError: () -> Unit){
        scope.launch{
            var result = AnketaRepository.getUpisane() as MutableList
            val lista = result
            when(lista){
                is List<Anketa> -> {
                    onSuccess.invoke(lista)
                    upisaneAnkete.postValue(lista)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getUpisaneDB(onSuccess: (lista: List<Anketa>) -> Unit,
                   onError: () -> Unit){
        scope.launch{
            var result = AnketaRepository.getUpisaneDB() as MutableList
            val lista = result
            when(lista){
                is List<Anketa> -> {
                    onSuccess.invoke(lista)
                    upisaneAnkete.postValue(lista)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getIstrazivanja(onSuccess: (lista : List<Istrazivanje>) -> Unit,
               onError: () -> Unit){
        scope.launch{
            var i = 1
            var lista = mutableListOf<Istrazivanje>()
            while(true) {
                val result = IstrazivanjeIGrupaRepository.getIstrazivanja(i)
                lista.addAll(result)
                if(result.size<5) {
                    break
                }
                i++
            }
            when(lista){
                is List<Istrazivanje> -> {
                    onSuccess.invoke(lista)
                    istrazivanja.postValue(lista)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getGrupe(onSuccess: () -> Unit,
                        onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getGrupe()
            when(result){
                is List<Grupa> -> onSuccess.invoke()
                else -> onError.invoke()
            }
        }
    }

    fun getGrupeZaIstrazivanje(idIstrazivanje : Int,onSuccess: (lista : List<Grupa>) -> Unit,
                 onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(idIstrazivanje)
            when(result){
                is List<Grupa> -> {
                    onSuccess.invoke(result)
                    grupeZaIstrazivanje.postValue(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun upisiUGrupu(idGrupa : Int,onSuccess: () -> Unit,
                 onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.upisiUGrupu(idGrupa)
            when(result){
                is Boolean -> onSuccess.invoke()
                else -> onError.invoke()
            }
        }
    }

    fun getUpisaneGrupe(onSuccess: () -> Unit,
                 onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            when(result){
                is List<Grupa> -> {
                    onSuccess.invoke()
                    grupeStudenta.postValue(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getPoceteAnketeAdapter(position: Int,onSuccess: (position : Int, lista : List<AnketaTaken>) -> Unit,
                        onError: () -> Unit){
        scope.launch{
            val result = TakeAnketaRepository.getPoceteAnkete()
            when(result){
                is List<AnketaTaken> -> {
                    onSuccess.invoke(position, result)
                    anketeTakenAdapter.postValue(result!!)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getPoceteAnketeFragment1(ankete : List<Anketa>,onSuccess: (ankete : List<Anketa>, lista : List<AnketaTaken>) -> Unit,
                        onError: () -> Unit){
        scope.launch{
            val result = TakeAnketaRepository.getPoceteAnkete()
            when(result){
                is List<AnketaTaken> -> onSuccess.invoke(ankete,result)
                else -> onError.invoke()
            }
        }
    }

    fun getPitanjaFragment1(idAnkete : Int,onSuccess: (lista : List<Pitanje>) -> Unit,
                   onError: () -> Unit){
        scope.launch{
            val result = PitanjeAnketaRepository.getPitanja(idAnkete)
            when(result){
                is List<Pitanje> -> {
                    onSuccess.invoke(result)
                    pitanja.postValue(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getUpisaneGrupeFragmentIst(onSuccess: (lista : List<Grupa>) -> Unit,
                        onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            when(result){
                is List<Grupa> -> onSuccess.invoke(result)
                else -> onError.invoke()
            }
        }
    }

    fun getIstrazivanjaZaGrupu(idGrupa : Int,onSuccess: (lista : List<Istrazivanje>) -> Unit,
                               onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getIstrazivanjaZaGrupu(idGrupa)
            when(result){
                is List<Istrazivanje> -> onSuccess.invoke(result)
                else -> onError.invoke()
            }
        }
    }

    fun getGrupeFragmentIst(naziv : String,onSuccess: (naziv : String, lista : List<Grupa>) -> Unit,
                            onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getGrupe()
            when(result){
                is List<Grupa> -> onSuccess.invoke(naziv,result)
                else -> onError.invoke()
            }
        }
    }

    fun getOdgovoriAnketaAdapter(idAnkete: Int, onSuccess: (odgovori: List<Odgovor>) -> Unit,
                          onError: () -> Unit){
        scope.launch{
            val result = OdgovorRepository.getOdgovoriAnketa(idAnkete)
            when(result){
                is List<Odgovor> -> {
                    onSuccess.invoke(result)
                    odgovori.postValue(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getGrupeZaAnketu(idAnkete: Int, onSuccess: (grupe: List<Grupa>) -> Unit,
                         onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getGrupeZaAnketu(idAnkete)
            when(result){
                is List<Grupa> ->  {
                    onSuccess.invoke(result)
                    grupeZaAnketu.postValue(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getIstrazivanjeZaId(idIstrazivanje: Int, onSuccess: (istr : Istrazivanje) -> Unit,
                            onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getIstrazivanjeZaId(idIstrazivanje)
            when(result){
                is Istrazivanje -> {
                    onSuccess.invoke(result)
                    istrazivanjeZaId.postValue(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun getSveAnkete(onSuccess: () -> Unit, onError: () -> Unit){
        scope.launch {
            val result = AnketaRepository.getSveAnkete()
            when(result){
                is List<MojaAnketa> -> {
                    onSuccess.invoke()
                    alleUmfragliste.addAll(result)
                }
                else -> onError.invoke()
            }
        }
    }

    fun upisi(context : Context, acHash : String, onSuccess: () -> Unit,
              onError: () -> Unit){
        scope.launch{
            AccountRepository.setContext(context)
            val result = AccountRepository.postaviHash(acHash)
            when (result) {
                true -> onSuccess.invoke()
                else-> onError.invoke()
            }
        }
    }
}
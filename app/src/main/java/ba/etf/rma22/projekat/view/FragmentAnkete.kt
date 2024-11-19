package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.CheckNetworkConnection
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.pagger
import ba.etf.rma22.projekat.viewmodel.AnketaDetailsViewModel
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class FragmentAnkete : Fragment() {

    private lateinit var umfragliste: RecyclerView
    private lateinit var spinnkoder: Spinner
    private lateinit var umfraglisteA: AnketaListAdapter
    private var umfraglisteVm = AnketaDetailsViewModel()


    private fun erfolgEin(mein : List<Anketa>){
        umfraglisteA.updateAnkete(mein.sorted())
    }
    private fun erfolgZwei(alle : List<Anketa>){
        meinUmfraglist = false
        umfraglisteA.updateAnkete(alle.sorted())
    }
    private fun erfolgDrei(mein : List<Anketa>){
        umfraglisteVm.getPoceteAnketeFragment1(mein, onSuccess = ::erfolgVier, onError = ::greska)
    }
    private fun erfolgVier(mein: List<Anketa>, start : List<AnketaTaken>){
        var liste = mutableListOf<Anketa>()
        for(umfraglist in start){
            if(umfraglist.progres==100){
                for(umfrg in mein){
                    if(umfraglist.AnketumId==umfrg.id){
                        liste.add(umfrg)
                        break
                    }
                }
            }
        }
        umfraglisteA.updateAnkete(liste.sorted())
    }
    private fun erfolgFunf(mein: List<Anketa>){
        var liste = mutableListOf<Anketa>()
        var kalender: Calendar = Calendar.getInstance()
        val datei: Date = kalender.time
        GlobalScope.launch(Dispatchers.IO){
            launch(Dispatchers.IO){
                umfraglisteVm.getPoceteAnkete(onSuccess = ::erfolgAcht, onError = ::greskaEmpty)
            }
            delay(500)
            val umfraglisteFertig = umfraglisteVm.anketeTaken.value
            for(umfraglist in mein){
                if(datei.compareTo(umfraglist.datumPocetak) <0){
                    liste.add(umfraglist)
                }
                else{
                    if(umfraglisteFertig!=null) {
                        var hilf = true
                        for (anketaTkn in umfraglisteFertig){
                            if(anketaTkn.AnketumId==umfraglist.id && anketaTkn.progres==100 || umfraglist.datumKraj!=null && datei.compareTo(umfraglist.datumKraj) > 0){
                                hilf = false
                                break
                            }
                        }
                        if(hilf) liste.add(umfraglist)
                    }
                    else liste.add(umfraglist)
                }
            }
            withContext(Dispatchers.Main) {
                umfraglisteA.updateAnkete(liste.sorted())
            }
        }
    }

    private fun erfolgSechs(frag : List<Pitanje>){}

    private fun erfolgSieben(umfraglistFertig: AnketaTaken) {}

    private fun erfolgAcht(){}

    private fun erfolgNeun(mein: List<Anketa>){
        umfraglisteVm.getPoceteAnketeFragment1(mein, onSuccess = ::erfolgZhan, onError = ::greskaEmpty)
    }

    private fun erfolgZhan(mein: List<Anketa>, start: List<AnketaTaken>){
        var liste = mutableListOf<Anketa>()
        var kalender: Calendar = Calendar.getInstance()
        val datei: Date = kalender.time
        for(umfraglist in mein){
            var ein = 0
            var zwei = 0
            for(umfraglisteFertig in start){
                if(umfraglist.id == umfraglisteFertig.AnketumId && umfraglisteFertig.progres == 0 && umfraglist.datumKraj!=null && datei.compareTo(umfraglist.datumKraj) > 0){
                    zwei = 1
                    break
                }
                if(umfraglist.id == umfraglisteFertig.AnketumId || umfraglist.datumKraj==null) ein = 1
            }
            if(zwei == 1 || ein == 0)  liste.add(umfraglist)

        }
        umfraglisteA.updateAnkete(liste.sorted())
    }

    private fun greskaEmpty() {}
    private fun greska() {
        val liste = listOf<Anketa>()
        umfraglisteA.updateAnkete(liste)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var aussicht =  inflater.inflate(R.layout.activity_main, container, false)
        spinnkoder = aussicht.findViewById(R.id.filterAnketa)
        umfragliste = aussicht.findViewById(R.id.listaAnketa)
        umfragliste.layoutManager = GridLayoutManager(
            activity,
            2
        )
        umfraglisteA = AnketaListAdapter(listOf()) { umfraglist -> anketaPitanja(umfraglist)}
        umfragliste.adapter = umfraglisteA
        if(checkConnection()) {
            umfraglisteVm.getUpisane(onSuccess = ::erfolgEin, onError = ::greskaEmpty)
        }
        else{
            umfraglisteVm.getUpisaneDB(onSuccess = ::erfolgEin, onError = ::greskaEmpty)
        }
        val spinnkoderHilf = arrayOf(
            "Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete"
        )
        val zwischenstecker = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, spinnkoderHilf
        )
        zwischenstecker.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnkoder.adapter = zwischenstecker
        spinnkoder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(aa: AdapterView<*>?, ab: View?, ac: Int, ad: Long) {
                if (ac == 3) {
                    umfraglisteVm.getUpisane(onSuccess = ::erfolgFunf, onError = ::greskaEmpty)
                }
                if (ac == 4) {
                    umfraglisteVm.getUpisane(onSuccess = ::erfolgNeun, onError = ::greskaEmpty)
                }
                if (ac == 0) {
                    umfraglisteVm.getUpisane(onSuccess = ::erfolgEin, onError = ::greskaEmpty)
                }
                if (ac == 1) {
                    umfraglisteVm.getAll(onSuccess = ::erfolgZwei, onError = ::greskaEmpty)
                }
                if (ac == 2) {
                    umfraglisteVm.getUpisane(onSuccess = ::erfolgDrei, onError = ::greskaEmpty)
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        return aussicht
    }

    companion object {
        fun newInstance(): FragmentAnkete = FragmentAnkete()
    }

    private fun anketaPitanja(anketa : Anketa) {
        var kalender: Calendar = Calendar.getInstance()
        val datei: Date = kalender.time
        if(!spinnkoder.selectedItem.equals("Sve ankete") && datei >= anketa.datumPocetak) {
            val fragmenten = mutableListOf<Fragment>()
            UmfraglisteJetzt = anketa
            GlobalScope.launch(Dispatchers.IO){
                launch(Dispatchers.IO){
                    umfraglisteVm.getPitanjaFragment1(anketa.id, onSuccess = ::erfolgSechs, onError = ::greskaEmpty)
                }
                delay(500)
                val frage = umfraglisteVm.pitanja.value
                fragen = frage!!
                for (frag in frage!!) {
                    val listen = ArrayList<String>()
                    listen.addAll(frag.opcije)
                    FrageJetzt = frag
                    val fragmente = FragmentPitanje.newInstance(
                        frag.tekstPitanja,
                        listen,
                        anketa.naziv,
                        frag.id,
                        frag.naziv
                    )
                    fragmenten.add(fragmente)
                }
                launch(Dispatchers.IO){
                    umfraglisteVm.zapocniAnketu(anketa.id, onSuccess = ::erfolgSieben, onError = ::greskaEmpty)
                }
                delay(500)
                val umfraglist = umfraglisteVm.anketaTkn.value
                if(umfraglist==null){
                    launch(Dispatchers.IO){
                        umfraglisteVm.getPoceteAnkete(onSuccess = ::erfolgAcht, onError = ::greskaEmpty)
                    }
                    delay(500)
                    val umfraglistFertig = umfraglisteVm.anketeTaken.value
                    for(hilf in umfraglistFertig!!){
                        if(hilf.AnketumId==anketa.id){
                            umfraglisteMach = hilf
                            val fragmente = FragmentPredaj.newInstance(hilf.progres, anketa.naziv, "Istrazivanje")
                            fragmenten.add(fragmente)
                            break
                        }
                    }
                }
                else {
                    umfraglisteMach = umfraglist
                    val fragmente = FragmentPredaj.newInstance(
                        umfraglist.progres,
                        anketa.naziv,
                        "Istrazivanje"
                    )
                    fragmenten.add(fragmente)
                }
                withContext(Dispatchers.Main){
                    pagger.offscreenPageLimit = fragmenten.size
                    pagerAdapterFp.refreshAll(fragmenten, 0)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            spinnkoder.selectedItem.equals("Sve moje ankete") -> {
                umfraglisteVm.getUpisane(onSuccess = ::erfolgEin, onError = ::greskaEmpty)
            }
            spinnkoder.selectedItem.equals("Sve ankete") -> {
                umfraglisteVm.getAll(onSuccess = ::erfolgZwei, onError = ::greskaEmpty)
            }
            spinnkoder.selectedItem.equals("Urađene ankete") -> {
                umfraglisteVm.getUpisane(onSuccess = ::erfolgDrei, onError = ::greskaEmpty)
            }
            spinnkoder.selectedItem.equals("Buduće ankete") -> {
                umfraglisteVm.getUpisane(onSuccess = ::erfolgFunf, onError = ::greskaEmpty)
            }
            spinnkoder.selectedItem.equals("Prošle ankete") -> {
                umfraglisteVm.getUpisane(onSuccess = ::erfolgNeun, onError = ::greskaEmpty)
            }
        }
    }


    fun checkConnection() : Boolean{
        val Netzwerkverbindung = CheckNetworkConnection(requireActivity().application)
        var richtig = true
        Netzwerkverbindung.observe(viewLifecycleOwner) { isConnected -> richtig = isConnected }
        return richtig
    }
}
package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.viewmodel.AnketaDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class FragmentPitanje : Fragment() {
    private lateinit var adappteren : ArrayAdapter<String>
    private lateinit var umfragliste: String
    private lateinit var frage : String
    private lateinit var fragNummer : String
    private var umgraglistVM = AnketaDetailsViewModel()
    private var frageStart = -1
    private lateinit var inschrifft : TextView
    private lateinit var liste : ListView
    private lateinit var button : Button
    private val  liste2 = arrayListOf<String>()


    private fun uspjesno() {}
    private fun greska() {}

    companion object {
        fun newInstance(string: String, antwort : ArrayList<String>, umfragliste : String, fragen : Int, fragName : String) = FragmentPitanje().apply {
            arguments = Bundle().apply {
                putString("meinString", string)
                putStringArrayList("meinAntwort", antwort)
                putString("umfragliste", umfragliste)
                putInt("fragen", fragen)
                putString("name", fragName)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var aussicht =  inflater.inflate(R.layout.fragment_pitanje, container, false)
        arguments?.getString("name")?.let { fragNummer = it }
        inschrifft = aussicht.findViewById(R.id.tekstPitanja)
        liste = aussicht.findViewById(R.id.odgovoriLista)
        button = aussicht.findViewById(R.id.dugmeZaustavi)
        arguments?.getString("meinString")?.let { frage = it }
        inschrifft.text = frage
        arguments?.getString("umfragliste")?.let { umfragliste = it }
        arguments?.getInt("fragen")?.let { frageStart = it }
        frageID = frageStart
        arguments?.getStringArrayList("meinAntwort")?.let { liste2.addAll(it) }
        adappteren = MyArrayAdapter(requireContext(), R.layout.fragment_pitanje2, liste2, frage)
        liste.adapter = adappteren
        val kalender: Calendar = Calendar.getInstance()
        val datei: Date = kalender.time
        GlobalScope.launch(Dispatchers.IO){
            launch(Dispatchers.IO){
                umgraglistVM.getOdgovoriAnketa(UmfraglisteJetzt.id, onSuccess = ::uspjesno, onError = ::greska)
            }
            delay(700)
            val antwort = umgraglistVM.odgovoriZaPitanje.value
            var hilf = 0
            if(antwort!=null) {
                for (antworten in antwort) {
                    if (antworten.pitanjeId == frageStart) {
                        hilf = 1
                        break
                    }
                }
            }
            if(datei >= UmfraglisteJetzt.datumPocetak && umfraglisteMach.progres!=100 && hilf==0) {
                liste.setOnItemClickListener { adapterView, view, counter, l ->
                    spinner = counter
                    frageID = frageStart
                    adappteren.notifyDataSetChanged()
                    liste.isEnabled = false
                }
            }
            else  liste.isEnabled = false

        }
        button.setOnClickListener {
            val fagmenten = mutableListOf(
                FragmentAnkete.newInstance(),
                FragmentIstrazivanje.newInstance()
            )
            pagerAdapterFp.refreshAll(fagmenten,0)
        }
        return aussicht
    }

}
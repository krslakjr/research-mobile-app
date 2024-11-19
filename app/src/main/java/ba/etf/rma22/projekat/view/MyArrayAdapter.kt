package ba.etf.rma22.projekat.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.viewmodel.AnketaDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyArrayAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val elements: ArrayList<String>, private val inschriffte : String):
    ArrayAdapter<String>(context, layoutResource, elements) {

    private fun uspjesno(liste : List<Odgovor>){
    }
    private fun uspesno(progressen : Int){
        ba.etf.rma22.projekat.data.repositories.progressen =progressen
    }
    private fun onError() {}

    private fun greska(progressen : Int) {
        if(progressen == -1){
            val anzeige = Toast.makeText(context, "Na ovo pitanje je vec dat odgovor!", Toast.LENGTH_SHORT)
            anzeige.show()
        }
    }

    override fun getView(pozition: Int, neueAussicht: View?, eltern: ViewGroup): View {
        var aussicht = neueAussicht
        aussicht = LayoutInflater.from(context).inflate(R.layout.fragment_pitanje2, eltern, false)
        val inschrifft = aussicht.findViewById<TextView>(R.id.tekstOdgovor)
        val element = elements.get(pozition)
        inschrifft.text=element
        val anketaDetailsViewModel = AnketaDetailsViewModel()
        if(spinner==-1){
            GlobalScope.launch(Dispatchers.Main) {
                launch {
                    anketaDetailsViewModel.getOdgovoriAnketaAdapter(
                        UmfraglisteJetzt.id,
                        onSuccess = ::uspjesno,
                        onError = ::onError
                    )
                }
                delay(700)
                val antwort = anketaDetailsViewModel.odgovori.value
                if(antwort!=null) {
                    var hilf = false
                    for (antwort in antwort) {
                        for (pitanje in fragen) {
                            if (antwort.anketaTakenId == umfraglisteMach.id && antwort.odgovoreno == pozition && inschriffte == pitanje.tekstPitanja && antwort.pitanjeId == pitanje.id ) {
                                inschrifft.setTextColor(Color.parseColor("#0000FF"))
                                hilf = true
                                break
                            }
                        }
                        if (hilf) {
                            break
                        }
                    }
                }
            }
        }
        else if(spinner!=-1 && spinner==pozition){
            GlobalScope.launch(Dispatchers.IO){
                launch(Dispatchers.IO){
                    anketaDetailsViewModel.postaviOdgovorAnketa(umfraglisteMach.id, frageID, spinner, onSuccess = ::uspesno, onError = ::greska)
                }
                delay(500)
                if(anketaDetailsViewModel.prog>0) inschrifft.setTextColor(Color.parseColor("#0000FF"))

                spinner = -1
            }
        }
        return aussicht
    }

}
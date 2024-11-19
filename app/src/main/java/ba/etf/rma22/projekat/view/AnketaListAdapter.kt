package ba.etf.rma22.projekat.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.meinUmfraglist
import ba.etf.rma22.projekat.data.repositories.alleUmfragliste
import ba.etf.rma22.projekat.viewmodel.AnketaDetailsViewModel
import kotlinx.coroutines.*
import java.util.*


private var progressen = 0
private lateinit var datei : Date
private var ubung = false

class AnketaListAdapter(
    private var umfrage : List<Anketa>,
    private val onItemClicked : (umfrag:Anketa) -> Unit) : RecyclerView.Adapter<AnketaListAdapter.AnketaViewHolder>() {
    inner class AnketaViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val endeProgressen: ProgressBar = view.findViewById(R.id.progresZavrsetka)
        val dateiFinal: TextView = view.findViewById(R.id.datum)
        val forschung : TextView = view.findViewById(R.id.istrazivanje)
        val umfrageName: TextView = view.findViewById(R.id.anketaTitle)
        val umfrageStatus: ImageView = view.findViewById(R.id.statusAnkete)
    }
    /*Ovu funkciju sam dosta izmijenjala za razliku od prve dvije spirale, sve radi isto, samo sada je sve povezano sa serverom i sa bazom :) */
    override fun onBindViewHolder(aussicht: AnketaViewHolder, pozition: Int) {
        aussicht.umfrageName.text = umfrage[pozition].naziv
        val umfraglisteVM = AnketaDetailsViewModel()
        GlobalScope.launch(Dispatchers.IO){
            var forschung = 0
            if(meinUmfraglist) {
                launch(Dispatchers.IO){
                    umfraglisteVM.getUpisaneGrupe(onSuccess = ::uspjesnovier, onError = ::greska)
                }
                delay(500)
                val gruppen = umfraglisteVM.grupeStudenta.value
                for (mein in alleUmfragliste) {
                    for (gruppe in gruppen!!) {
                        if (mein.id == umfrage[pozition].id && mein.idGrupe == gruppe.id && mein.idIstrazivanja == gruppe.istrazivanjeId && !mein.iskoristeno) {
                            mein.iskoristeno = true
                            forschung = gruppe.istrazivanjeId
                            break
                        }
                    }
                    if (forschung != 0) break
                }
                launch(Dispatchers.IO) {
                    umfraglisteVM.getIstrazivanjeZaId(
                        forschung,
                        onSuccess = ::uspjesno,
                        onError = ::greska
                    )
                }
                delay(1000)
                if(forschung!=0) {
                    aussicht.forschung.text = umfraglisteVM.istrazivanjeZaId.value!!.naziv
                }
                else{
                    aussicht.forschung.text = "IM1"
                }
            }
            else{
                launch(Dispatchers.IO){
                    umfraglisteVM.getGrupeZaAnketu(umfrage[pozition].id,onSuccess = ::uspjesnozwei, onError = ::greska)
                }
                delay(500)
                val grosseGruppe = umfraglisteVM.grupeZaAnketu.value
                var einigeListe = mutableListOf<String>()
                for(gruppe in grosseGruppe!!) {
                    launch(Dispatchers.IO) {
                        umfraglisteVM.getIstrazivanjeZaId(
                            gruppe.istrazivanjeId,
                            onSuccess = ::uspjesno,
                            onError = ::greska
                        )
                    }
                    delay(500)
                    einigeListe.add(umfraglisteVM.istrazivanjeZaId.value!!.naziv)
                }
                var hilf = 0
                var inschrift = ""
                val zweiteListe = einigeListe.distinct()
                for(hilfForschung in zweiteListe){
                    if(hilf == 0){
                        inschrift += hilfForschung
                        hilf = 1
                    }
                    else  inschrift = inschrift + "/" + hilfForschung

                }
                aussicht.forschung.text = inschrift
            }
            launch(Dispatchers.IO){
                umfraglisteVM.getPoceteAnketeAdapter(pozition, onSuccess = ::uspjesnodrei, onError = ::greska)
            }
            delay(1000)
            if(pozition == umfrage.size-1){
                for(mein in alleUmfragliste)  mein.iskoristeno = false
            }


            if(pozition == umfrage.size-1 && !meinUmfraglist)  meinUmfraglist = true

            val umfraglisteFertig = umfraglisteVM.anketeTakenAdapter.value
            if(umfraglisteFertig!=null) {
                for (umgragelistHilf in umfraglisteFertig) {
                    if (umgragelistHilf.AnketumId == umfrage[pozition].id) {
                        ubung = true
                        progressen = umgragelistHilf.progres
                        datei = umgragelistHilf.datumRada
                        break
                    }
                }
            }
            if(ubung && progressen!=0){
                if(progressen>=90 && progressen<=100) progressen = 100
                else if(progressen>=70 && progressen<=89) progressen = 80
                else if(progressen>=50 && progressen<=69) progressen=60
                else if(progressen>=30 && progressen<=49) progressen=40
                else if(progressen>=10 && progressen<=29) progressen=20
                else if(progressen >= 0 && progressen<=9) progressen = 0
            }
            aussicht.endeProgressen.setProgress(progressen)
            val kontekst : Context = aussicht.umfrageStatus.getContext()
            var kalender: Calendar = Calendar.getInstance()
            val dateiHilf: Date = kalender.time

            if((umfrage[pozition].datumKraj !=null && dateiHilf.compareTo(umfrage[pozition].datumPocetak) >=0
                        &&  dateiHilf.compareTo(umfrage[pozition].datumKraj) >0 || progressen==100)){
                val id : Int = kontekst.getResources().getIdentifier("plava","drawable",kontekst.getPackageName())
                withContext(Dispatchers.Main) {
                    aussicht.umfrageStatus.setImageResource(id)
                    val dateineu: Date = datei
                    kalender.setTime(dateineu)
                    aussicht.dateiFinal.text =
                        "Anketa urađena: " + kalender.get(Calendar.DAY_OF_MONTH) + "." + kalender.get(Calendar.MONTH) + "." + kalender.get(Calendar.YEAR)
                }
            }



            if(progressen<100 && dateiHilf.compareTo(umfrage[pozition].datumPocetak) >=0 ){
                val id : Int = kontekst.getResources().getIdentifier("zelena","drawable",kontekst.getPackageName())
                withContext(Dispatchers.Main) {
                    aussicht.umfrageStatus.setImageResource(id)
                    aussicht.dateiFinal.text = "Vrijeme zatvaranja: "
                }
            }
            if( dateiHilf.compareTo(umfrage[pozition].datumKraj) >0 && umfrage[pozition].datumKraj!=null){
                val id : Int = kontekst.getResources().getIdentifier("crvena","drawable",kontekst.getPackageName())
                aussicht.umfrageStatus.setImageResource(id)
                kalender.setTime(umfrage[pozition].datumKraj)
                aussicht.dateiFinal.text = "Anketa zatvorena: " + kalender.get(Calendar.DAY_OF_MONTH) + "."+ kalender.get(Calendar.MONTH) + "." + kalender.get(Calendar.YEAR)
            }

            if(dateiHilf.compareTo(umfrage[pozition].datumPocetak) <0){
                val id : Int = kontekst.getResources().getIdentifier("zuta","drawable",kontekst.getPackageName())
                aussicht.umfrageStatus.setImageResource(id)
                kalender.setTime(umfrage[pozition].datumPocetak)
                aussicht.dateiFinal.text = "Vrijeme aktiviranja: " + kalender.get(Calendar.DAY_OF_MONTH) + "."+ kalender.get(Calendar.MONTH) + "." + kalender.get(Calendar.YEAR)
            }

            withContext(Dispatchers.Main) {
                aussicht.itemView.setOnClickListener { onItemClicked(umfrage[pozition]) }
            }
            ubung = false
            progressen = 0
        }
        GlobalScope.launch(Dispatchers.IO){ }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AnketaViewHolder {
        val aussicht = LayoutInflater.from(viewGroup.context) .inflate(R.layout.item_istrazivanje, viewGroup, false)
        return AnketaViewHolder(aussicht)
    }

    fun updateAnkete(umfragen: List<Anketa>) {
        this.umfrage = umfragen
        notifyDataSetChanged()
    }

    override fun getItemCount() = umfrage.size
    private fun uspjesnodrei(pozition : Int, start : List<AnketaTaken>){}
    private fun uspjesnozwei(liste : List<Grupa>){}
    private fun uspjesno(istrazivanje: Istrazivanje){}
    private fun uspjesnovier() {}
    private fun greska(){}
}
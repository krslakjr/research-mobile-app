package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.viewmodel.AnketaDetailsViewModel

class FragmentIstrazivanje : Fragment() {

    private lateinit var button : Button
    private var umfraglisteVM = AnketaDetailsViewModel()
    private var jetzt = 0
    private var umfraglisteJeztz = mutableListOf<Istrazivanje>()
    private lateinit var jahrs : Spinner
    private lateinit var umfraglistes : Spinner
    private lateinit var gruppens : Spinner



    companion object {
        fun newInstance(): FragmentIstrazivanje = FragmentIstrazivanje()
    }
    private fun erfolg1(umfraglisten : List<Istrazivanje>){}
    private fun erfolg2(liste : List<Grupa>){}
    private fun erfolg3(name : String, liste: List<Grupa>){
        for(gruppe in liste){
            if(gruppe.naziv==name){
                umfraglisteVM.upisiUGrupu(gruppe.id, onSuccess = ::erfolg4, onError = ::greska)
            }
        }
    }
    private fun erfolg4(){}
    private fun greska(){}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var aussicht =  inflater.inflate(R.layout.activity_upis, container, false)
        jahrs = aussicht.findViewById(R.id.odabirGodina)
        umfraglistes = aussicht.findViewById(R.id.odabirIstrazivanja)
        gruppens = aussicht.findViewById(R.id.odabirGrupa)
        button = aussicht.findViewById(R.id.dodajIstrazivanjeDugme)
        button.isEnabled = false
        val jahreen = arrayOf(
            "1","2","3","4","5"
        )
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, jahreen
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jahrs.adapter=adapter
        jahrs.setSelection(Jahr)
        var einigeliste = mutableListOf<String>()
        val adappter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, einigeliste
        )
        adappter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        umfraglistes.adapter=adappter
        var gruppen = mutableListOf<String>()
        val adappteren = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, gruppen
        )
        adappteren.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gruppens.adapter=adappteren
        umfraglisteVM.getIstrazivanja(
            onSuccess = ::erfolg1,
            onError = ::greska
        )
        jahrs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(aa: AdapterView<*>?, ab: View?, ac: Int, ad: Long) {
                Jahr = ac
                jetzt = ac+1
                umfraglisteVM.istrazivanja.observe(requireActivity(), Observer { id ->
                    einigeliste.clear()
                    if(id!=null){
                        einigeliste.add(" ")
                        for(umfraglist in id){
                            if(umfraglist.godina==ac+1){
                                umfraglisteJeztz.add(umfraglist)
                                einigeliste.add(umfraglist.naziv)
                            }
                        }
                    }
                    adappter.notifyDataSetChanged()
                    umfraglistes.setSelection(0)
                })
            }
            override fun onNothingSelected(aa: AdapterView<*>?) {

            }
        }
        umfraglistes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(aa: AdapterView<*>?, ab: View?, ac: Int, ad: Long) {
                var jetzten = 0
                var umfraglistten = aa?.getItemAtPosition(ac).toString()

                umfraglisteVM.istrazivanja.observe(requireActivity(), Observer {
                    if (it != null) {
                        for (umfragl in it) {
                            if (umfragl.naziv == umfraglistten) {
                                jetzten = umfragl.id
                                break
                            }
                        }
                    }
                })
                umfraglisteVM.getGrupeZaIstrazivanje(jetzten, onSuccess = ::erfolg2, onError = ::greska)
                umfraglisteVM.getUpisaneGrupe(onSuccess = ::erfolg4, onError = ::greska)
                umfraglisteVM.grupeZaIstrazivanje.observe(requireActivity(), Observer {
                    if(it!=null){
                        gruppen.clear()
                        for(gruppe in it){
                            gruppen.add(gruppe.naziv)
                        }
                    }
                    val listen = mutableListOf<String>()
                    for(gruppe in gruppen){
                        for(grupppen in umfraglisteVM.grupeStudenta.value!!){
                            if(gruppe == grupppen.naziv){
                                listen.add(gruppe)
                            }
                        }
                    }
                    gruppen.removeAll(listen)
                    adappteren.notifyDataSetChanged()
                    button.isEnabled = gruppen.size != 0
                    umfraglisteJeztz.clear()
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        button.setOnClickListener {
            if(jahrs.selectedItem!=null && umfraglistes.selectedItem!=null && gruppens.selectedItem!=null){
                umfraglisteVM.getGrupeFragmentIst(gruppens.selectedItem.toString(), onSuccess = ::erfolg3, onError = ::greska)
                val inschrifft = "Uspješno ste upisani u grupu " + gruppens.selectedItem.toString() + " istraživanja " + umfraglistes.selectedItem.toString() + "!"
                pagerAdapterFp.refreshFragment(1, FragmentPoruka.newInstance(inschrifft))
            }
        }
        return aussicht
    }

}
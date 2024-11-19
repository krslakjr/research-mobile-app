package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.repositories.*
import java.util.*

class FragmentPredaj : Fragment() {
    private lateinit var progressen : TextView
    private lateinit var button : Button

    private fun bestimmenProzenten(start : Int) : Int {
        var hilf = start
        if(hilf>=90 && hilf<=100) hilf = 100

        else if(hilf>=70 && hilf<=89) hilf = 80

        else if(hilf>=50 && hilf<=69) hilf=60

        else if(hilf>=30 && hilf<=49) hilf=40

        else if(hilf>=10 && hilf<=29) hilf=20

        else if(hilf >= 0 && hilf<=9) hilf = 0

        return hilf
    }

    override fun onResume() {
        super.onResume()
        if(ba.etf.rma22.projekat.data.repositories.progressen !=-1) {
            progressen.text = ba.etf.rma22.projekat.data.repositories.progressen.toString() + "%"
            ba.etf.rma22.projekat.data.repositories.progressen = -1
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var aussicht =  inflater.inflate(R.layout.fragment_predaj, container, false)
        button = aussicht.findViewById(R.id.dugmePredaj)
        progressen = aussicht.findViewById(R.id.progresTekst)
        arguments?.getInt("mojInt").let { progressen.text = it.toString() + "%" }
        val inschrifft = "Završili ste anketu " + UmfraglisteJetzt + " u okviru istraživanja " + ForschungJetzt
        var kalender: Calendar = Calendar.getInstance()
        val datei: Date = kalender.time
        if(umfraglisteMach.progres!=100 && datei >= UmfraglisteJetzt.datumPocetak ) {
            button.setOnClickListener {
                val fragmenten = mutableListOf(
                    FragmentAnkete.newInstance(),
                    FragmentPoruka.newInstance(inschrifft)
                )
                pagerAdapterFp.refreshAll(fragmenten,1)
            }
        }
        else  button.isEnabled = false

        return aussicht
    }

    companion object {
        fun newInstance(progressen : Int, umfraglisteName : String, forschung : String) = FragmentPredaj().apply {
            arguments = Bundle().apply {
                var hilff = bestimmenProzenten(progressen)
                putInt("mojInt", hilff)
                putString("nazivIstrazivanja", forschung)
                putString("nazivAnkete", umfraglisteName)
            }
        }
    }




}
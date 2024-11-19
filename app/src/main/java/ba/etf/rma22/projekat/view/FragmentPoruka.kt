package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.repositories.pagerAdapterFp

class FragmentPoruka : Fragment() {

    private lateinit var nachricht : TextView

    companion object {
        fun newInstance(tekst : String) = FragmentPoruka().apply {
            arguments = Bundle().apply {
                putString("tekst", tekst)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Handler(Looper.getMainLooper()).postDelayed({
            pagerAdapterFp.refreshFragment(1, FragmentIstrazivanje.newInstance())
        }, 100)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var aussicht =  inflater.inflate(R.layout.fragment_poruka, container, false)
        nachricht = aussicht.findViewById(R.id.tvPoruka)
        arguments?.getString("tekst").let { nachricht.text = it }
        return aussicht
    }




}
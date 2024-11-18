package ba.etf.rma22.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.view.ListaAnketaAdapter
import ba.etf.rma22.projekat.viewmodel.AnketaListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var Umfrageliste: RecyclerView
    private lateinit var UmfragelisteAdapter: ListaAnketaAdapter
    private lateinit var Abfragefilter: Spinner
    private lateinit var Anmeldetaste: FloatingActionButton
    private var UmfragelisteViewModel = AnketaListViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Umfrageliste = findViewById(R.id.listaAnketa)
        Anmeldetaste = findViewById(R.id.upisDugme)
        Abfragefilter = findViewById(R.id.filterAnketa)
        Anmeldetaste.setOnClickListener {
            val intent = Intent(this, UpisIstrazivanje::class.java)
            startActivity(intent)
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.spinerFilter,
            android.R.layout.simple_spinner_item).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            Abfragefilter.adapter = adapter
        }
        Abfragefilter.onItemSelectedListener = this
        Umfrageliste.layoutManager = GridLayoutManager(this, 2)
        //sljedeca linija mi koristi za razdvajanje elemenata RecycleViewa
        //link gdje sam pronasla rjesenje https://stackoverflow.com/questions/3669325/notifydatasetchanged-example
        //klasa DecoradorDeItensPadrao mi sluzi za stvaranje tih 'margina'
        Umfrageliste.addItemDecoration(DecoradorDeItensPadrao(18, 4))
        UmfragelisteAdapter = ListaAnketaAdapter(UmfragelisteViewModel.getMyAnkete())
        Umfrageliste.adapter = UmfragelisteAdapter


    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pozition: Int, id: Long) {
        when (parent.getItemAtPosition(pozition)) {
            /*Bukvalno u ovom dijelu, vršim klasično sortiranje anketa unutar određene grupe po atributu ==datumPocetka*/
            "Sve ankete" -> {
                UmfragelisteAdapter = ListaAnketaAdapter(UmfragelisteViewModel.getAll().sortedBy { it.datumPocetka })
                Umfrageliste.adapter = UmfragelisteAdapter
                UmfragelisteAdapter.azurirajAnketu(UmfragelisteViewModel.getAll())
            }
            "Urađene ankete" -> {
                UmfragelisteAdapter = ListaAnketaAdapter(UmfragelisteViewModel.getDone().sortedBy { it.datumPocetka })
                Umfrageliste.adapter = UmfragelisteAdapter
                UmfragelisteAdapter.azurirajAnketu(UmfragelisteViewModel.getDone())
            }
            "Buduće ankete" -> {
                UmfragelisteAdapter = ListaAnketaAdapter(UmfragelisteViewModel.getFuture().sortedBy { it.datumPocetka })
                Umfrageliste.adapter = UmfragelisteAdapter
                UmfragelisteAdapter.azurirajAnketu(UmfragelisteViewModel.getFuture())
            }
            "Prošle ankete" -> {
                UmfragelisteAdapter = ListaAnketaAdapter(UmfragelisteViewModel.getNotTaken().sortedBy { it.datumPocetka })
                Umfrageliste.adapter = UmfragelisteAdapter
                UmfragelisteAdapter.azurirajAnketu(UmfragelisteViewModel.getNotTaken())
            }
            else -> {
                UmfragelisteAdapter = ListaAnketaAdapter(UmfragelisteViewModel.getMyAnkete().sortedBy { it.datumPocetka })
                Umfrageliste.adapter = UmfragelisteAdapter
                UmfragelisteAdapter.azurirajAnketu(UmfragelisteViewModel.getMyAnkete())
            }
        }
    }

    /*Ovu metodu korstim kako bi updateovala ankete unutar odredjene kategorije anketa*/
    override fun onResume() {
        super.onResume()
        UmfragelisteAdapter.notifyDataSetChanged()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}
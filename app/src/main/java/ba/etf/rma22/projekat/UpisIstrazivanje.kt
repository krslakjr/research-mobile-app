package ba.etf.rma22.projekat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import ba.etf.rma22.projekat.data.models.Istrazivanja
import ba.etf.rma22.projekat.view.ListaAnketaAdapter
import ba.etf.rma22.projekat.viewmodel.AnketaListViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjaListViewModel


class UpisIstrazivanje : AppCompatActivity() {
    private lateinit var jahrAuswahl: Spinner
    private lateinit var umfrageAuswahl: Spinner
    private lateinit var gruppeAuswahl: Spinner
    private lateinit var umfrageHinzufugenB: Button
    private var umfrageListViewModel = AnketaListViewModel()
    private var forschungListViewModel = IstrazivanjaListViewModel()
    private lateinit var listUmfrageAdapter: ListaAnketaAdapter
    /*stvaram referencu da bih mogla ostvariti dodavanje nove ankete na početnu stranu :
    * ovakvo rješenje sam pronašla googlajuci :
    * link 1: https://stackoverflow.com/questions/56833657/preferencemanager-getdefaultsharedpreferences-deprecated-in-android-q
    * link 2: https://stackoverflow.com/questions/22178414/what-are-the-preferencemanager-and-sharedpreference-classes-used-for-in-android#:~:text=PreferenceManager%3A,getSharedPreferences(String%2C%20int).*/
    private var praferenzManager: PrivatePM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ba.etf.rma22.projekat.R.layout.activity_upis_istrazivanje)

        praferenzManager = PrivatePM(this)
        umfrageHinzufugenB = findViewById(R.id.dodajIstrazivanjeDugme)
        jahrAuswahl = findViewById(R.id.odabirGodina)
        umfrageAuswahl = findViewById(R.id.odabirIstrazivanja)
        gruppeAuswahl = findViewById(R.id.odabirGrupa)

        listUmfrageAdapter = ListaAnketaAdapter(umfrageListViewModel.getMyAnkete())
/*U narednih nekoliko linija ove klase postavljat cu i dodjeljivati adapteru preko spinera Godine tipa PRVA,DRUGA,TRECA i td. */
        ArrayAdapter.createFromResource(
            this,
            R.array.Godine,
            android.R.layout.simple_spinner_item
        ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            jahrAuswahl.adapter = adapter
        }
        var jahr: Int = 0
        jahrAuswahl.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {
                jahrAuswahl.setSelection(praferenzManager!!.auswahl)
            }

            override fun onItemSelected(
                elternteil: AdapterView<*>,
                aussicht: View,
                position: Int,
                id: Long
            ) {
                praferenzManager!!.auswahl = position
                jahr =
                    when (elternteil.getItemAtPosition(position)) {
                        "1" -> 1
                        "2" -> 2
                        "3" -> 3
                        "4" -> 4
                        "5" -> 5
                        else -> 0
                    }
                if(jahr == 0) {
                    ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.Odaberite,
                        android.R.layout.simple_spinner_item
                    ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        umfrageAuswahl.adapter = adapter
                    }
                }
                if (jahr == 1) {
                    ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.Prva,
                        android.R.layout.simple_spinner_item
                    ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        umfrageAuswahl.adapter = adapter
                    }
                } else if (jahr == 2) {
                    ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.Druga,
                        android.R.layout.simple_spinner_item
                    ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        umfrageAuswahl.adapter = adapter
                    }
                } else if (jahr == 3) {
                    ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.Treca,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        umfrageAuswahl.adapter = adapter
                    }
                } else if (jahr == 4) {
                    ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.Cetvrta,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        umfrageAuswahl.adapter = adapter
                    }
                } else if (jahr == 5) {
                    ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.Peta,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        umfrageAuswahl.adapter = adapter
                    }
                }
            }
        })
        /*Ista stvar kao i u slucaju iznad, sada su samo u pitanju Istrazivanja, pravim spiner koji ce
        * sluziti za to i pridruzujem mu moguce vrijednosti (zavisi od spinera godina) a odma kada
        * se izaere odredjeno istrazivanje u trecem spineru se pojavljuju moguce grupe*/
        var gewahlteUmfrage: Boolean = false
        umfrageAuswahl.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
            override fun onItemSelected(
                elternteil: AdapterView<*>,
                aussicht: View,
                position: Int,
                id: Long
            ) {
                var umfrage: String = elternteil.getItemAtPosition(position).toString()
                when (umfrage) {
                    "Istraživanje 1" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja1,
                        android.R.layout.simple_spinner_item
                    ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                    "Istraživanje 2" ->
                        ArrayAdapter.createFromResource(
                            aussicht.context,
                            R.array.grupeIstraživanja2,
                            android.R.layout.simple_spinner_item
                        ).also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            gruppeAuswahl.adapter = adapter
                        }
                    "Istraživanje 3" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja3,
                        android.R.layout.simple_spinner_item
                    ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                    "Istraživanje 4" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja4,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                    "Istraživanje 5" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja5,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                    "Istraživanje 6" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja6,
                        android.R.layout.simple_spinner_item
                    ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                    "Istraživanje 7" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja7,
                        android.R.layout.simple_spinner_item
                    ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                    "Istraživanje 8" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja8,
                        android.R.layout.simple_spinner_item
                    ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                    "Istraživanje 9" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja9,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                    "Istraživanje 10" -> ArrayAdapter.createFromResource(
                        aussicht.context,
                        R.array.grupeIstraživanja10,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        gruppeAuswahl.adapter = adapter
                    }
                }
                gewahlteUmfrage = true
            }
        })

        /*Sada slijedi dio koji mi sluzi da upisi dugme reaguje, i da se na pocetnoj stranici
        * fakat pojavi istrazivanje koje je izabrano */
        gruppeAuswahl.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(arg0: AdapterView<*>?) {}
            override fun onItemSelected(
                elternteil: AdapterView<*>,
                aussicht: View,
                position: Int,
                id: Long
            ) {
                if(jahr != 0 && gewahlteUmfrage)
                    umfrageHinzufugenB.visibility = View.VISIBLE
            }
        })
        umfrageHinzufugenB.setOnClickListener {
            anmelden()
        }
    }

    /*upisuje na kurs i pokazuje na pocetnoj stranici :: */
    private fun anmelden() {
        var umfrage = umfrageAuswahl.selectedItem.toString()
        var gruppe = gruppeAuswahl.selectedItem
        var jahr = jahrAuswahl.selectedItem.toString()

        forschungListViewModel.Einschreibungsforschung(Istrazivanja(umfrage, jahr.toInt()))
        for (ankete in umfrageListViewModel.getAll()) {
            if (ankete.nazivIstrazivanja.equals(umfrage) || ankete.nazivGrupe.equals(gruppe) && !umfrageListViewModel.getMyAnkete()
                    .contains(ankete))    umfrageListViewModel.meineHinzufugen(ankete)
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
        jahrAuswahl.setSelection(praferenzManager?.auswahl!!)
        umfrageAuswahl.setSelection(praferenzManager?.auswahl!!)
        gruppeAuswahl.setSelection(praferenzManager?.auswahl!!)
    }
}
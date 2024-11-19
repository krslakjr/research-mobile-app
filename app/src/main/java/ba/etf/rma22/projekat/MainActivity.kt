package ba.etf.rma22.projekat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.view.*
import ba.etf.rma22.projekat.viewmodel.AnketaDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

lateinit var pagger: ViewPager2
class MainActivity : AppCompatActivity() {
    private val umfraglisteVM = AnketaDetailsViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pager_main)
        IstrazivanjeIGrupaRepository.setContext(applicationContext)
        OdgovorRepository.setContext(applicationContext)
        PitanjeAnketaRepository.setContext(applicationContext)
        AccountRepository.setContext(applicationContext)
        TakeAnketaRepository.setContext(applicationContext)
        AnketaRepository.setContext(applicationContext)
        val benutzer : String? = intent.extras?.getString("payload")
        if (benutzer != null)  umfraglisteVM.upisi(applicationContext ,benutzer, onSuccess = ::onSuccess, onError = ::onError )
        GlobalScope.launch(Dispatchers.IO){
            launch(Dispatchers.IO){
                umfraglisteVM.getSveAnkete(onSuccess = ::onSuccess, onError = ::onError)
            }
        }
        pagger = findViewById(R.id.pager)
        val fragmenten = mutableListOf(FragmentAnkete.newInstance(), FragmentIstrazivanje.newInstance())
        pagerAdapterFp = ViewPagerAdapter(fragmenten, supportFragmentManager, lifecycle)
        pagger.adapter = pagerAdapterFp
    }

    private fun onSuccess() {}
    private fun onError() {}
}
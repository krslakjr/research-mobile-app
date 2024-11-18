package ba.etf.rma22.projekat

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/*Ovu klasu koristim da bi mi izgled pocetnog screena mogao izgledati tako
* Pravim spacing izmedju kartica Istrazivanja/Anketa da ne bi bile jedna na drugoj*/

class DecoradorDeItensPadrao(private val espacamentoHorizontal: Int, private val espacamentoVertical: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        expor: Rect,
        visualizar: View,
        mais: RecyclerView,
        estado: RecyclerView.State
    ) {
        super.getItemOffsets(expor, visualizar, mais, estado)
        expor.right = espacamentoVertical
        expor.left = espacamentoHorizontal
        if (mais.getChildLayoutPosition(visualizar) == 0)
            expor.top = espacamentoVertical

        expor.bottom = espacamentoHorizontal
    }
}
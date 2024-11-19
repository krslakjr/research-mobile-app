package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class GetIstrazivanjeResponse(
    @SerializedName("listaIstrazivanja") val listaIstrazivanja : List<Istrazivanje>
)

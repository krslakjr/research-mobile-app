package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class GetOdgovorResponse(
    @SerializedName("listaOdgovora") val listaOdgovora : List<Odgovor>
)

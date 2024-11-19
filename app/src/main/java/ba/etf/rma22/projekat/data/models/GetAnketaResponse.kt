package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class GetAnketaResponse(
    @SerializedName("listaAnketa") val listaAnketa : List<Anketa>
)

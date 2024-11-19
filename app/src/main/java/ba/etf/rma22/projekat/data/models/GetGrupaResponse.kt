package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class GetGrupaResponse(
    @SerializedName("listaGrupa") val listaGrupa : List<Grupa>
)

package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class GetPitanjeAnketaResponse(
    @SerializedName("lista") val lista: List<Pitanje>
)

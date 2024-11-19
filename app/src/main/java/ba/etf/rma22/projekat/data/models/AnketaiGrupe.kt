package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class AnketaiGrupe(
    @SerializedName("GrupaId") val grupaId : Int,
    @SerializedName("AnketumId") val anketumId : Int
)

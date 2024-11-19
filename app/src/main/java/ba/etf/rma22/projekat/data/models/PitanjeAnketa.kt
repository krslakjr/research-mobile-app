package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class PitanjeAnketa(
    @SerializedName("AnketumId") val anketumId : Int,
    @SerializedName("PitanjeId") val pitanjeId : Int
    /* var naziv: String, var anketa : String*/
)

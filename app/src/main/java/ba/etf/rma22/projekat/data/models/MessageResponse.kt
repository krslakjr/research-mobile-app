package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("message") val message :String
)

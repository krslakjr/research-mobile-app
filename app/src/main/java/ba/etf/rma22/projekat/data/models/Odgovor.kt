package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Odgovor(
    @ColumnInfo(name = "AnketaTakenId") @SerializedName("AnketaTakenId") val anketaTakenId: Int,
    @ColumnInfo(name = "PitanjeId") @SerializedName("PitanjeId") val pitanjeId: Int,
    //@ColumnInfo(name = "message") @SerializedName("message") val message: String
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "odgovoreno") @SerializedName("odgovoreno") val odgovoreno: Int

)

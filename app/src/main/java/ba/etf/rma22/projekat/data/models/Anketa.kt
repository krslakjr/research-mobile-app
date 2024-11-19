package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Anketa(
    @PrimaryKey @SerializedName("id") val id : Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
    @ColumnInfo(name = "datumPocetak") @SerializedName("datumPocetak") val datumPocetak: Date,
    @ColumnInfo(name = "datumKraj") @SerializedName("datumKraj") var datumKraj: Date?,
    @ColumnInfo(name = "trajanje") @SerializedName("trajanje") val trajanje: Int
) : Comparable<Anketa>{
    override fun compareTo(other: Anketa): Int {
        if(this.datumPocetak<other.datumPocetak) return -1
        else if(this.datumPocetak==other.datumPocetak) return 0
        else return 1
    }
}

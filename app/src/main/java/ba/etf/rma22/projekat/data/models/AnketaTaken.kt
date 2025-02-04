package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class AnketaTaken(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "datumRada") @SerializedName("datumRada") val datumRada: Date,
    @ColumnInfo(name = "progres") @SerializedName("progres") val progres: Int,
    @ColumnInfo(name = "AnketumId") @SerializedName("AnketumId") val AnketumId : Int,
    @ColumnInfo(name = "student") @SerializedName("student") val student: String
)

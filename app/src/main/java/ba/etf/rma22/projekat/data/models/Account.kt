package ba.etf.rma22.projekat.data.models
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import androidx.room.Entity


@Entity
data class Account(
    @PrimaryKey @SerializedName("acHash") val acHash: String
)

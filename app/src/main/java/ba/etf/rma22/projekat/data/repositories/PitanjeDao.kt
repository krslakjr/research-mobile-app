package ba.etf.rma22.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Pitanje

@Dao
interface PitanjeDao {
    @Query("SELECT * FROM pitanje")
    suspend fun getAll(): List<Pitanje>
    @Query("DELETE from pitanje")
    fun izbrisiSve()
    @Query("SELECT * FROM pitanje WHERE anketaId = :ankId")
    fun getAllZaAnketu(ankId : Int) : List<Pitanje>
    @Insert
    suspend fun insertAll(pitanja: List<Pitanje>)
}
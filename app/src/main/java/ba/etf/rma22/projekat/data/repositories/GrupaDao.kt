package ba.etf.rma22.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Grupa

@Dao
interface GrupaDao {
    @Query("SELECT * FROM grupa")
    suspend fun getAll(): List<Grupa>
    @Query("DELETE from grupa")
    fun izbrisiSve()
    @Insert
    suspend fun insertAll(grupe: List<Grupa>)
}
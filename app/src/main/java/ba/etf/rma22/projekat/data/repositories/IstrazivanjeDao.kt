package ba.etf.rma22.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDao {
    @Query("SELECT * FROM istrazivanje")
    suspend fun getAll(): List<Istrazivanje>
    @Query("DELETE from istrazivanje")
    fun izbrisiSve()
    @Insert
    suspend fun insertAll(istrazivanja: List<Istrazivanje>)
}
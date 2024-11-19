package ba.etf.rma22.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.AnketaTaken

@Dao
interface AnketaTakenDao {
    @Query("SELECT * FROM anketataken")
    suspend fun getAll(): List<AnketaTaken>
    @Query("UPDATE anketataken SET progres= :prog WHERE id =:ankId")
    suspend fun setProgress(prog : Int, ankId : Int)
    @Query("DELETE from anketataken")
    fun izbrisiSve()
    @Insert
    suspend fun insertAll(anketeTaken: List<AnketaTaken>)
}
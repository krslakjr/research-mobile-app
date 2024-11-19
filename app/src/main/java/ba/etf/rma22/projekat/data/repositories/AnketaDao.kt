package ba.etf.rma22.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*

@Dao
interface AnketaDao {
    @Query("SELECT * FROM anketa")
    fun getAll(): List<Anketa>

    @Query("DELETE from anketa")
    fun izbrisiSve()

    @Query("INSERT INTO anketa VALUES(:id, :naziv, :datumPocetak, :datumKraj, :trajanje)")
    fun unesiAnketu(id : Int, naziv: String, datumPocetak: Date, datumKraj : Date, trajanje : Int)

    @Insert
    suspend fun insertAll(ankete: List<Anketa>)
}
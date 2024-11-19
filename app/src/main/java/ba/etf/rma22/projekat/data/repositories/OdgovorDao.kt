package ba.etf.rma22.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface OdgovorDao {
    @Query("SELECT * FROM odgovor")
    suspend fun getAll(): List<Odgovor>
    @Query("SELECT * FROM odgovor WHERE AnketaTakenId = :anketaTakenId")
    suspend fun getAllZaAnketu(anketaTakenId : Int) : List<Odgovor>
    @Query("DELETE from odgovor")
    fun izbrisiSve()
    @Insert
    suspend fun insertAll(odgovori: List<Odgovor>)
}
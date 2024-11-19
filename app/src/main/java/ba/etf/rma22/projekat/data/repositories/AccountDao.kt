package ba.etf.rma22.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AccountDao {
    @Query("DELETE FROM Account")
    suspend fun izbrisiSve()

    @Query("INSERT INTO Account VALUES(:acHash)")
    suspend fun upisi(acHash: String)

    @Query("SELECT acHash from Account")
    suspend fun getHash() : String
}
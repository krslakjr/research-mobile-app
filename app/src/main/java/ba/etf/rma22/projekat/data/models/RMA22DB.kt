package ba.etf.rma22.projekat.data.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.etf.rma22.projekat.data.repositories.*

@Database(entities = arrayOf(Account::class,Anketa::class,AnketaTaken::class, Grupa::class,Istrazivanje::class,Odgovor::class,Pitanje::class), version = 1)
@TypeConverters(Converters::class)
abstract class RMA22DB : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun anketaDao(): AnketaDao
    abstract fun anketaTakenDao(): AnketaTakenDao
    abstract fun grupaDao(): GrupaDao
    abstract fun istrazivanjeDao(): IstrazivanjeDao
    abstract fun odgovorDao(): OdgovorDao
    abstract fun pitanjeDao(): PitanjeDao

    companion object {
        private var INSTANCE: RMA22DB? = null
        fun getInstance(context: Context): RMA22DB {
            if (INSTANCE == null) {
                synchronized(RMA22DB::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RMA22DB::class.java,
                "RMA22DB"
            ).build()
    }
}
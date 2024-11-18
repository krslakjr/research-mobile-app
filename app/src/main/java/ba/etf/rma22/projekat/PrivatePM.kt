package ba.etf.rma22.projekat

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences


class PrivatePM(context: Context) {
    var kontext: Context

    var auswahl: Int
        get() {
            val GemeinsameEinstellungen: SharedPreferences = kontext.getSharedPreferences(
                PREFERENCE_NAME,
                MODE_PRIVATE
            )
            return GemeinsameEinstellungen.getInt(KEY_SELECTION, 0)
        }
        set(position) {
            val GemeinsameEinstellungen: SharedPreferences = kontext.getSharedPreferences(
                PREFERENCE_NAME,
                MODE_PRIVATE
            )
            GemeinsameEinstellungen.edit().putInt(KEY_SELECTION, position).commit()
        }

    companion object {
        private const val PREFERENCE_NAME = "test"
        private const val KEY_SELECTION = "key_selection"
    }

    init {
        kontext = context
    }
}
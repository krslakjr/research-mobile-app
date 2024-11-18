package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Istrazivanja

var meineUmfrage = mutableListOf(
    Istrazivanja("Istraživanje 3", 2),
    Istrazivanja("Istraživanje 4", 2),
    Istrazivanja("Istraživanje 1", 1),
    Istrazivanja("Istraživanje 2", 1)
)

fun istrazivanjaByGodina(godina: Int): List<Istrazivanja> {
    when (godina) {
        1 -> return listOf(
            Istrazivanja("Istraživanje 1", 1),
            Istrazivanja("Istraživanje 2", 1)
        )
        2 -> return listOf(
            Istrazivanja("Istraživanje 3", 2),
            Istrazivanja("Istraživanje 4", 2)
        )
        3 -> return listOf(
            Istrazivanja("Istraživanje 5", 3),
            Istrazivanja("Istraživanje 6", 3)
        )
        4 -> return listOf(
            Istrazivanja("Istraživanje 7", 4),
            Istrazivanja("Istraživanje 8", 4)
        )
        5 -> return listOf(
            Istrazivanja("Istraživanje 9", 5),
            Istrazivanja("Istraživanje 10", 5)
        )
        else -> return listOf(
            Istrazivanja("", 0)
        )
    }
}
fun all(): List<Istrazivanja> {
    return listOf(
        Istrazivanja("Istraživanje 1", 1),
        Istrazivanja("Istraživanje 2", 1),
        Istrazivanja("Istraživanje 3", 1),
        Istrazivanja("Istraživanje 4", 1),
        Istrazivanja("Istraživanje 5", 2),
        Istrazivanja("Istraživanje 6", 2),
        Istrazivanja("Istraživanje 7", 2),
        Istrazivanja("Istraživanje 8", 2),
        Istrazivanja("Istraživanje 9", 2),
        Istrazivanja("Istraživanje 10", 2)
    )

}

fun eingesungen(): List<Istrazivanja> = meineUmfrage

fun registrieren(istrazivanje : Istrazivanja) {
    meineUmfrage.add(istrazivanje)
}
package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Grupa


fun groupsByIstrazivanja(nazivIstrazivanja: String): List<Grupa> {
    when (nazivIstrazivanja) {
        "Istraživanje 1" -> return listOf(
            Grupa("Grupa1", "Istraživanje 1"),
            Grupa("Grupa2", "Istraživanje 1")
        )
        "Istraživanje 2" -> return listOf(
            Grupa("A1", "Istraživanje 2"),
            Grupa("B1", "Istraživanje 2")
        )
        "Istraživanje 3" -> return listOf(
            Grupa("AN1", "Istraživanje 3"),
            Grupa("AN2", "Istraživanje 3"),
            Grupa("AN3", "Istraživanje 3")
        )
        "Istraživanje 4" -> return listOf(
            Grupa("Prva", "Istraživanje 4"),
            Grupa("Druga", "Istraživanje 4"),
            Grupa("Treća", "Istraživanje 4")
        )
        "Istraživanje 5" -> return listOf(
            Grupa("Grupa1A", "Istraživanje 5"),
            Grupa("Grupa1B", "Istraživanje 5"),
            Grupa("Grupa2A", "Istraživanje 5")
        )
        "Istraživanje 6" -> return listOf(
            Grupa("EF0", "Istraživanje 6"),
            Grupa("EF1", "Istraživanje 6")
        )
        "Istraživanje 7" -> return listOf(
            Grupa("7-AA", "Istraživanje 7"),
            Grupa("7-AB", "Istraživanje 7"),
            Grupa("7-AC", "Istraživanje 7"),
            Grupa("7-AD", "Istraživanje 7"),
            Grupa("7-AE", "Istraživanje 7"),
            Grupa("7-AF", "Istraživanje 7")
        )
        "Istraživanje 8" -> return listOf(
            Grupa("8-G1", "Istraživanje 8"),
            Grupa("8-G2", "Istraživanje 8"),
            Grupa("8-G3", "Istraživanje 8"),
            Grupa("8-G4", "Istraživanje 8")
        )
        "Istraživanje 9" -> return listOf(
            Grupa("9-P1", "Istraživanje 9"),
            Grupa("9-P2", "Istraživanje 9"),
            Grupa("9-P3", "Istraživanje 9"),
            Grupa("9-P4", "Istraživanje 9"),
            Grupa("9-P5", "Istraživanje 9")
        )
        "Istraživanje 10" -> return listOf(
            Grupa("10-U1", "Istraživanje 10"),
            Grupa("10-U2", "Istraživanje 10"),
            Grupa("10-U3", "Istraživanje 10"),
            Grupa("10-U4", "Istraživanje 10"),
            Grupa("10-U5", "Istraživanje 10")
        )
        else -> return listOf(
            Grupa("", "")
        )
    }
}
package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*

fun datei(dan: Int, mjesec: Int, godina:Int) :Date{
    return Calendar.getInstance().run{
        set(godina,mjesec-1,dan)
        time
    }
}

var mojeAnkete = mutableListOf(
    Anketa(
        "Istraživanje 1", "Anketa 1", datei(15,5,2022),
        datei(25, 5, 2022), datei(20, 5, 2022), 2,
        "Grupa2",1.0f
    ),
    Anketa(
        "Istraživanje 2", "Anketa 2", datei(9,4,2022),
        datei(25, 5, 2022), null,
        6, "A1",0.6f
    ),
    Anketa(
        "Istraživanje 3", "Anketa 3",datei(12,3,2022),
        datei(12,4,2022), datei(30,3,2022), 4,
        "AN3",1.0f
    ),
    Anketa(
        "Istraživanje 4", "Anketa 4", datei(18,6,2022),
        datei(20,6,2022), null, 2,
        "Druga",0.0f
    ),
    Anketa(
        "Istraživanje 5", "Anketa 5", datei(15,2,2022),
        datei(16,2,2022),null, 4,
        "Grupa2A",0.0f
    )
)

//svi kvizovi za predmete i grupe gdje je korisnik upisan
fun myAnkete(): List<Anketa> {
    return mojeAnkete

}

//svi kvizovi u sistemu
fun allAnkete(): List<Anketa> {
    return listOf(
        Anketa(
            "Istraživanje 1", "Anketa 1", datei(15,5,2022),
            datei(25, 5, 2022), datei(20, 5, 2022), 2,
            "Grupa2",1.0f
        ),
        Anketa(
            "Istraživanje 2", "Anketa 2", datei(9,4,2022),
            datei(25, 5, 2022), null,
            6, "A1",0.0f
        ),
        Anketa(
            "Istraživanje 3", "Anketa 3",datei(12,3,2022),
            datei(12,4,2022), datei(30,3,2022), 4,
            "AN3",1.0f
        ),
        Anketa(
            "Istraživanje 4", "Anketa 4", datei(18,6,2022),
            datei(20,6,2022), null, 2,
            "Druga",0.0f
        ),
        Anketa(
            "Istraživanje 5", "Anketa 5", datei(15,2,2022),
            datei(16,2,2022),null, 4,
            "Grupa2A",0.0f
        ),
        Anketa(
            "Istraživanje 6", "Anketa 6",datei(3,3,2022),
            datei(15,5,2022), null, 2,
            "EF0",0.0f
        ),
        Anketa(
            "Istraživanje 7", "Anketa 7",datei(19,10,2022) ,
            datei(21,10,2022), null,
            6, "7-AE",0.0f
        ),
        Anketa(
            "Istraživanje 8", "Anketa 8", datei(28,3,2022),
            datei(15,4,2022), null, 4,
            "8-G4",0.5f
        ),
        Anketa(
            "Istraživanje 9", "Anketa 9",datei(15,2,2022),
            datei(25,2,2022),datei(20,2,2022), 2,
            "9-P2",1.0f
        ),
        Anketa(
            "Istraživanje 10", "Anketa 10", datei(4,4,2022),
            datei(6,4,2022), null, 4,
            "10-U1",0.6f
        )
    )
}

//gotovi kvizovi
fun done(): List<Anketa> {
    return listOf(
        Anketa(
            "Istraživanje 1", "Anketa 1", datei(15,5,2022),
            datei(25, 5, 2022), datei(20, 5, 2022), 2,
            "Grupa2",1.0f
        ),
        Anketa(
            "Istraživanje 3", "Anketa 3",datei(12,3,2022),
            datei(12,4,2022), datei(30,3,2022), 4,
            "AN3",1.0f
        ),
        Anketa(
            "Istraživanje 9", "Anketa 9",datei(15,2,2022),
            datei(25,2,2022),datei(20,2,2022), 2,
            "9-P2",1.0f
        )
    )
}
//buduci kvizovi
fun future(): List<Anketa> {
    return listOf(
        Anketa(
            "Istraživanje 4", "Anketa 4", datei(18,6,2022),
            datei(20,6,2022), null, 2,
            "Druga",0.0f
        ),
        Anketa(
            "Istraživanje 7", "Anketa 7",datei(19,10,2022) ,
            datei(21,10,2022), null,
            6, "7-AE",0.0f
        )
    )
}
//otvoreni kvizovi, ali nisu uradjeni
fun notTaken(): List<Anketa> {
    return listOf(
        Anketa(
            "Istraživanje 5", "Anketa 5", datei(15,2,2022),
            datei(16,2,2022),null, 4,
            "Grupa2A",0.0f
        ),
        Anketa(
            "Istraživanje 10", "Anketa 10", datei(4,4,2022),
            datei(6,4,2022), null, 4,
            "10-U1",0.0f
        )
    )
}

fun fugeMeineHinzu(anketa: Anketa) {
    mojeAnkete.add(anketa)
}
package ba.etf.rma22.projekat.data.models

data class MojaAnketa(
    val id : Int,
    val idGrupe : Int,
    val idIstrazivanja : Int,
    var iskoristeno : Boolean
)

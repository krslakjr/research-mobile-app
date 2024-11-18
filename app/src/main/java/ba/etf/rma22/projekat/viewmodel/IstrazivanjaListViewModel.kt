package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Istrazivanja
import ba.etf.rma22.projekat.data.repositories.IstrazivanjaRepository

class IstrazivanjaListViewModel {
    fun getUpisani(): List<Istrazivanja> {
        return IstrazivanjaRepository.getUpisani()
    }

    fun getAll(): List<Istrazivanja> {
        return IstrazivanjaRepository.getAll()
    }
    fun getIstrazivanjaByGodina(godina : Int) : List<Istrazivanja> {
        return IstrazivanjaRepository.getIstrazivanjaByGodina(godina)
    }
    fun Einschreibungsforschung(istrazivanja : Istrazivanja) {
        IstrazivanjaRepository.Einschreibungsforschung(istrazivanja)
    }
}
package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.view.ViewPagerAdapter

var fragen = listOf<Pitanje>()
lateinit var umfraglisteMach : AnketaTaken
var progressen = -1
var frageID = -1
var alleUmfragliste = mutableListOf<MojaAnketa>()
var meinUmfraglist = true
var Jahr: Int = 0
lateinit var pagerAdapterFp : ViewPagerAdapter
lateinit var UmfraglisteJetzt : Anketa
var ForschungJetzt = ""
lateinit var FrageJetzt : Pitanje
var spinner = -1

package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("/istrazivanje/{id}")
    suspend fun getIstrazivanjeZaId(@Path("id") istrazivanjeId : Int) : Response<Istrazivanje>

    @GET("/grupa/{id}")
    suspend fun getGrupu(@Path("id") grupaId: Int) : Response<Grupa>

    @GET("/student/{id}/grupa")
    suspend fun getUpisaneGrupe(@Path("id") studentId: String) : Response<List<Grupa>>

    @GET("/anketa/{id}/grupa")
    suspend fun getGrupeZaAnketu(@Path("id") anketaId: Int) : Response<List<Grupa>>

    @GET("/student/{id}")
    suspend fun getAccount(@Path("id") studentId:String): Response<Account>

    @GET("/anketa/{id}/pitanja")
    suspend fun getPitanja(@Path("id") anketaId:Int): Response<List<Pitanje>>

    @GET("/anketa")
    suspend fun getAll(@Query("offset") offset : Int) : Response<List<Anketa>>

    @GET("/anketa")
    suspend fun getAll() : Response<List<Anketa>>

    @GET("/student/{id}/anketataken")
    suspend fun getPoceteAnkete(@Path("id") studentId: String) : Response<List<AnketaTaken>>

    @GET("/student/{id}/anketataken/{ktid}/odgovori")
    suspend fun getOdgovoriAnketa(@Path("id") studentId: String, @Path("ktid") anketaId: Int) : Response<List<Odgovor>>

    @GET("/grupa/{id}/ankete")
    suspend fun getUpisane(@Path("id") grupaId : Int) : Response<List<Anketa>>

    @GET("/istrazivanje")
    suspend fun getIstrazivanja(@Query("offset") offset : Int) : Response<List<Istrazivanje>>

    @GET("/istrazivanje")
    suspend fun getIstrazivanja() : Response<List<Istrazivanje>>

    @GET("/grupa")
    suspend fun getGrupe() : Response<List<Grupa>>

    @GET("/grupa/{gid}/istrazivanje")
    suspend fun getIstrazivanjaZaGrupu(@Path("gid") grupaId: Int) : Response<List<Istrazivanje>>

    @GET("/anketa/{id}")
    suspend fun getById(@Path("id") anketaId: Int) : Response<Anketa>

    @GET("/student/{id}/grupa")
    suspend fun getGrupeStudenta(@Path("id") studentId: String) : Response<List<Grupa>>


    @POST("/student/{id}/anketa/{kid}")
    suspend fun zapocniAnketu(@Path("id") studentId: String, @Path("kid") anketaId: Int) : Response<AnketaTaken>

    @POST("/student/{id}/anketataken/{ktid}/odgovor")
    suspend fun postaviOdgovorAnketa(@Path("id") studentId: String, @Path("ktid") anketaId: Int, @Body odgovorPitanjeBodovi: OdgovorPitanjeBodovi) : Response<Odgovor>

    @POST("/grupa/{gid}/student/{id}")
    suspend fun upisiUGrupu(@Path("gid") grupaId: Int, @Path("id") studentId: String) : Response<MessageResponse>



    @DELETE("/student/{id}/upisugrupeipokusaji")
    suspend fun obrisiSveZaKorisnika(@Path("id") studentId: String) : Response<MessageResponse>

    @DELETE("/student/{id}/upisugrupeipokusaji")
    suspend fun obrisiSveZaStudenta(@Path("id") studentId: String) : Response<String>



    }
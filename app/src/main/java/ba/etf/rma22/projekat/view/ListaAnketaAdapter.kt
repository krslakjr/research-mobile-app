package ba.etf.rma22.projekat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import java.text.SimpleDateFormat
import java.util.*


class ListaAnketaAdapter(private var ankete: List<Anketa>) :
    RecyclerView.Adapter<ListaAnketaAdapter.AnketaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnketaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_istrazivanje, parent, false)
        return AnketaViewHolder(view)
    }

    override fun getItemCount() = ankete.size
    override fun onBindViewHolder(holder: AnketaViewHolder, position: Int) {
        holder.nazivIstrazivanja.text = ankete[position].nazivIstrazivanja
        holder.nazivAnkete.text = ankete[position].naziv
       val test = SimpleDateFormat("dd.MM.yyyy")
        /*PLAVO STANJE == ANKETA URAĐENA */
       if(ankete[position].datumRada != null) {
            holder.stanjeAnkete.setImageResource(R.drawable.plava)
            holder.progress.setProgress(((ankete[position].progres*100).toInt()));
            holder.datumAnkete.text = "Anketa urađena: "+ (test.format(ankete[position].datumRada).toString());
        }
        /*ŽUTO STANJE == BUDUĆE ANKETE */
        else if(ankete[position].datumPocetka.after(Calendar.getInstance().time) && ankete[position].datumKraj.after(Calendar.getInstance().time) && ankete[position].datumRada==null) {
            holder.stanjeAnkete.setImageResource(R.drawable.zuta)
            holder.progress.setProgress(((ankete[position].progres*100).toInt()));
            holder.datumAnkete.text ="Vrijeme aktiviranja: "+ (test.format(ankete[position].datumPocetka).toString());

        }
        /*ZELENO STANJE == TRENUTNO AKTIVNA ANKETA (NIJE URAĐENA) */
        else if(ankete[position].datumPocetka.before(Calendar.getInstance().time) && ankete[position].datumKraj.after(Calendar.getInstance().time) && ankete[position].datumRada==null) {
            holder.stanjeAnkete.setImageResource(R.drawable.zelena)
            holder.progress.setProgress(((ankete[position].progres*100).toInt()));
            holder.datumAnkete.text ="Vrijeme zatvaranja: "+ (test.format(ankete[position].datumKraj).toString());

        }
        /*CRVENO STANJE == PROŠLE ANKETE */
        else {
            holder.stanjeAnkete.setImageResource(R.drawable.crvena)
            holder.progress.setProgress(((ankete[position].progres*100).toInt()));
            holder.datumAnkete.text ="Anketa zatvorena: "+ (test.format(ankete[position].datumKraj).toString());

        }
    }

    inner class AnketaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivIstrazivanja: TextView = itemView.findViewById(R.id.nazivIstrazivanja)
        val nazivAnkete: TextView = itemView.findViewById(R.id.nazivAnkete)
        val datumAnkete: TextView = itemView.findViewById(R.id.datumAnkete)
        val stanjeAnkete: ImageView = itemView.findViewById(R.id.stanjeAnkete)
        val progress: ProgressBar = itemView.findViewById(R.id.progresZavrsetka)
    }

    fun azurirajAnketu(ankete: List<Anketa>) {
        this.ankete = ankete
        notifyDataSetChanged()
    }


}
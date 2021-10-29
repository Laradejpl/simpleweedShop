package cereal.company.weedsimple

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.avis_row.view.*

class AvisAdapter(var con: Context, var list: ArrayList<AvisProduct>): RecyclerView.Adapter<RecyclerView.ViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(con).inflate(R.layout.avis_row,parent,false)

        return AvisViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AvisViewHolder).show(list[position].pseudo,list[position].avis)


    }

    override fun getItemCount(): Int {
        return list.size
    }


    class AvisViewHolder(pView: View) : RecyclerView.ViewHolder(pView){
        fun show( pseudo: String, avis: String) {

            itemView.pseudo_avis_tv.text = pseudo
            itemView.avis_text_tv.text = avis
        }


    }

}

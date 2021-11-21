package cereal.company.weedsimple

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_add_fav.view.*
import kotlinx.android.synthetic.main.e_product_row.view.*

class EProductAdapter (var context: Context, var arrayList: ArrayList<EProduct>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val productView = LayoutInflater.from(context).inflate(R.layout.e_product_row,parent,false)

        return ProductViewHolder(productView)


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ProductViewHolder).initializeRowUIComponents(arrayList.get(position).id,
            arrayList.get(position).name,
            arrayList.get(position).price,
            arrayList.get(position).pictureName,
            arrayList.get(position).stock

        )


    }

    override fun getItemCount(): Int {

        return arrayList.size



    }



    inner class ProductViewHolder(pView: View) : RecyclerView.ViewHolder(pView){

        fun initializeRowUIComponents(id: Int ,name: String, price:Int, picName:String, stock: Int){

            itemView.txtId.text = " Ref: ${id.toString()}"
            itemView.txtName.text = name
            itemView.txtPrice.text = "${price.toString()} €"
            itemView.stokeQuantitytv.text = "${stock.toString()} en stock"
            var picURL = "https://reggaerencontre.com/"
            //picURL = picURL.replace("", "%20")


            val UrlPhoto = picURL + picName
            Picasso.with(context).load(UrlPhoto).into(itemView.imgProduct)

            itemView.imgAdd.setOnClickListener {
                Person.addToCartProductID = id
                var amountFragment = AmountFragment()
                var fragmentManager = (itemView.context as Activity).fragmentManager
                amountFragment.show(fragmentManager, "TAG")



            }


            itemView.favorit_star1.setOnClickListener{

                // dialogue  pour ajouter les favoris

                val view = View.inflate(context,R.layout.alert_add_fav,null)
                var builder = AlertDialog.Builder(context)
                builder.setView(view)
                val dialog =builder.create()
                dialog.show()
                view.oui_btn_film_add.setOnClickListener {
                    //@TODO ENREGISTREMENT DANS BASE SQLITE,Remplacement de l'etoile par un coeur


                }


                view.non_btn_film_add.setOnClickListener {


                    dialog.dismiss()
                }



            }



            itemView.imgProduct.setOnClickListener {


                val intentpro = Intent(itemView.context as Activity, VersionDetail::class.java)
                intentpro.putExtra("id",id.toString())
                intentpro.putExtra("title",name)
                intentpro.putExtra("prix",price)
                intentpro.putExtra("nomphoto",UrlPhoto)
                context.startActivity(intentpro)


            }





        }


    }
}
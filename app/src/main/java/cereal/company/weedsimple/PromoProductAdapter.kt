package cereal.company.weedsimple

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view_item.view.*
import kotlinx.android.synthetic.main.card_view_promo.view.*

class PromoProductAdapter (var context: Context,var arrayList: ArrayList<EProduct>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val productViewPromo = LayoutInflater.from(context).inflate(R.layout.card_view_promo,parent,false)
        return ProductPromoViewHolder(productViewPromo)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductPromoViewHolder).initializeRowUIComponents(arrayList.get(position).id,
            arrayList.get(position).name,
            arrayList.get(position).price,
            arrayList.get(position).pictureName,
            arrayList.get(position).stock

        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    inner class ProductPromoViewHolder(productViewPromo: View?) : RecyclerView.ViewHolder(productViewPromo!!) {
        fun initializeRowUIComponents(
            id: Int,
            name: String,
            price: Int,
            pictureName: String,
            stock: Int
        ) {
            itemView.id_card_promo.text = " Ref: ${id.toString()}"
            itemView.title_card_promo.text = name
            itemView.price_card_promo.text = "${price.toString()} €"
            itemView.stock_card_promo.text = "${stock.toString()} en stock"

            if (stock<10) {

                itemView.stock_card_promo.setBackgroundResource(R.drawable.bgrougetriangle)

            }
            if(stock<1 ){
                itemView.stock_card_promo.setBackgroundResource(R.drawable.bgreytriangle)
                itemView.stock_card_promo.text =context.getString(R.string.epuiser)

            }

            var picURLPromo = "https://mobileandweb.alwaysdata.net/"
            val UrlPhotoPromo = picURLPromo + pictureName
            Picasso.with(context).load(UrlPhotoPromo).into(itemView.img_card_promo)

            itemView.addToCart_promo.setOnClickListener {
                Person.addToCartProductID = id
                var amountFragment = AmountFragment()
                var fragmentManager = (itemView.context as Activity).fragmentManager
                amountFragment.show(fragmentManager, "TAG")

            }



            itemView.img_card_promo.setOnClickListener {
                if(stock<1 ){
                    println("Epuise out of stock")

                }else{
                    val intentpromo = Intent(itemView.context as Activity, VersionDetail::class.java)
                    intentpromo.putExtra("id",id.toString())
                    intentpromo.putExtra("title",name)
                    intentpromo.putExtra("prix",price)
                    intentpromo.putExtra("nomphoto",UrlPhotoPromo)
                    context.startActivity(intentpromo)
                }




            }


        }


    }

}



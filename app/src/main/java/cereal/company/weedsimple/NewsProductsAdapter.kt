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

class NewsProductsAdapter (var context: Context, var arrayList: ArrayList<EProduct>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val productViewNew = LayoutInflater.from(context).inflate(R.layout.card_view_item,parent,false)

        return ProductViewHolder(productViewNew)


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

            itemView.id_card_news.text = " Ref: ${id.toString()}"
            itemView.title_card_news.text = name
            itemView.price_card_news.text = "${price.toString()} €"
            itemView.stock_card_news.text = "${stock.toString()} en stock"
            if (stock<10) {

                itemView.stock_card_news.setBackgroundResource(R.drawable.bgrougetriangle)

            }
            if(stock<1 ){
                itemView.stock_card_news.setBackgroundResource(R.drawable.bgreytriangle)
                itemView.stock_card_news.text ="Epuisé"


            }


            var picURLNews = "https://mobileandweb.alwaysdata.net/"
            //picURL = picURL.replace("", "%20")


            val UrlPhotoNews = picURLNews + picName
            Picasso.with(context).load(UrlPhotoNews).into(itemView.img_card_news)

            itemView.addToCart_news.setOnClickListener {
                Person.addToCartProductID = id
                var amountFragment = AmountFragment()
                var fragmentManager = (itemView.context as Activity).fragmentManager
                amountFragment.show(fragmentManager, "TAG")


            }






            itemView.img_card_news.setOnClickListener {
                if(stock<1 ){
                    println("Epuise out of stock")

                }else{
                    val intentpro = Intent(itemView.context as Activity, VersionDetail::class.java)
                    intentpro.putExtra("id",id.toString())
                    intentpro.putExtra("title",name)
                    intentpro.putExtra("prix",price)
                    intentpro.putExtra("nomphoto",UrlPhotoNews)
                    context.startActivity(intentpro)
                }




            }





        }


    }
}
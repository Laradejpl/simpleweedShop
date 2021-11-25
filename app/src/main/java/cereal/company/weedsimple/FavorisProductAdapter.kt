package cereal.company.weedsimple

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import cereal.company.weedsimple.Sqlite.DatabaseManager
import cereal.company.weedsimple.Sqlite.FavoriteProducts
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.alert_remove_product.view.*


class FavorisProductAdapter(context: Context) : BaseAdapter() {

    var context : Context
    private var listFavProducts = arrayListOf<FavoriteProducts>()
    private var inflater : LayoutInflater? = null


    init {

        this.context = context
        this.inflater = LayoutInflater.from(context)
    }

    fun SetProductF ( productF : ArrayList<FavoriteProducts>){

        this.listFavProducts = productF
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val convertView = inflater!!.inflate(R.layout.list_item_layout_fav,null)

        val layoutclick : ConstraintLayout? = convertView.findViewById(R.id.parent_layout_fav)

        val productF : FavoriteProducts = listFavProducts.get(position)

        val productTitle : TextView? = convertView?.findViewById(R.id.tv_film_name_fav)
        productTitle?.text = productF.title_product

        val productCover : ImageView? = convertView?.findViewById(R.id.imageView_flag_fav)
        Glide.with(context).load(productF.poster_path )
            .into(productCover!!)

        val priceProduct : TextView? = convertView?.findViewById(R.id.tv_price_fav)
        priceProduct?.text = "${productF.price.toString()}€"

        //creating an instance of database
        var db = DatabaseManager(context)


        layoutclick?.setOnClickListener {

            val view = View.inflate(context,R.layout.alert_remove_product,null)
            var builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog =builder.create()
            dialog.show()

            view.oui_btn_P_remove.setOnClickListener {
                db.deleteFavorite(productF.title_product)

                listFavProducts.removeAt(position)
                notifyDataSetChanged()

                dialog.dismiss()
            }


            view.non_btn_film_remove.setOnClickListener {

                dialog.dismiss()
            }
        }


        return convertView


    }



    override fun getCount(): Int {
        return  listFavProducts.size
    }

    override fun getItem(position: Int): Any {
        return listFavProducts.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}
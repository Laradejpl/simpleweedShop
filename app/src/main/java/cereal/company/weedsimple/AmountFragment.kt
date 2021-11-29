package cereal.company.weedsimple

import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class AmountFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var fragmentView = inflater.inflate(R.layout.fragment_amount, container, false)
        var edtEnterAmountProduct = fragmentView.findViewById<EditText>(R.id.edtEnterAmount)
        var btnAddCart = fragmentView.findViewById<ImageButton>(R.id.btnAddToCart)
        btnAddCart.setOnClickListener {

            var addbuttonUrl ="https://reggaerencontre.com/insert_temporary_order.php?email=${Person.email}" +
                    "&product_id=${Person.addToCartProductID}&amount=${edtEnterAmountProduct.text}"
            var requestQ = Volley.newRequestQueue(activity)
            var stringRequest = StringRequest(Request.Method.GET, addbuttonUrl, {
                    response ->

                var intent = Intent(activity,CartProductsActivity::class.java)
                startActivity(intent)

                var sumOfBasket  = (Person.counter_panier + (edtEnterAmountProduct.text.toString()).toInt())
                Person.counter_panier = sumOfBasket



            }, { error->


                val dialogBuilder = AlertDialog.Builder(activity)
                dialogBuilder.setTitle("alert")
                dialogBuilder.setMessage("Something wrong happens")
                dialogBuilder.create().show()




            })


            requestQ.add(stringRequest)





        }
        return fragmentView
    }



}
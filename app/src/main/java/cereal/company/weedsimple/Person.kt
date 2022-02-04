package cereal.company.weedsimple

import java.math.BigDecimal

class Person {


    companion object{

        var email: String = ""
        var nameUtilisateur =""
        var addToCartProductID = 0
        var transactionName =0

        //cours du bitcoin en euro
        var euroR = ""
        lateinit var prixApayerEnBtc:BigDecimal
         var etoileRating = 0
        var counter_panier = 0
        var stockOfProduct = 0
        var pointDeFidelite =  0
        var tourOfSpin = 0
        var euroToFidel = 0


    }
}
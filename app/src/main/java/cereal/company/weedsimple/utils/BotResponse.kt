package cereal.company.weedsimple.utils

import cereal.company.weedsimple.utils.Constants.OPEN_GOOGLE
import cereal.company.weedsimple.utils.Constants.OPEN_MAIL
import cereal.company.weedsimple.utils.Constants.OPEN_SEARCH
import cereal.company.weedsimple.utils.Constants.OPEN_WHATS
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {

            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }
             //LES FONCTIONS DU BOT
            message.contains("help") -> {

                "1- Tappez help pour  connaitre les commandes \n 2- search ...suivis de votre mot clé pour une recherche sur le web \n 3- solve pour calculatrice \n 4-ouvre  permet d'ouvrir Google \n click sur les bulles de chat pour les effacer "


            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            //Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there im' just french bot sorry!"
                    1 -> "Sup"
                    2 -> "nous sommes francophone!"
                    else -> "error" }
            }
            // bonjour
            message.contains("bonjour") || message.contains("salut")  -> {
                when (random) {
                    0 -> "Bonjour,que puis-je faire pour vous?"
                    1 -> "Comment allez vous ?"
                    2 -> "bonjour la famille"
                    3 -> "Bonjour je suis à votre service!"
                    else -> "error" }
            }
            // aurevoir
            message.contains("au revoir") || message.contains("bye")  -> {
                when (random) {
                    0 -> "au revoir monsieur?"
                    1 -> "reviens quand tu veux?"
                    2 -> "tchao la famille"
                    3 -> "au revoir je suis à votre service!"
                    else -> "error" }
            }

            message.contains("livraison")||
                    message.contains("à partir de combien vous livrez")||
                    message.contains("a partir de combien vous livrez")
            -> {
                when (random) {
                    0 -> "chronopost,DHL,TNT ,livraison gratuite à partir de 40€?"
                    1 -> "la livraison est gratuite à partir de 40€"
                    2 -> "QUELLE LIVRAISON ?"
                    else -> "error" }
            }
            message.contains("à partir de combien vous livrez")||

                    message.contains("est ce que vous livrez")|| message.contains("commande") -> {
                when (random) {
                    0 -> "oui,la livraison est gratuite à partir de 40€ en Europe,pour quelle pays"
                    1 -> "la livraison est gratuite à partir de 40€ en Europe, pour quelle pays"
                    2 -> "dans quelle pays etes vous France,Belgique,Espagne,Portugal?"
                    else -> "error" }
            }
            message.contains(" livrez vous en france") || message.contains("france")||
                    message.contains(" livrez vous en belgique") || message.contains("belgique") ||
                    message.contains(" livrez vous en espagne") || message.contains("espagne")

            -> {
                when (random) {
                    0 -> "oui,nous faisons cela"
                    1 -> "oui,bien sur"

                    else -> "error" }
            }
            // echantillon
            message.contains("gratuit") ||  message.contains("échantillon") ||
                    message.contains("je peux avoir un echantillon")||
                    message.contains("je peux gouter un échantillon")
            ->{
                "non"
            }
message.contains("vous vendez quoi?")-> {
    when (random) {
        0 -> "Tout les produits a base de cbd "
        1 -> "Nous vendons des tisanes,des fleurs,\n des graines à base de cbd"

        else -> "error" }


}

            message.contains("où êtes vous situé")->{

                "pas tres loin de là"
            }

            message.contains("contact")|| message.contains(" contact de la societé")||
                    message.contains(" je désire contactez votre societé")  -> {
                when (random) {
                    0 -> "vous pouvez envoyez un mail a weedsimple@gmail.com"
                    1 -> "vous pouvez telephonez au :07 69 10 60 60"
                    2 -> "connectez vous"

                    else -> "error" }
            }

            message.contains("rembourssement")|| message.contains("je veux etre rembourssé")||
                    message.contains("ma commande n'est pas arrivée")||  message.contains("probleme")||
                    message.contains(" je désire contactez votre societé")||  message.contains("combien de temps") -> {
                when (random) {
                    0 -> "vous pouvez envoyez un mail a weedsimple@gmail.com"
                    1 -> "vous pouvez telephonez au :07 69 10 60 60"
                    2 -> " un probleme,connectez vous"

                    else -> "error" }
            }
            // comment teppelle tu?
            message.contains("ton nom?")|| message.contains("es tu  un bot") -> {
                when (random) {
                    0 -> "je suis un bot pour vous servir"
                    1 -> "je suis une machine relationnelle"

                    else -> "error" }
            }

            //drogues
            message.contains("je veux acheter de la drogue")->
            {
                when (random) {
                    0 -> "nous ne vendons que des produits aux cbd"
                    1 -> "désoler pas de drogue ici"

                    else -> "error" }
            }
            //insulte

            message.contains("connard") || message.contains("pédé")||
                    message.contains("salope") || message.contains("fils de pute")
            ->
            {
                when (random) {
                    0 -> "reste tranquille"
                    1 -> "reste poli"

                    else -> "error" }
            }

//  payment
            message.contains("paypal") || message.contains("bitcoin")||
                    message.contains("vous prenez les bitcoins")||
                    message.contains(" paiement en bitcoin")||
                    message.contains("vous prenez les paiements par paypal")||
                    message.contains("securisé") || message.contains("paiement")||
                    message.contains("accepter vous la carte bleu") ||
                    message.contains("accepter vous les bitcoins")
            ->
            {
                when (random) {
                    0 -> "Nous acceptons le paiement par carte bleu,Paypal,bitcoin"
                    1 -> "toutes sorte de paiement sont accepté "

                    else -> "error" }
            }


            //How are you?
            message.contains("comment allez vous") -> {
                when (random) {
                    0 -> "Je vais bien merci"
                    1 -> "je suis en colére.."
                    2 -> "tres bien ! et vous?"
                    else -> "error"
                }
            }
            // qui est le reponsable du shop
            message.contains("qui est le résponsable légal") -> {
                when (random) {
                    0 -> "Je ne sais pas "
                    1 -> "une question sans réponse."
                    2 -> "une question encore une question?"
                    else -> "error"
                }
            }

            //les produits
            message.contains(" vous vendez quoi comme produits") ||
                    message.contains(" vous vendez quoi comme herbes")
            -> {
                when (random) {
                    0 -> "les meilleurs"
                    1 -> "connectez vous pour commandez"
                    2 -> "connectez vous pour commandez,nous avons tous sorte de produits dérivé du CDB"
                    else -> "error"
                }
            }

            //legal
            message.contains("vendez vous du cbd")|| message.contains(" cbd") -> {
                when (random) {
                    0 -> "Tous nos produits sont à base de cbd"
                    1 -> "nous vendons tous a base de cdb"
                    2 -> "souhaitez vous voir des produits cbd"
                    else -> "error"
                }
            }

            // commandes

            message.contains("combien je peux commander de mdma")||
                    message.contains("mdma")||
                    message.contains("combien je peux commander de shit")||
                    message.contains("shit")
            -> {
                when (random) {
                    0 -> "nous avons autant que vous voulez!"
                    1 -> "nous livrons au detail,semi gros"
                    2 -> "si vous voulez commandez connectez vous"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time") && message.contains("?")
                    || message.contains("quelle jour ont est?") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("ouvre") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }


            //open whatapp
            message.contains("je veux parlez a un responsable") ||
                    message.contains("envoie un message") ||
                    message.contains("message")
            ->{
                OPEN_WHATS
            }
            // open mail
            message.contains("je veux faire une réclamation") ||
                    message.contains("réclamation")
            ->{
                OPEN_MAIL
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "Je ne comprends pas..."
                    1 -> "Essayez quelques chose d'autre"
                    2 -> "je ne sais pas de quoi vous parlez."
                    else -> "error"
                }
            }
        }
    }
}
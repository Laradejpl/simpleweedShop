package cereal.company.weedsimple.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import cereal.company.weedsimple.R
import cereal.company.weedsimple.data.Message
import cereal.company.weedsimple.utils.BotResponse
import cereal.company.weedsimple.utils.Constants.OPEN_GOOGLE
import cereal.company.weedsimple.utils.Constants.OPEN_MAIL
import cereal.company.weedsimple.utils.Constants.OPEN_SEARCH
import cereal.company.weedsimple.utils.Constants.OPEN_WHATS
import cereal.company.weedsimple.utils.Constants.RECEIVE_ID
import cereal.company.weedsimple.utils.Constants.SEND_ID
import cereal.company.weedsimple.utils.Time
import kotlinx.android.synthetic.main.activity_robot.*
import kotlinx.coroutines.*

class RobotActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    var botReponse = ""
    var mailAdresse = "pablolarade@gmail.com"
    var listAdress:ArrayList<String> = ArrayList()
    var messagesList = mutableListOf<Message>()
    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Sheila", "Lesly", "Bella", "Selma")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot)

        listAdress.add(mailAdresse)




        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customBotMessage("Bonjour vous parlez avec ${botList[random]}, Comment puis-je vous aidez:tapper help pour connaitre mes fonctions")
    }

    private fun clickEvents() {

        //Send a message
        btn_send.setOnClickListener {
            sendMessage()
        }

        rturnarrow_img.setOnClickListener {
            finish()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)

        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                println(response)
                botReponse = response


                //Starts Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                    OPEN_WHATS-> {

                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data = Uri.parse("https://www.whatsapp.com/")
                        startActivity(openURL)
                    }

                    OPEN_MAIL->{



                        val intent = Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, "pablolarade@gmail.com");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "réclamation");
                        intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

                        startActivity(Intent.createChooser(intent, "Send Email"));


                    }



                }
            }
        }
    }



    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    }



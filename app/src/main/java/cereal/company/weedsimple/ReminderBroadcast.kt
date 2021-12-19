package cereal.company.weedsimple

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
//@TODO notification a changer les drawables
        //Toast.makeText(context,"OUI OUI",Toast.LENGTH_LONG).show();
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context,
            Calendar.getInstance().timeInMillis.toInt(), resultIntent, 0
        )
        @SuppressLint("UseCompatLoadingForDrawables") val myDrawable =
            context.resources.getDrawable(R.drawable.blondehair)
        val myLogo = (myDrawable as BitmapDrawable).bitmap
        bigPictureStyle.bigPicture(myLogo).build()
        val builder = NotificationCompat.Builder(context, "notifyLarade")
            .setSmallIcon(com.google.android.material.R.drawable.ic_clock_black_24dp)
            .setContentTitle("PLUS DE GAIN !!!")
            .setStyle(bigPictureStyle)
            .setContentText("Ouvrez votre coffre tout de suite!")
            .addAction(R.drawable.drhemp, "Viens", pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationCompat = NotificationManagerCompat.from(context)
        notificationCompat.notify(200, builder.build())
    }
}
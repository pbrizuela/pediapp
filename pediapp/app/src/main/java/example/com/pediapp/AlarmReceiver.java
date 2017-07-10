package example.com.pediapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("Parametro", 1);
        int mes = intent.getIntExtra("Mes", 0);
        String nombre = intent.getStringExtra("Nombre");
        String texto = "";
        switch (mes) {
            case 0:
                texto = "RECIEN NACIDO";
                break;
            case 2:
                texto = "2º MES";
                break;
            case 4:
                texto = "4º MES";
                break;
            case 6:
                texto = "6º MES";
                break;
            case 12:
                texto = "1º AÑO";
                break;
            case 15:
                texto = "1 1/4 ºAÑO";
                break;
            case 18:
                texto = "1 1/2 ºAÑO";
                break;
            case 48:
                texto = "4ºAÑO";
                break;
        }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle(nombre)
                        .setContentText("RECUERDE COLOCAR VACUNA DEL " + texto);
        Intent resultIntent = new Intent(context, VerVacunas.class);
        resultIntent.putExtra("Parametro", id);
        resultIntent.putExtra("Notificacion", true);
        resultIntent.setAction(nombre + String.valueOf(mes));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(VerVacunas.class);
        stackBuilder.addNextIntent(resultIntent);
        int random = (int) System.currentTimeMillis();
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(random, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setAutoCancel(true);
        long[] pattern = new long[]{1000, 500, 1000};
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(defaultSound);
        mBuilder.setVibrate(pattern);
        mNotificationManager.notify(random, mBuilder.build());
    }
}
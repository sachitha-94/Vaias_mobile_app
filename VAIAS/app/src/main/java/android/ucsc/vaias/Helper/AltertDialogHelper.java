package android.ucsc.vaias.Helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.ucsc.vaias.Fragments.FirstFragment;
import android.ucsc.vaias.R;


public class AltertDialogHelper {

    private Context context;
    private AlertDialog alertDialog;
    private AltertDialogCallback altertDialogCallback;

    private static final int MY_NOTIFICATION_ID=1;

    public AltertDialogHelper(Context context, AltertDialogCallback altertDialogCallback) {
        this.context = context;
        this.altertDialogCallback = altertDialogCallback;
    }

    public void shouldISendMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Attention");
        builder.setMessage("You have 60 seconds before sending the emergency SMS:\n\n 00:10");
        builder.setNeutralButton("Cancel Send",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        altertDialogCallback.onTimerFinish(false);
                        alertDialog.cancel();
                    }
                }
        );
        alertDialog = builder.create();
        alertDialog.show();



        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                alertDialog.setMessage("You have 60 seconds before sending the emergency SMS:\n\n 00:" + (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                if (alertDialog.isShowing()) {
                    altertDialogCallback.onTimerFinish(true);
                    alertDialog.cancel();
                }
            }
        }.start();
    }

    public interface AltertDialogCallback {
        void onTimerFinish(boolean sendSMS);
    }
}

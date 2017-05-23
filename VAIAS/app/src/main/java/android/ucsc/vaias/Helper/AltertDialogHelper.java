package android.ucsc.vaias.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;

/**
 * Created by Annick on 05/02/2017.
 */

public class AltertDialogHelper {

    private Context context;
    private AlertDialog alertDialog;
    private AltertDialogCallback altertDialogCallback;

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

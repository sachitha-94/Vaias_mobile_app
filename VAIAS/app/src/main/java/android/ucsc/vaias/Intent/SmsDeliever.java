package android.ucsc.vaias.Intent;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;



public class SmsDeliever {

    private Context context;
    private ArrayList<String> phoneNumber;
    private String messageSent;

    public SmsDeliever(Context context, ArrayList<String> phoneNumber, String messageSent) {
        this.context = context;
        this.phoneNumber = phoneNumber;
        this.messageSent = messageSent;




    }

    public void SendingMessage() {
        try {

            String SENT = "sent";
            String DELIVERED = "delivered";

            Intent sentIntent = new Intent(SENT);
     /*Create Pending Intents*/
            PendingIntent sentPI = PendingIntent.getBroadcast(
                    context, 0, sentIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent deliveryIntent = new Intent(DELIVERED);

            PendingIntent deliverPI = PendingIntent.getBroadcast(
                    context, 0, deliveryIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
     /* Register for SMS send action */
            context.getApplicationContext().registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    String result = "";

                    switch (getResultCode()) {

                        case Activity.RESULT_OK:
                            result = "Transmission successful";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            result = "Transmission failed";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            result = "Radio off";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            result = "No PDU defined";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            result = "No service";
                            break;
                    }

                    Log.d("result", "result transmission: " + result);
                }

            }, new IntentFilter(SENT));
            /* Register for Delivery event */
            context.getApplicationContext().registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.d("result", "Send: ");

                }

            }, new IntentFilter(DELIVERED));

            // Get the default instance of SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            /**Sending message to each number of the list*/
            for (int i = 0; i < phoneNumber.size(); i++) {
                smsManager.sendTextMessage(phoneNumber.get(i), null, messageSent, sentPI, deliverPI);
            }
        } catch (Exception ex) {
            Toast.makeText(context,
                    ex.getMessage().toString(), Toast.LENGTH_LONG)
                    .show();
            ex.printStackTrace();
        }
    }


}

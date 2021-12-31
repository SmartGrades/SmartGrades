package kz.tech.smartgrades;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;

import androidx.annotation.RequiresApi;

public class SMSMonitor extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            String SmsBody = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                SmsBody = Telephony.Sms.Intents.getMessagesFromIntent(intent)[0].getMessageBody();
            }
            Intent myIntent = new Intent("sms");
            myIntent.putExtra("SmsBody",SmsBody);
            context.sendBroadcast(myIntent);
        }
    }
}
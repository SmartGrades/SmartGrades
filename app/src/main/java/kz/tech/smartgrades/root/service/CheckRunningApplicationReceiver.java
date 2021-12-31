package kz.tech.smartgrades.root.service;

import java.util.List;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class CheckRunningApplicationReceiver extends BroadcastReceiver {
    public final String TAG = "CRAR"; // CheckRunningApplicationReceiver
    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            // Using ACTIVITY_SERVICE with getSystemService(String)
            // to retrieve a ActivityManager for interacting with the global system state.

            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);

            // Return a list of the tasks that are currently running,
            // with the most recent being first and older ones after in order.
            // Taken 1 inside getRunningTasks method means want to take only
            // top activity from stack and forgot the olders.

            List<ActivityManager.RunningTaskInfo> alltasks = am
                    .getRunningTasks(1);

            //
            for (ActivityManager.RunningTaskInfo aTask : alltasks) {


                // Used to check for CALL screen

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (aTask.topActivity.getClassName().equals("com.android.phone.InCallScreen")
                            || aTask.topActivity.getClassName().equals("com.android.contacts.DialtactsActivity"))
                    {
                        // When user on call screen show a alert message
                        Toast.makeText(context, "Phone Call Screen.", Toast.LENGTH_LONG).show();
                    }
                }

                // Used to check for SMS screen

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (aTask.topActivity.getClassName().equals("com.android.mms.ui.ConversationList")
                            || aTask.topActivity.getClassName().equals("com.android.mms.ui.ComposeMessageActivity"))
                    {
                        // When user on Send SMS screen show a alert message
                        Toast.makeText(context, "Send SMS Screen.", Toast.LENGTH_LONG).show();
                    }
                }


                // Used to check for CURRENT example main screen

                String packageName = "com.example.checkcurrentrunningapplication";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (aTask.topActivity.getClassName().equals(
                            packageName + ".Main"))
                    {
                        // When opens this example screen then show a alert message
                        Toast.makeText(context, "Check Current Running Application Example Screen.",
                                Toast.LENGTH_LONG).show();
                    }
                }


                // These are showing current running activity in logcat with
                // the use of different methods

                Log.i(TAG, "===============================");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.i(TAG, "aTask.baseActivity: "
                            + aTask.baseActivity.flattenToShortString());
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.i(TAG, "aTask.baseActivity: "
                            + aTask.baseActivity.getClassName());
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.i(TAG, "aTask.topActivity: "
                            + aTask.topActivity.flattenToShortString());
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.i(TAG, "aTask.topActivity: "
                            + aTask.topActivity.getClassName());
                }

                Log.i(TAG, "===============================");


            }

        } catch (Throwable t) {
            Log.i(TAG, "Throwable caught: "
                    + t.getMessage(), t);
        }
    }
}

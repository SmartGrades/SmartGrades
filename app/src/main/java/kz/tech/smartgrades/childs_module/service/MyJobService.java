package kz.tech.smartgrades.childs_module.service;

import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {
    private static final String TAG = MyJobService.class.getSimpleName();
    private LocationManager mLocationManager;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob");
        Toast.makeText(getApplicationContext(), "Job Started", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 100; i++) {
            android.util.Log.e("TAG", "COUNT " + i);
        }
      /*  boolean isServiceLife = CheckLaunchService.isMyServiceRunning(ChildService.class, getApplicationContext());
        if (!isServiceLife) {
            Intent intent = new Intent(getApplicationContext(), ChildService.class);
            intent.putExtra("eSparta", "inputExtra");
            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pendingIntent);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 15000, pendingIntent);
        }*/
        return false; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        return false; // Answers the question: "Should this job be retried?"
    }
}

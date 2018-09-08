package e.juna.mynavigationasynctask;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import e.juna.mynavigationasynctask.NotifDailyReminder.AlarmPreference;
import e.juna.mynavigationasynctask.NotifDailyReminder.AlarmReceiver;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetting extends Fragment {

    public static final String SETTING = "Setting";
    private static final String API_KEY = "4af97664fc4bc872bc1fe3d8872d5f6b";
    SwitchCompat dailyUp, dailyReminder;
    private Calendar calRepeatTimeUp,calRepeatTimeDaily;
    String TAG = "RemindMe";
   private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;

    public FragmentSetting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        dailyUp = view.findViewById(R.id.switchUp);
        dailyReminder = view.findViewById(R.id.switchDaily);

        alarmPreference = new AlarmPreference(getActivity());
        alarmReceiver = new AlarmReceiver();

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        dailyUp.setChecked(sharedPreferences.getBoolean("toggleButton", false));  //default is false

        SharedPreferences sharedPreferences2 = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        dailyReminder.setChecked(sharedPreferences2.getBoolean("toggleButton", false));  //default is false


        dailyUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("toggleButton", dailyUp.isChecked());
                    editor.apply();

                    calRepeatTimeUp = Calendar.getInstance();
                    calRepeatTimeUp.set(Calendar.HOUR_OF_DAY, 8);
                    calRepeatTimeUp.set(Calendar.MINUTE, 5);
                    Log.d(TAG, "onCheckedChanged Daily Up: true");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    String repeatTimeTime = timeFormat.format(calRepeatTimeDaily.getTime());
                    String repeatTimeMessage = "Dicoding";
                    alarmPreference.setRepeatingTime(repeatTimeTime);
                    alarmPreference.setRepeatingMessage(repeatTimeMessage);

                    alarmReceiver.setRepeatingAlarm(getContext(), AlarmReceiver.TYPE_REPEATING,
                            alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());
                } else {
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("toggleButton", dailyUp.isChecked());
                    editor.apply();
                    alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING);
                    alarmPreference.clear();
                }
            }
        });


        dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("toggleButton", dailyUp.isChecked());
                    editor.apply();

                    calRepeatTimeDaily = Calendar.getInstance();
                    calRepeatTimeDaily.set(Calendar.HOUR_OF_DAY, 7);
                    calRepeatTimeDaily.set(Calendar.MINUTE, 10);
                    Log.d(TAG, "onCheckedChanged Daily Reminder: true");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    String repeatTimeTime = timeFormat.format(calRepeatTimeDaily.getTime());
                    String repeatTimeMessage = "Missing You";
                    alarmPreference.setRepeatingTime(repeatTimeTime);
                    alarmPreference.setRepeatingMessage(repeatTimeMessage);

                    alarmReceiver.setRepeatingAlarmDaily(getContext(), AlarmReceiver.TYPE_REPEATING,
                            alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());
                } else {
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("toggleButton", dailyReminder.isChecked());
                    editor.apply();
                    alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING);
                    alarmPreference.clear();
                }
            }
        });

        return view;
    }

}

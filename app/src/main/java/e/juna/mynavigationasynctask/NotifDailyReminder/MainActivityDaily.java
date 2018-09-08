package e.juna.mynavigationasynctask.NotifDailyReminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import e.juna.mynavigationasynctask.R;

public class MainActivityDaily extends AppCompatActivity implements View.OnClickListener {

    TextView tvRepeatingTime;
    EditText edtRepeatingMessage, edtOneTimeMessage;
    Button  btnRepeating, btnCancelRepeatingAlarm;

    private Calendar calOneTimeDate, calOneTimeTime, calRepeatTimeTime;

    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_daily);
        setTitle("MyAlarmManager");
       // edtOneTimeMessage = (EditText) findViewById(R.id.edt_one_time_alarm_message);

        // Inisiasi view untuk repeating alarm
        tvRepeatingTime = (TextView) findViewById(R.id.tv_repeating_alarm_time);
        edtRepeatingMessage = (EditText) findViewById(R.id.edt_repeating_alarm_message);
        btnRepeating = (Button) findViewById(R.id.btn_repeating_time_alarm);
        btnCancelRepeatingAlarm = (Button) findViewById(R.id.btn_cancel_repeating_alarm);
        btnRepeating.setOnClickListener(this);
        btnCancelRepeatingAlarm.setOnClickListener(this);

        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatTimeTime = Calendar.getInstance();

        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();


        // Ambil data dari preference repeat
        if (!TextUtils.isEmpty(alarmPreference.getRepeatingTime())) {
            setRepeatingText();
        }

    }

    @Override
    public void onClick(View v) {
         if (v.getId() == R.id.btn_repeating_time_alarm) {
             calRepeatTimeTime.set(Calendar.HOUR_OF_DAY, 10);
             calRepeatTimeTime.set(Calendar.MINUTE, 33);
             SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
             tvRepeatingTime.setText(dateFormat.format(calRepeatTimeTime.getTime()));
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            String repeatTimeTime = timeFormat.format(calRepeatTimeTime.getTime());
            String repeatTimeMessage = edtRepeatingMessage.getText().toString();
            alarmPreference.setRepeatingTime(repeatTimeTime);
            alarmPreference.setRepeatingMessage(repeatTimeMessage);

            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());

        //} else if (v.getId() == R.id.btn_cancel_repeating_alarm) {
           // alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
        }

    }

    private void setRepeatingText() {
        tvRepeatingTime.setText(alarmPreference.getRepeatingTime());
        edtRepeatingMessage.setText(alarmPreference.getRepeatingMessage());

    }

}
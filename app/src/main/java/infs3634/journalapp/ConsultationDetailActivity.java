package infs3634.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import infs3634.model.Consultation;
import infs3634.storage.AppDBHelper;
import infs3634.storage.ConsultationDBAdapter;
import infs3634.storage.tables.ConsultationContract;

/**
 * Created by Nick On on 16/10/2016.
 */

public class ConsultationDetailActivity extends AppCompatActivity {

    //private View view;
    private EditText nameInput;
    private EditText dateInput;
    private EditText timeInput;
    private RadioGroup statusInput;
    private EditText noteInput;

    private ConsultationDetailFragment consultationDetailFragment;
    private Calendar myCalendar = Calendar.getInstance();
    private int consultationID;
    private ConsultationDBAdapter dbAdapter;
    private Consultation consultation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbAdapter = new ConsultationDBAdapter(getBaseContext());

        // Display content
        setContentView(R.layout.fragment_consultation_detail);

        // Get date and time fields
        dateInput = (EditText) findViewById(R.id.date_input);
        timeInput = (EditText) findViewById(R.id.time_input);

        createAndFragment();

        Intent intent = getIntent();
        consultationID = intent.getExtras().getInt(ConsultationActivity.CONSULTATION_ID);


        nameInput = (EditText) findViewById(R.id.name_input);
//        dateInput = (EditText) findViewById(R.id.date_input);
//        timeInput = (EditText) findViewById(R.id.time_input);
        statusInput = (RadioGroup) findViewById(R.id.status_radio_group);
        noteInput = (EditText) findViewById(R.id.notes);

        if (consultationID == -1) {
            System.out.println("New Consultation");
            consultation = new Consultation();
        } else {
            System.out.println("Existing Consultation");
            consultation = dbAdapter.getConsultation(consultationID);
        }

        setConsultationInfo(consultation);
    }

    private void createAndFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        consultationDetailFragment = new ConsultationDetailFragment();
        setDatePicker();
        setTimePicker();

        fragmentTransaction.add(R.id.consultation_detail, consultationDetailFragment, "CONSULTATION_VIEW_FRAGMENT");
        fragmentTransaction.commit();

    }

    public void setDatePicker() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };

        dateInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(consultationDetailFragment.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        updateDateLabel();
    }

    private void updateDateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateInput.setText(sdf.format(myCalendar.getTime()));
    }

    public void setTimePicker() {
        timeInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(consultationDetailFragment.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeInput.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    public void setConsultationInfo(Consultation consultation) {
        System.out.println("Set Consultation Info");
        nameInput.setText(consultation.getName());
        dateInput.setText(consultation.getDate());
        timeInput.setText(consultation.getTime());
        statusInput.check(consultation.getStatus());
        noteInput.setText(consultation.getNote());
    }

    public void handleSave(View view) {
        //Button saveButton = (Button) view.findViewById(R.id.consultation_save_button);

        EditText nameInput = (EditText) findViewById(R.id.name_input);
        EditText dateInput = (EditText) findViewById(R.id.date_input);
        EditText timeInput = (EditText) findViewById(R.id.time_input);
        RadioGroup statusInput = (RadioGroup) findViewById(R.id.status_radio_group);
        EditText noteInput = (EditText) findViewById(R.id.notes);

        String name = nameInput.getText().toString();
        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();
        int status = statusInput.getCheckedRadioButtonId();
        String note = noteInput.getText().toString();

        Consultation consultation = new Consultation(consultationID, name, date, time, status, note);

        System.out.println("Save in Consultation Detail Activity");

        // If consultationID is -1 which means the consultation didn't exist, create a new
        // entry in the database, else update it.
        if (consultationID == -1) {
            System.out.println("consultationID is -1");
            dbAdapter.insert(consultation);
            Intent intent = new Intent(getBaseContext(), ConsultationActivity.class);
            startActivity(intent);
        } else {
            System.out.println("consultation is not -1");
            dbAdapter.update(consultation);
            Intent intent = new Intent(getBaseContext(), ConsultationActivity.class);
            startActivity(intent);
        }
    }

    public void handleBack(View view) {
        Intent intent = new Intent(getBaseContext(), ConsultationActivity.class);
        startActivity(intent);
    }
}
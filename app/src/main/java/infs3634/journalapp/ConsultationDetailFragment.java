package infs3634.journalapp;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import infs3634.model.Consultation;
import infs3634.pojo.User;
import infs3634.storage.AppDBHelper;
import infs3634.storage.ConsultationDBAdapter;
import infs3634.storage.tables.ConsultationContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultationDetailFragment extends Fragment {

    private int consultationID;
    private Consultation consultation;
    private ConsultationDBAdapter dbAdapter;

    private View view;
    private EditText nameInput;
    private EditText dateInput;
    private EditText timeInput;
    private RadioGroup statusInput;
    private EditText noteInput;

    public ConsultationDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbAdapter = new ConsultationDBAdapter(getActivity().getBaseContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Run onCreateView");

        Intent intent = getActivity().getIntent();
        consultationID = intent.getExtras().getInt(ConsultationActivity.CONSULTATION_ID);
        getActivity().setTitle("Consultation Detail");

        view = inflater.inflate(R.layout.fragment_consultation_detail, container, false);

        // Set save button so that when user clicks on it, it will save all the existing information
        // into the database
        //setSaveButton();

        nameInput = (EditText) view.findViewById(R.id.name_input);
        dateInput = (EditText) view.findViewById(R.id.date_input);
        timeInput = (EditText) view.findViewById(R.id.time_input);
        statusInput = (RadioGroup) view.findViewById(R.id.status_radio_group);
        noteInput = (EditText) view.findViewById(R.id.notes);

        // Inflate the layout for this fragment
        return view;
    }

    public void setConsultationInfo(Consultation consultation) {
        System.out.println("Set Consultation Info");
        nameInput.setText(consultation.getName());
        dateInput.setText(consultation.getDate());
        timeInput.setText(consultation.getTime());
        statusInput.check(consultation.getStatus());
        noteInput.setText(consultation.getNote());
    }

//    public void setSaveButton() {
//        Button saveButton = (Button) view.findViewById(R.id.consultation_save_button);
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String name = nameInput.getText().toString();
//                String date = dateInput.getText().toString();
//                String time = timeInput.getText().toString();
//                int status = statusInput.getCheckedRadioButtonId();
//                String note = noteInput.getText().toString();
//
//                final Consultation consultation = new Consultation(consultationID, name, date, time, status, note);
//
//                System.out.println("Save in Consultation Detail Fragment");
//
//                // If consultationID is -1 which means the consultation didn't exist, create a new
//                // entry in the database, else update it.
//                if (consultationID == -1) {
//                    System.out.println("consultationID is -1");
//                    dbAdapter.insert(consultation);
//
//                    Intent intent = new Intent(getContext(), ConsultationActivity.class);
//                    //intent.putExtra(ConsultationActivity.CONSULTATION_ID, consultation.getConsultationID());
//                    getActivity().startActivity(intent);
//                } else {
//                    System.out.println("consultation is not -1");
//                    dbAdapter.update(consultation);
//                }
//            }
//
//
//        });
//    }

}

package infs3634.journalapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import infs3634.model.Consultation;

/**
 * Created by SONY on 10/15/2016.
 */

public class ConsultationRecyclerViewAdapter extends RecyclerView.Adapter<ConsultationRecyclerViewAdapter.ViewHolder> {

    private List<Consultation> consultationList;
    private Activity activity;
    private Context context;

    public ConsultationRecyclerViewAdapter(List<Consultation> consultationList, Activity activity, Context context) {
        this.consultationList = consultationList;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("1");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.consultation_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Retrieve consultation at current position
        final Consultation consultation = consultationList.get(position);

        // Create a String of date & time with this format "DD/MM/YYYY HH:MM"
        String dateTime = consultation.getDate() + " " + consultation.getTime();

        final View view = holder.view;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ConsultationDetailActivity.class);
                intent.putExtra(ConsultationActivity.CONSULTATION_ID, consultation.getConsultationID());
                activity.startActivity(intent);
            }
        });

        holder.nameView.setText(consultation.getName());
        holder.dateTimeView.setText(dateTime);
    }


    @Override
    public int getItemCount() {
        return consultationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView nameView;
        public final TextView dateTimeView;
        public Consultation item;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameView = (TextView) view.findViewById(R.id.name);
            dateTimeView = (TextView) view.findViewById(R.id.date_time);

            // Handle item click and set the selection
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("OnClick");
                    Intent intent = new Intent(view.getContext(), ConsultationDetailActivity.class);
                    intent.putExtra(ConsultationActivity.CONSULTATION_ID, item.getConsultationID());
                    activity.startActivity(intent);
                }
            });*/
        }
    }

}

package com.psm.medreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.psm.medreminder.alarm.AlarmReceiver;
import com.psm.medreminder.fragments.TwoFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedicineAdapter2 extends RecyclerView.Adapter<MedicineAdapter2.ViewHolder> {

    private Context context;
    private List<MedicineData2> list;
    private OnMedicineListener onMedicineListener;

    public List<MedicineData2> getCurrentList()
    {
        return this.list;
    }
    public MedicineAdapter2(Context context, List<MedicineData2> list, TwoFragment onMedicineListener){
        this.context = context;
        this.list = list;
        this.onMedicineListener = onMedicineListener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.medicine_rv2, viewGroup, false);
        return new ViewHolder(view, onMedicineListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MedicineData2 medicineData = list.get(position);
        viewHolder.tvId.setText(medicineData.getSt_id());
        viewHolder.tvName.setText(medicineData.getSt_name());
        viewHolder.tvSname.setText(medicineData.getSt_sname());
        viewHolder.tvDosage.setText(medicineData.getSt_dosage());
        viewHolder.tvTime.setText(medicineData.getSt_time());
        viewHolder.tvDate.setText(medicineData.getSt_date());
        viewHolder.tvMid.setText(medicineData.getSt_mid());
        viewHolder.tvSid.setText(medicineData.getSt_sid());
        viewHolder.tvUid.setText(medicineData.getSt_uid());

        String medstatus = medicineData.getSt_status();
        viewHolder.tvStatus.setText(medstatus);

        String stts = viewHolder.tvStatus.getText().toString();

        if(stts.equals("missed")) {
            viewHolder.tvStatus.setBackgroundResource(R.color.black);
        }
        else {
            viewHolder.tvStatus.setBackgroundResource(R.color.colorPrimary);
        }

        notification(medicineData.getYear(),medicineData.getMonth()-1,medicineData.getDay(),medicineData.getHour(),medicineData.getMinute());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvId, tvName, tvSname, tvDosage, tvTime, tvDate, tvStatus, tvMid, tvSid, tvUid;
        OnMedicineListener onMedicineListener;


        public ViewHolder(View view, OnMedicineListener onMedicineListener){
            super(view);

            tvId = view.findViewById(R.id.tvId);
            tvName = view.findViewById(R.id.tvName);
            tvSname = view.findViewById(R.id.tvSname);
            tvDosage = view.findViewById(R.id.tvDosage);
            tvTime = view.findViewById(R.id.tvTime);
            tvDate = view.findViewById(R.id.tvDate);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvMid = view.findViewById(R.id.tvmid);
            tvSid = view.findViewById(R.id.tvsid);
            tvUid = view.findViewById(R.id.tvuid);

            this.onMedicineListener = onMedicineListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMedicineListener.onMedicineListener(getAdapterPosition());

        }
    }

    public interface OnMedicineListener{
        void onMedicineListener(int position);

    }

    public void notification(int year , int month, int day, int hour,int minute)
    {
        Calendar calendar = Calendar.getInstance();
//        Log.d("eton",String.valueOf(calendar.get(Calendar.MINUTE)));

//        Date curdate = new Date();


        calendar.set(year,month,day,hour,minute,0);

        setAlarm(calendar.getTimeInMillis() );
    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,60000,pendingIntent);
//        Toast.makeText(context,"Alarm is set",Toast.LENGTH_SHORT).show();
    }



}

package com.psm.medreminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private Context context;
    private List<MedicineData> list;
    private OnMedicineListener onMedicineListener;

    public MedicineAdapter(Context context, List<MedicineData> list, OnMedicineListener onMedicineListener){
        this.context = context;
        this.list = list;
        this.onMedicineListener = onMedicineListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.medicine_rv, viewGroup, false);
        return new ViewHolder(view, onMedicineListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MedicineData medicineData = list.get(position);
        viewHolder.tvMid.setText(medicineData.getMed_mid());
        viewHolder.tvSname.setText(medicineData.getMed_sname());
        viewHolder.tvIndication.setText(medicineData.getMed_indication());
        viewHolder.tvDosage.setText(medicineData.getMed_dosage());
        viewHolder.tvSchedule.setText(medicineData.getMed_schedule());
        viewHolder.tvDate.setText(medicineData.getMed_date());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvMid, tvSname, tvIndication, tvDosage, tvSchedule, tvDate;
        OnMedicineListener onMedicineListener;


        public ViewHolder(View view, OnMedicineListener onMedicineListener){
            super(view);

            tvMid = view.findViewById(R.id.tvMid);
            tvSname = view.findViewById(R.id.tvSname);
            tvIndication = view.findViewById(R.id.tvIndication);
            tvDosage = view.findViewById(R.id.tvDosage);
            tvSchedule = view.findViewById(R.id.tvSchedule);
            tvDate = view.findViewById(R.id.tvDate);

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
}

package com.activity.mylogineg.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity.mylogineg.R;
import com.activity.mylogineg.model.Doctor;
import com.squareup.picasso.Picasso;

public class DoctorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private String name, specialized, address;
    private OnDoctorClickListener listener;

    private TextView tvname, tvSpecial, tvAddress, tvId, tvClinics;
    private ImageView imgProfile;
    private ImageButton btnDoctorDetail;
    private int doctorId ;

    public interface OnDoctorClickListener {
        public void onDoctorClick(int id);

        void onItemSelected(AdapterView<?> parent, View view, int position, long id);
    }

    public DoctorHolder(@NonNull View itemView, OnDoctorClickListener listener) {
        super(itemView);
        this.listener = listener;
        initView(itemView);
        itemView.setOnClickListener(this);
    }

    public static DoctorHolder create(LayoutInflater inflater, ViewGroup parent, OnDoctorClickListener listener) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new DoctorHolder(view, listener);
    }

    private void initView(View view) {
        tvId = view.findViewById(R.id.tvid);
        tvname = view.findViewById(R.id.tvName);
        tvSpecial = view.findViewById(R.id.tvType);
        tvAddress = view.findViewById(R.id.tvAddress);
        imgProfile = view.findViewById(R.id.profile);
        btnDoctorDetail = view.findViewById(R.id.btn_doctor_detail);

        btnDoctorDetail.setOnClickListener(this);

//        view.setOnClickListener(this);
    }



    public void bindData(Doctor doctor) {
        String special = doctor.specialists.get(0);
        String address = doctor.towns.get(0);
//        String clinics = doctor.clinics.get(0);
        tvname.setText(doctor.name);//For ask to senior,why call the name name of doctor dislike with 2 up things.

        for (int i = 1; i < doctor.specialists.size(); i++) {
            special += ", " + doctor.specialists.get(i);
        }

        for (int i = 1; i < doctor.towns.size(); i++) {
            address += ", " + doctor.towns.get(i);
        }
//
//        for (int i = 1; i < doctor.clinics.size(); i++) {
//            clinics += "," + doctor.clinics.get(i);
//        }

        tvSpecial.setText(special);

        tvAddress.setText(address);

//        tvClinics.setText(clinics);

        Log.e("photo", doctor.photo);

        Picasso.get()
                .load("http://128.199.180.50/api/get_image/" + doctor.photo)
                .resize(40, 40)
                .onlyScaleDown()
                .centerCrop()
                .into(imgProfile);
        tvId.setText(String.valueOf(doctor.id));
    }

    @Override
    public void onClick(View v) {
        listener.onDoctorClick(Integer.parseInt((String) tvId.getText()));
        int position;
        position = getAdapterPosition();
        Log.e("position", String.valueOf(position));
//        Log.e("doctorId", String.valueOf(doctorId));

    }


}

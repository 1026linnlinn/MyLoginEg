package com.activity.mylogineg.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.activity.mylogineg.holder.DoctorHolder;
import com.activity.mylogineg.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorHolder>{

    List<Doctor> doctorLists =new ArrayList<>();
        private DoctorHolder.OnDoctorClickListener listener;

        public DoctorAdapter(DoctorHolder.OnDoctorClickListener listener){

            this.listener=listener;
        }

    @NonNull
        @Override
        public DoctorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
            return DoctorHolder.create(inflater,viewGroup,listener);

        }

        @Override
        public void onBindViewHolder(@NonNull DoctorHolder doctorHolder, int i) {

            doctorHolder.bindData(doctorLists.get(i));

        }

        @Override
        public int getItemCount() {
            return doctorLists.size();
        }

        public void addDoctors(List<Doctor> doctorList) {
            this.doctorLists.clear();
            this.doctorLists.addAll(doctorList);
            notifyDataSetChanged();
        }
    }


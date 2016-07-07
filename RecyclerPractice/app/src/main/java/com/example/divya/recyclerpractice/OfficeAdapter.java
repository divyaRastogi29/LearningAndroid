package com.example.divya.recyclerpractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by divya on 15/6/16.
 */
public class OfficeAdapter extends RecyclerView.Adapter<OfficeAdapter.MyViewHolder> {

    private List<EmployeeInfo> employeeList ;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, office, profile;
        public ImageView img;
        MyViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.nameId);
            office = (TextView)view.findViewById(R.id.officeId);
            profile = (TextView)view.findViewById(R.id.profileId);
            img = (ImageView)view.findViewById(R.id.imgId);

        }

    }

    public OfficeAdapter(List<EmployeeInfo> employeeList){
        this.employeeList = employeeList ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EmployeeInfo employeeInfo = employeeList.get(position);
        holder.name.setText(employeeInfo.getName());
        holder.office.setText(employeeInfo.getOffice());
        holder.profile.setText(employeeInfo.getProfile());
        holder.img.setImageResource(employeeInfo.getImg());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }
}

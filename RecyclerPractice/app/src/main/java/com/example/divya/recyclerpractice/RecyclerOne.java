package com.example.divya.recyclerpractice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerOne extends AppCompatActivity {
    private List<EmployeeInfo> employeeInfoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OfficeAdapter officeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_one);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerId);
        officeAdapter = new OfficeAdapter(employeeInfoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(officeAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        prepareOfficeData();
    }

    public void prepareOfficeData(){
        EmployeeInfo e1 = new EmployeeInfo();
        e1.setName("divya");
        e1.setOffice("snapdeal");
        e1.setProfile("testing");
        e1.setImg(R.drawable.college);
        employeeInfoList.add(e1);
        EmployeeInfo e2 = new EmployeeInfo();
        e2.setName("riya");
        e2.setOffice("tcs");
        e2.setProfile("development");
        e2.setImg(R.drawable.college);
        employeeInfoList.add(e2);
        EmployeeInfo e3 = new EmployeeInfo();
        e3.setName("priya");
        e3.setOffice("accenture");
        e3.setProfile("management");
        e3.setImg(R.drawable.college);
        employeeInfoList.add(e3);
        EmployeeInfo e4 = new EmployeeInfo();
        e4.setName("rahul");
        e4.setOffice("sapient");
        e4.setProfile("lawyer");
        e4.setImg(R.drawable.college);
        employeeInfoList.add(e4);
    }
}

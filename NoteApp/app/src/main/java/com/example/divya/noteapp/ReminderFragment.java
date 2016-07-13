package com.example.divya.noteapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by divya on 5/7/16.
 */
public class ReminderFragment extends Fragment implements View.OnClickListener{
    private EditText editTitle;
    private EditText editReminder;
    private FloatingActionButton submitButton;
    private ReminderDataSource reminderDataSource;
    private LinearLayout linearLayout,linearDateId,coordinateLayout;
    private Note note;
    private Switch switchId;
    private TextView dateId, timeId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        initViews(view);
        //   submitButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        reminderDataSource = ReminderDataSource.getInstance(getActivity());
        reminderDataSource.open();
        submitButton.setOnClickListener(this);
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable("note");
            if (note != null) {
                editTitle.setText(note.getTitle());
                editReminder.setText(note.getReminder());
            }
        }
        switchId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    linearDateId.setVisibility(View.VISIBLE);
                    dateId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPopUpDate();
                        }
                    });
                    timeId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPopUpTime();
                        }
                    });
                }
                else{
                    linearDateId.setVisibility(View.GONE);
                }

            }
        });
        return view;
    }
    private void showPopUpTime(){
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplication()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.clock,coordinateLayout ,false);
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(layout);
        popupWindow.setWidth(900);
        popupWindow.setHeight(1300);
        TimePicker tp = (TimePicker)layout.findViewById(R.id.clock);
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeId.setText(hourOfDay+":"+minute);
                popupWindow.dismiss();
            }
        });
        coordinateLayout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(coordinateLayout, Gravity.CENTER ,0,0);
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
    }
    private void showPopUpDate(){
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplication()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.calendar,coordinateLayout ,false);
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(layout);
        CalendarView cv = (CalendarView) layout.findViewById(R.id.calendarId);
        cv.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub
                dateId.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                //    dateId.setText(dayOfMonth+" "+(new DateFormatSymbols().getMonths()[month])+","+year);
                popupWindow.dismiss();
                Log.d("date selected", "date selected " + year + " " + month + " " + dayOfMonth);

            }
        });

        coordinateLayout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(coordinateLayout, Gravity.CENTER ,0,0);
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
    }


    private void initViews(View view) {
        submitButton = (FloatingActionButton)view.findViewById(R.id.submitButton);
        editTitle = (EditText) view.findViewById(R.id.titleNote);
        editReminder = (EditText)view.findViewById(R.id.reminderNote);
        linearLayout = (LinearLayout)getActivity().findViewById(R.id.linearLayout);
        switchId = (Switch)view.findViewById(R.id.switchId);
        linearDateId = (LinearLayout)view.findViewById(R.id.linearDateId);
        coordinateLayout = (LinearLayout)view.findViewById(R.id.coordinatorLayout);
        dateId = (TextView) view.findViewById(R.id.dateId);
        dateId.setPaintFlags(dateId.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        timeId = (TextView) view.findViewById(R.id.timeId);
        timeId.setPaintFlags(timeId.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onClick(View v) {
        setAlarm(getActivity().getApplication());
        if((note == null)||(note.getId()==0)) {
            note = new Note();

            final  String title = editTitle.getText().toString().trim();
            final String reminder = editReminder.getText().toString().trim();
            if (title.length() > 0) {
                new Thread(new CreateThread(title,reminder)).start();
                Snackbar.make(v,"Reminder created",Snackbar.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
                Log.d("Note Inserted", note.toString());
            } else
                Snackbar.make(v,"Title cannot be left empty",Snackbar.LENGTH_SHORT).show();
        }
        else{
            final String title = editTitle.getText().toString();
            final String reminder = editReminder.getText().toString();
            if (title.length() > 0) {
                new Thread(new UpdateThread(note.getId(),title, reminder, note.getImgColor())).start();
                Snackbar.make(v,"Reminder updated",Snackbar.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
                Log.d("Note Updated", note.toString());
            } else
                Snackbar.make(v,"Title cannot be left empty",Snackbar.LENGTH_SHORT).show();
        }

    }
    private void setAlarm(Context context){
        String date = dateId.getText().toString();
        String time = timeId.getText().toString();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),234324243,intent,0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        time = time+":00.000";
        Date datefinal= new Date();
        try {
            Log.d("Alarm","alarm to be set for time : "+date+time);
            datefinal = sdf.parse(date+" "+time);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, datefinal.getTime(),pendingIntent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if((note == null)||(note.getId()==0)) {
            note = new Note();

            final String title = editTitle.getText().toString().trim();
            final  String reminder = editReminder.getText().toString().trim();
            if (title.length() > 0) {
                new Thread(new CreateThread(title,reminder)).start();
                Snackbar.make(linearLayout,"Reminder created",Snackbar.LENGTH_SHORT).show();
                Log.d("Note Inserted", note.toString());
            } else {
                if(reminder.length()>0) {
                    Snackbar.make(linearLayout, "Title cannot be left empty . Note created with default title",
                            Snackbar.LENGTH_SHORT).show();
                    new Thread(new CreateThread("Set title",reminder)).start();
                }
            }
        }
        else{
            final String title = editTitle.getText().toString();
            final String reminder = editReminder.getText().toString();
            if (title.length() > 0) {
                new Thread(new UpdateThread(note.getId(),title, reminder, note.getImgColor())).start();
                Snackbar.make(linearLayout,"Reminder updated",Snackbar.LENGTH_SHORT).show();
                Log.d("Note Updated", note.toString());
            } else {
                if(reminder.length()>0) {
                    Snackbar.make(linearLayout, "Title cannot be left empty . Note updated with default title",
                            Snackbar.LENGTH_SHORT).show();
                    new Thread(new UpdateThread(note.getId(), "Set title", reminder, note.getImgColor())).start();
                }
            }
        }
    }
    class CreateThread implements Runnable{
        String title, reminder;
        Note note;
        CreateThread(String title, String reminder){
            this.title = title ;
            this.reminder = reminder;
        }
        @Override
        public void run() {
            note = reminderDataSource.createNote(title, reminder);
        }
    }

    class UpdateThread implements Runnable{
        long  id ;String title, reminder ; int color;
        UpdateThread(long  id ,String title, String reminder, int color){
            this.id = id;
            this.title = title;
            this.reminder = reminder;
            this.color = color;
        }
        @Override
        public void run() {
            reminderDataSource.updateNote(id,title, reminder, color);
        }
    }
}

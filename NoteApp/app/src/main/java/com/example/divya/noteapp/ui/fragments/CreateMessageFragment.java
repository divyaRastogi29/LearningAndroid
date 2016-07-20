package com.example.divya.noteapp.ui.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.divya.noteapp.model.Note;
import com.example.divya.noteapp.R;
import com.example.divya.noteapp.data_manager.ReminderDataSource;
import com.example.divya.noteapp.receiver.AlarmReceiver;
import com.example.divya.noteapp.utils.NoteAlarmManager;

import java.util.Calendar;

/**
 * Created by divya on 5/7/16.
 */
public class CreateMessageFragment extends Fragment implements View.OnClickListener{
    private EditText editTitle;
    private EditText editReminder;
    private FloatingActionButton submitButton;
    private ReminderDataSource reminderDataSource;
    private LinearLayout linearLayout,linearDateId,coordinateLayout;
    private Note note;
    private Switch mSwitch;
    private TextView mDate, mTime;
    private Calendar mCalendar;
    private int flag = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        initViews(view);
        reminderDataSource = ReminderDataSource.getInstance(getActivity());
        reminderDataSource.open();
        submitButton.setOnClickListener(this);
        Log.d("CALENDAR",mCalendar+"");
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable(AlarmReceiver.NOTE);
            if (note != null) {
                editTitle.setText(note.getTitle());
                editReminder.setText(note.getReminder());
                if(note.isAlarmSet()==1) {
                    mSwitch.toggle();
                    linearDateId.setVisibility(View.VISIBLE);
                    String time = note.getTime();
                    mDate.setText(note.getTime().split(" ")[0]);
                    mTime.setText(note.getTime().split(" ")[1]);
                }
            }
        }
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    linearDateId.setVisibility(View.VISIBLE);
                    mDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPopUpDate();
                        }
                    });
                    mTime.setOnClickListener(new View.OnClickListener() {
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
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                mTime.setText(hourOfDay+":"+minute);
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
                mDate.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                //    mDate.setText(dayOfMonth+" "+(new DateFormatSymbols().getMonths()[month])+","+year);
                popupWindow.dismiss();
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
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
        mSwitch = (Switch)view.findViewById(R.id.switchId);
        linearDateId = (LinearLayout)view.findViewById(R.id.linearDateId);
        coordinateLayout = (LinearLayout)view.findViewById(R.id.coordinatorLayout);
        mDate = (TextView) view.findViewById(R.id.dateId);
        mTime = (TextView) view.findViewById(R.id.timeId);
        mCalendar = Calendar.getInstance();
    }

    @Override
    public void onClick(View v) {
        if((note == null)||(note.getId()==0)) {
            createNote(linearLayout);
        }
        else{
            updateNote(linearLayout);
        }
        flag=1;
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(flag==0) {
            if ((note == null) || (note.getId() == 0)) {
                createNote(linearLayout);
            } else {
                updateNote(linearLayout);
            }
        }
    }

    public void createNote(View v) {
        note = new Note();
        final String title = editTitle.getText().toString().trim();
        final String reminder = editReminder.getText().toString().trim();
        int isAlarmSet = 0;
        String time = "";
        if (mSwitch.isChecked()) {
            if((!mDate.getText().equals(""))&&(!mTime.getText().equals(""))) {
                isAlarmSet = 1;
                time = mDate.getText() + " " + mTime.getText();
            }
        }

        if (title.length() > 0) {
            new Thread(new CreateRunnable(title, reminder, isAlarmSet, time)).start();
            Snackbar.make(v, "Reminder created", Snackbar.LENGTH_SHORT).show();
        } else {
            if(reminder.length()>0) {
                Snackbar.make(v, "Note created with title - ToDo", Snackbar.LENGTH_SHORT).show();
                new Thread(new CreateRunnable("ToDo", reminder, isAlarmSet, time)).start();
            }
        }
    }

    public void updateNote(View v){
        final String title = editTitle.getText().toString();
        final String reminder = editReminder.getText().toString();
        int isAlarmSet = 0;
        String time = "";
        if(mSwitch.isChecked()){
            if((!mDate.getText().equals(""))&&(!mTime.getText().equals(""))) {
                isAlarmSet = 1;
                time = mDate.getText() + " " + mTime.getText();
            }
        }
        if (title.length() > 0) {
            new Thread(new UpdateRunnable(note.getId(),title, reminder, note.getImgColor(),isAlarmSet,time)).start();
            Snackbar.make(v,"Reminder updated",Snackbar.LENGTH_SHORT).show();
            Log.d("Note Updated", note.toString());
        } else {
            if(reminder.length()>0) {
                Snackbar.make(v, "Note created with title - ToDo", Snackbar.LENGTH_SHORT).show();
                new Thread(new UpdateRunnable(note.getId(), "ToDo", reminder, note.getImgColor(), isAlarmSet, time)).start();
            }
        }
    }

    class CreateRunnable implements Runnable {
        String title, reminder, time;
        int isAlarmSet;
        Note note;
        CreateRunnable(String title, String reminder, int isAlarmSet, String time){
            this.isAlarmSet = isAlarmSet;
            this.time = time;
            this.title = title ;
            this.reminder = reminder;
        }
        @Override
        public void run() {
            note = reminderDataSource.createNote(title, reminder, isAlarmSet,time);
            Log.d("Note Inserted", note.toString());
            if(isAlarmSet==1) {
                NoteAlarmManager.getInstance().setAlarm(mCalendar, note);
            }
        }
    }

    class UpdateRunnable implements Runnable {
        long  id ;String title, reminder,time ; int color, isAlarmSet;
        UpdateRunnable(long  id , String title, String reminder, int color,int isAlarmSet,String time){
            this.id = id;
            this.title = title;
            this.reminder = reminder;
            this.color = color;
            this.isAlarmSet = isAlarmSet;
            this.time = time;
        }
        @Override
        public void run() {
            reminderDataSource.updateNote(id,title, reminder, color, isAlarmSet,time);
            note.setId(id);
            note.setTitle(title);
            note.setReminder(reminder);note.setImgColor(color);
            note.setisAlarmSet(isAlarmSet);
            note.setTime(time);
            if(isAlarmSet==1) {
                NoteAlarmManager.getInstance().setAlarm(mCalendar, note);
            }
        }
    }
}

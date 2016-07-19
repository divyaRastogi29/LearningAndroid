package com.example.divya.noteapp.ui.fragments;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        initViews(view);
        reminderDataSource = ReminderDataSource.getInstance(getActivity());
        reminderDataSource.open();
        submitButton.setOnClickListener(this);
        mCalendar = Calendar.getInstance();
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable(AlarmReceiver.NOTE);
            if (note != null) {
                editTitle.setText(note.getTitle());
                editReminder.setText(note.getReminder());
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
                mTime.setText(hourOfDay+":"+minute);

                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
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
        mDate.setPaintFlags(mDate.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        mTime = (TextView) view.findViewById(R.id.timeId);
        mTime.setPaintFlags(mTime.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onClick(View v) {
        if((note == null)||(note.getId()==0)) {
            note = new Note();

            final  String title = editTitle.getText().toString().trim();
            final String reminder = editReminder.getText().toString().trim();

            if (title.length() > 0) {
                new Thread(new CreateRunnable(title,reminder)).start();
                Snackbar.make(v,"Reminder created",Snackbar.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
                Log.d("Note Inserted", note.toString());
            } else {
                Snackbar.make(v, "Title cannot be left empty", Snackbar.LENGTH_SHORT).show();
            }
        }
        else{
            final String title = editTitle.getText().toString();
            final String reminder = editReminder.getText().toString();
            if (title.length() > 0) {
                new Thread(new UpdateRunnable(note.getId(),title, reminder, note.getImgColor())).start();
                Snackbar.make(v,"Reminder updated",Snackbar.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();
                Log.d("Note Updated", note.toString());
            } else
                Snackbar.make(v,"Title cannot be left empty",Snackbar.LENGTH_SHORT).show();
        }

        if(mSwitch.isChecked()){
            setAlarm();
        }

    }

    private void setAlarm() {
        NoteAlarmManager noteAlarmManager = NoteAlarmManager.getInstance();
        noteAlarmManager.setAlarm(mCalendar,note);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if((note == null)||(note.getId()==0)) {
            note = new Note();

            final String title = editTitle.getText().toString().trim();
            final  String reminder = editReminder.getText().toString().trim();
            if (title.length() > 0) {
                new Thread(new CreateRunnable(title,reminder)).start();
                Snackbar.make(linearLayout,"Reminder created",Snackbar.LENGTH_SHORT).show();
                Log.d("Note Inserted", note.toString());
            } else {
                if(reminder.length()>0) {
                    Snackbar.make(linearLayout, "Title cannot be left empty . Note created with default title",
                            Snackbar.LENGTH_SHORT).show();
                    new Thread(new CreateRunnable("Set title",reminder)).start();
                }
            }
        }
        else{
            final String title = editTitle.getText().toString();
            final String reminder = editReminder.getText().toString();
            if (title.length() > 0) {
                new Thread(new UpdateRunnable(note.getId(),title, reminder, note.getImgColor())).start();
                Snackbar.make(linearLayout,"Reminder updated",Snackbar.LENGTH_SHORT).show();
                Log.d("Note Updated", note.toString());
            } else {
                if(reminder.length()>0) {
                    Snackbar.make(linearLayout, "Title cannot be left empty . Note updated with default title",
                            Snackbar.LENGTH_SHORT).show();
                    new Thread(new UpdateRunnable(note.getId(), "Set title", reminder, note.getImgColor())).start();
                }
            }
        }
        if(mSwitch.isChecked()){
            setAlarm();
        }
    }

    class CreateRunnable implements Runnable {
        String title, reminder;
        Note note;
        CreateRunnable(String title, String reminder){
            this.title = title ;
            this.reminder = reminder;
        }
        @Override
        public void run() {
            note = reminderDataSource.createNote(title, reminder);
            if(mSwitch.isChecked()) {
                NoteAlarmManager.getInstance().setAlarm(mCalendar, note);
            }
        }
    }

    class UpdateRunnable implements Runnable {

        long  id ;String title, reminder ; int color;
        UpdateRunnable(long  id , String title, String reminder, int color){
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

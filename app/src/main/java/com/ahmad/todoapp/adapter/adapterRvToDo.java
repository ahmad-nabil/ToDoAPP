package com.ahmad.todoapp.adapter;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.todoapp.NotificationReceiver;
import com.ahmad.todoapp.R;
import com.ahmad.todoapp.databinding.DialogPopupNotesBinding;
import com.ahmad.todoapp.databinding.NotesRvBinding;
import com.ahmad.todoapp.todo.listenerFinishedTask;
import com.ahmad.todoapp.todo.objectModel;
import com.bumptech.glide.Glide;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Calendar;

public class adapterRvToDo extends RecyclerView.Adapter<adapterRvToDo.notes> {
    ArrayList<objectModel> listnotes;
Context context ;
    Calendar calendar;
    private listenerFinishedTask finishedTask;
    public adapterRvToDo(ArrayList<objectModel> listnotes, Context context,listenerFinishedTask finishedTask) {
        this.listnotes = listnotes;
        this.context = context;
        calendar=Calendar.getInstance();
        this.finishedTask=finishedTask;
    }

    @NonNull
    @Override
    public adapterRvToDo.notes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotesRvBinding rvBinding=NotesRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new notes(rvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterRvToDo.notes holder, @SuppressLint("RecyclerView") int position) {
holder.Binding.notes.setText(listnotes.get(holder.getAdapterPosition()).getNote());

    Glide.with(context).load(listnotes.get(holder.getAdapterPosition()).getImg()).into(holder.Binding.imagenotes);
    //when click long will popup dialog
holder.Binding.getRoot().setOnLongClickListener(v -> {
    DialogPopupNotesBinding popupNotesBinding=DialogPopupNotesBinding.inflate(LayoutInflater.from(context));
    Dialog dialog=new Dialog(context);
    dialog.setContentView(popupNotesBinding.getRoot());
    //set text in dialog like note
    popupNotesBinding.textView3.setText(listnotes.get(holder.getAdapterPosition()).getNote());
    dialog.show();
    //delete note
    popupNotesBinding.deletenote.setOnClickListener(d->{
        listnotes.remove(holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
        dialog.dismiss();
    });
    //set alert
popupNotesBinding.setalertbtn.setOnClickListener(A-> {
    dialog.dismiss();
showAlertDialog(listnotes.get(holder.getAdapterPosition()).getNote());
});
popupNotesBinding.finished.setOnClickListener(F->{
finishedTask.onTaskComplete(listnotes.get(holder.getAdapterPosition()));
listnotes.remove(holder.getAdapterPosition());
    notifyItemRemoved(holder.getAdapterPosition());
    dialog.dismiss();
;});
    return true;
});
    }
    // Show alert dialog to set a reminder
    private void showAlertDialog(String note) {
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        alert.setTitle("Alert");
        // Inflate the layout for time picker
        View DialogView=LayoutInflater.from(context).inflate(R.layout.alert_picker,null);
        alert.setView(DialogView);
        // Initialize  pickers
        final DatePicker datePicker=DialogView.findViewById(R.id.datepicker);
        final TimePicker timePicker=DialogView.findViewById(R.id.timePicker);
        datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),null);
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));
        // Set positive and negative buttons for the alert
        alert.setPositiveButton("set",(dialog, which) -> {
int year=datePicker.getYear();
int month=datePicker.getMonth();
int day=datePicker.getDayOfMonth();
           int  hourOfDay = timePicker.getHour();
            int minute = timePicker.getMinute();
            // Update calendar with the selected date and time
calendar.set(Calendar.MONTH,month);
calendar.set(Calendar.YEAR,year);
calendar.set(Calendar.DAY_OF_MONTH,day);
calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
calendar.set(Calendar.MINUTE,minute);
        });
        alert.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
alert.show();
        // Create a notification for the selected date and time
        createNotification(calendar.getTimeInMillis(),"Task","your "+note+"\n is due");
    }

    @SuppressLint({"MissingPermission", "ScheduleExactAlarm", "NewApi"})
    private void createNotification(long timeInMillis, String title, String content) {
                // Notification channel ID and create channel
        String Channel="1";

            @SuppressLint({"NewApi", "LocalSuppress"}) NotificationChannel notificationChannel=new NotificationChannel(Channel,"task reminder", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        // Create an intent for the NotificationReceiver class
        Intent intent=new Intent(context, NotificationReceiver.class).putExtra("title",title).putExtra("content",content);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Create a PendingIntent for the broadcast
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_MUTABLE);
      //get alarm manager
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);

    }


    @Override
    public int getItemCount() {
        return listnotes.size();
    }

    public class notes extends RecyclerView.ViewHolder{
        NotesRvBinding Binding;
        public notes(NotesRvBinding rvBinding) {
            super(rvBinding.getRoot());
            Binding=rvBinding;
        }
    }
}

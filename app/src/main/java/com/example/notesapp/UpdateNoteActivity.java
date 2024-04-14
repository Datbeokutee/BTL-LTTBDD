package com.example.notesapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateNoteActivity extends AppCompatActivity {
    EditText title,content,date;
    Calendar calendar;
    CheckBox isImportant;
    ImageButton addbtn;
    Database database;
    boolean isimportant = false;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_note);
        title = findViewById(R.id.notes_title_text_edit);
        content = findViewById(R.id.notes_content_text_edit);
        addbtn = findViewById(R.id.check_btn_edit);
        isImportant = findViewById(R.id.isImportant_edit);
        date = findViewById(R.id.notes_date_complete_edit);
        calendar = Calendar.getInstance();
        database = new Database(UpdateNoteActivity.this);
        Intent intent = getIntent();
        Bundle myBundle = intent.getBundleExtra("note");
        String mytitle = myBundle.getString("title");
        String mycontext = myBundle.getString("context");
        String mydate = myBundle.getString("date");
        String myisImportant = myBundle.getString("isImportant");
        String myId = myBundle.getString("_id");
//        System.out.println(title + context + date +isImportant);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                updatelable();
            }
        };
        date.setOnClickListener(v->dateclick(dateSetListener));
        title.setText(mytitle);
        content.setText(mycontext);
        date.setText(mydate);

        if(myisImportant.equals("1")){
            isImportant.setChecked(true);
            check = 1;
        }else{
            isImportant.setChecked(false);
            check = 0;
        }
        addbtn.setOnClickListener(v -> addbtnclick(title.getText().toString(),
                content.getText().toString(),date.getText().toString(),check,myId));





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void addbtnclick(String title,String context,String date,int isImportant ,String id){
        database.updateNote(title,context,date,isImportant,id);
    }

    void updatelable(){
        String myFormal = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormal);
        date.setText(dateFormat.format(calendar.getTime()));
    }

    void dateclick(DatePickerDialog.OnDateSetListener dateSetListener){
        new DatePickerDialog(UpdateNoteActivity.this,dateSetListener
                ,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
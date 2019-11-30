package com.dongkyoo.gongzza.post.write;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.customViews.hashTagRecyclerView.HashTagRecyclerView;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.HashTag;
import com.dongkyoo.gongzza.vos.User;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WritePostActivity extends AppCompatActivity {

    private WritePostViewModel viewModel;
    private User me;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        me = getIntent().getParcelableExtra(Config.USER);
        viewModel = new WritePostViewModel(this);

        EditText titleEditText = findViewById(R.id.title_editText);
        EditText contentEditText = findViewById(R.id.content_editText);
        EditText numOfParticipantsEditText = findViewById(R.id.num_of_participants_editText);
        TextView meetDatetimeTextView = findViewById(R.id.meet_datetime_textView);
        HashTagRecyclerView hashTagRecyclerView = findViewById(R.id.hash_tag_recyclerView);

        Button meetDatetimeButton = findViewById(R.id.modify_meet_datetime_button);
        Button submitButton = findViewById(R.id.submit_button);
        meetDatetimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(viewModel.meetDatetime.getValue());

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        WritePostActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            c.set(Calendar.YEAR, year);
                            c.set(Calendar.MONTH, month);
                            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            viewModel.setMeetDatetime(c.getTime());
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
                );

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        WritePostActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            c.set(Calendar.MINUTE, minute);
                            viewModel.setMeetDatetime(c.getTime());
                        }
                    }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false
                );

                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        timePickerDialog.show();
                    }
                });
                datePickerDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<HashTag> hashTagList = hashTagRecyclerView.getHashTagList();
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                String numOfParticipants = numOfParticipantsEditText.getText().toString();
                viewModel.submit(title, content, numOfParticipants, me.getId(), hashTagList);
            }
        });

        viewModel.writeState.observe(this, new Observer<WritePostViewModel.WriteState>() {
            @Override
            public void onChanged(WritePostViewModel.WriteState writeState) {
                if (writeState.errorMessage != null) {
                    Snackbar.make(findViewById(android.R.id.content), writeState.errorMessage, Snackbar.LENGTH_LONG).show();
                } else if (writeState.state == 200) {
                    Intent intent = new Intent();
                    intent.putExtra(Config.POST, writeState.postDto);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        viewModel.meetDatetime.observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 dd일 HH시 mm분");
                meetDatetimeTextView.setText(simpleDateFormat.format(date));
            }
        });
    }
}

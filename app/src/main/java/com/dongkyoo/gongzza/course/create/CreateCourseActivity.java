package com.dongkyoo.gongzza.course.create;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.course.CourseState;
import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.Course;
import com.dongkyoo.gongzza.vos.CourseInfo;
import com.dongkyoo.gongzza.vos.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateCourseActivity extends AppCompatActivity {

    private TimePickerDialog picker;
    private CreateCourseViewModel viewModel;
    private User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        me = getIntent().getParcelableExtra(Config.USER);

        viewModel = new CreateCourseViewModel();

        //CreateCourseActivity 다이얼로그의 각 위젯들을 생성
        EditText editTextCourseName = findViewById(R.id.editTextClassName);
        EditText professorEditText = findViewById(R.id.editTextProfessorName);
        Button submitButton = findViewById(R.id.submit_button);

        RecyclerView courseInfoRecyclerView = findViewById(R.id.course_info_recyclerView);
        courseInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseInfoAdapter adapter = new CourseInfoAdapter(this);
        courseInfoRecyclerView.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextCourseName.getText().toString();
                String professor = professorEditText.getText().toString();

                viewModel.createCourse(new CourseDto(0, me.getId(), name, professor, adapter.getCourseInfoList()));
            }
        });

        viewModel.state.observe(this, new Observer<CourseState>() {
            @Override
            public void onChanged(CourseState courseState) {
                if (courseState.errorMessage != null) {
                    Snackbar.make(findViewById(android.R.id.content), courseState.errorMessage, Snackbar.LENGTH_LONG).show();
                } else if (courseState.state == 200) {
                    Intent intent = new Intent();
                    intent.putExtra(Config.COURSE, courseState.courseDto);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}

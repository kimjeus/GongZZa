package com.dongkyoo.gongzza.course;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dongkyoo.gongzza.MockData;
import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.customViews.timetableView.TimetableView;
import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.course.create.CreateCourseActivity;
import com.dongkyoo.gongzza.main.MainActivity;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.Course;
import com.dongkyoo.gongzza.vos.CourseInfo;
import com.dongkyoo.gongzza.vos.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static android.widget.Toast.makeText;

public class CourseFragment extends Fragment {

    private Context context;

    private CourseViewModel viewModel;
    private User me;
    private TimetableView timetableView;

    private CourseFragment() {
        // Required empty public constructor
    }

    public static CourseFragment newInstance(User me) {
        CourseFragment fragment = new CourseFragment();
        fragment.me = me;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        context = container.getContext();

        timetableView = view.findViewById(R.id.timetableView);
        timetableView.setOnClickCourseInfoListener(new TimetableView.OnClickCourseInfoListener() {
            @Override
            public void onClick(Course course, CourseInfo courseInfo) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(course.getName())
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.deleteCourse(course.getId());
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
            }
        });

        Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateCourseActivity.class);
                intent.putExtra(Config.USER, me);
                startActivityForResult(intent, MainActivity.CODE_INSERT_COURSE);
            }
        });

        viewModel = new CourseViewModel();
        viewModel.loadCourseList(me.getId());

        viewModel.state.observe(this, new Observer<CourseState>() {
            @Override
            public void onChanged(CourseState courseState) {
                if (courseState.errorMessage != null) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), courseState.errorMessage, Snackbar.LENGTH_LONG).show();
                } else if (courseState.state == 200) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "저장 완료", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        viewModel.courseList.observe(this, new Observer<List<CourseDto>>() {
            @Override
            public void onChanged(List<CourseDto> courseDtoList) {
                displayCourseList(courseDtoList);
            }
        });

        return view;
    }

    private void displayCourseList(List<CourseDto> courseDtoList) {
        timetableView.setCourseDtoList(courseDtoList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.CODE_INSERT_COURSE) {
            if (resultCode == Activity.RESULT_OK) {
                CourseDto courseDto = data.getParcelableExtra(Config.COURSE);
                viewModel.insertCourse(courseDto);
            }
        }
    }
}

package com.dongkyoo.gongzza.course.create;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.vos.CourseInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CourseInfo> courseInfoList;
    private String[] dayList;

    private static int TYPE_BODY = 0;
    private static int TYPE_FOOTER = 1;

    public CourseInfoAdapter(Context context) {
        this.context = context;
        courseInfoList = new ArrayList<>();
        dayList = context.getResources().getStringArray(R.array.day_list);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == courseInfoList.size())
            return TYPE_FOOTER;
        return TYPE_BODY;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == TYPE_BODY) {
            return new ViewHolder(layoutInflater.inflate(R.layout.item_course_info, parent, false));
        }
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(layoutInflater.inflate(R.layout.item_footer_course_info, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.startTimeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        Date date = courseInfoList.get(position).getStartTime();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                Date time = calendar.getTime();
                                courseInfoList.get(position).setStartTime(time);
                                viewHolder.startTimeEditText.setText(format.format(courseInfoList.get(position).getStartTime()));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                    }
                }
            });

            viewHolder.endTimeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        Date date = courseInfoList.get(position).getEndTime();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                Date time = calendar.getTime();
                                courseInfoList.get(position).setEndTime(time);
                                viewHolder.endTimeEditText.setText(format.format(courseInfoList.get(position).getEndTime()));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                    }
                }
            });

            viewHolder.daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int spinnerPosition, long id) {
                    int value = spinnerPosition + Calendar.MONDAY;
                    courseInfoList.get(position).setDay(value);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    int value = Calendar.MONDAY;
                    courseInfoList.get(position).setDay(value);
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            viewHolder.addImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseInfoList.add(new CourseInfo());
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return courseInfoList.size() + 1;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<CourseInfo> getCourseInfoList() {
        return courseInfoList;
    }

    public void setCourseInfoList(List<CourseInfo> courseInfoList) {
        this.courseInfoList = courseInfoList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Spinner daySpinner;
        EditText startTimeEditText;
        EditText endTimeEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            daySpinner = itemView.findViewById(R.id.day_spinner);
            startTimeEditText = itemView.findViewById(R.id.start_time_editText);
            endTimeEditText = itemView.findViewById(R.id.end_time_editText);

            daySpinner.setAdapter(new ArrayAdapter<String>(itemView.getContext(), android.R.layout.simple_spinner_dropdown_item, dayList));
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        ImageButton addImageButton;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);

            addImageButton = itemView.findViewById(R.id.add_imageButton);
        }
    }
}

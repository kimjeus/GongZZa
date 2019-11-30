package com.dongkyoo.gongzza.customViews.timetableView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.vos.Course;
import com.dongkyoo.gongzza.vos.CourseInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimetableView extends View {

    private List<CourseDto> courseDtoList;
    private int width;
    private int height;
    private Context context;

    private String[] weekDays;
    private int cellWidth;
    private int cellHeight;
    private int mostLeftColumnWidth;
    private int topRowHeight;
    private String[] colorList;

    private static final int MIN_HOUR = 9;
    private static final int MAX_HOUR = 21;

    private static final int BIG_TEXT_SIZE = 50;
    private static final int SMALL_TEXT_SIZE = 40;

    private static final int MIN_CELL_HEIGHT = 150;

    private List<RectF> courseInfoRectList;
    private List<CourseData> courseList;
    private List<OnClickCourseInfoListener> clickCourseInfoListenerList;

    public TimetableView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TimetableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public TimetableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public interface OnClickCourseInfoListener {
        void onClick(Course course, CourseInfo courseInfo);
    }

    private void init() {
        courseInfoRectList = new ArrayList<>();
        courseList = new ArrayList<>();

        clickCourseInfoListenerList = new ArrayList<>();
        courseDtoList = new ArrayList<>();
        colorList = context.getResources().getStringArray(R.array.color_list);
    }

    public void setOnClickCourseInfoListener(OnClickCourseInfoListener listener) {
        clickCourseInfoListenerList.add(listener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int colorIndex = 0;
        courseInfoRectList = new ArrayList<>();
        courseList = new ArrayList<>();

        Paint p = new Paint();

        // 월화수목금
        p.setTextSize(BIG_TEXT_SIZE);
        for (int i = 0; i < weekDays.length; i++) {
            p.setColor(Color.BLACK);
            canvas.drawText(weekDays[i], (float) i * cellWidth + cellWidth / 2 - BIG_TEXT_SIZE / 2 + mostLeftColumnWidth, BIG_TEXT_SIZE, p);

            p.setColor(Color.GRAY);
            int x = (i + 1) * cellWidth + mostLeftColumnWidth;
            canvas.drawLine(x , 0, x, height, p);
        }

        // 시간
        p.setTextSize(SMALL_TEXT_SIZE);
        for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
            p.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(i), 0, cellHeight * (i - MIN_HOUR) + SMALL_TEXT_SIZE / 2 + topRowHeight, p);

            p.setColor(Color.GRAY);
            int y = cellHeight * (i - MIN_HOUR + 1) + topRowHeight;
            canvas.drawLine(mostLeftColumnWidth, y, width, y, p);
        }

        p.setColor(Color.GRAY);
        canvas.drawLine(mostLeftColumnWidth, topRowHeight, width, topRowHeight, p);
        canvas.drawLine(mostLeftColumnWidth, 0, mostLeftColumnWidth, height, p);

        p.setColor(Color.WHITE);
        for (CourseDto courseDto : courseDtoList) {
            for (CourseInfo info : courseDto.getCourseInfoList()) {
                int day = info.getDay();
                Paint cellPaint = new Paint();
                cellPaint.setColor(Color.parseColor(colorList[colorIndex++]));
                if (day >= Calendar.MONDAY && day <= Calendar.FRIDAY) {
                    Calendar c = Calendar.getInstance();

                    c.setTime(info.getStartTime());
                    int startHour = c.get(Calendar.HOUR_OF_DAY);
                    int startMin = c.get(Calendar.MINUTE);

                    c.setTime(info.getEndTime());
                    int endHour = c.get(Calendar.HOUR_OF_DAY);
                    int endMin = c.get(Calendar.MINUTE);

                    float startX = mostLeftColumnWidth + cellWidth * (day - Calendar.MONDAY);
                    float startY = topRowHeight + cellHeight * (startHour - MIN_HOUR) + cellHeight * (startMin / 60f);

                    float endX = startX + cellWidth;
                    float endY = topRowHeight + cellHeight * (endHour - MIN_HOUR) + cellHeight * (endMin / 60f);

                    RectF rectF = new RectF(startX, startY, endX, endY);
                    courseInfoRectList.add(rectF);
                    courseList.add(new CourseData(courseDto, info));

                    canvas.drawRect(rectF, cellPaint);

                    List<String> nameList = split(courseDto.getName(), 5);

                    p.setFakeBoldText(true);
                    float nextY = 0;
                    for (int i = 0; i < nameList.size(); i++) {
                        nextY = startY + p.getTextSize() * (i + 1);
                        canvas.drawText(nameList.get(i), startX + 5, nextY, p);
                    }

                    p.setFakeBoldText(false);
                    nameList = split(courseDto.getProfessor(), 5);
                    nextY += 10;
                    for (int i = 0; i < nameList.size(); i++) {
                        nextY += p.getTextSize();
                        canvas.drawText(nameList.get(i), startX + 5, nextY, p);
                    }
                }
            }
        }
    }

    private List<String> split(String str, int count) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length() ;i += count) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < count && i + j < str.length(); j++) {
                sb.append(str.charAt(i + j));
            }
            list.add(sb.toString());
        }
        return list;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);

        weekDays = context.getResources().getStringArray(R.array.weekday_list);

        mostLeftColumnWidth = SMALL_TEXT_SIZE * 2 + 15;
        topRowHeight = BIG_TEXT_SIZE + 30;

        cellWidth = (width - mostLeftColumnWidth) / weekDays.length;
        cellHeight = (height - topRowHeight) / (MAX_HOUR - MIN_HOUR);
        if (cellHeight < MIN_CELL_HEIGHT) {
            cellHeight = MIN_CELL_HEIGHT;
        }

        height = cellHeight * (MAX_HOUR - MIN_HOUR + 1) + topRowHeight;

        setMeasuredDimension(width, height + 30);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && event.getEventTime() - event.getDownTime() < 300) {
            for (int i = 0; i < courseInfoRectList.size(); i++) {
                if (courseInfoRectList.get(i).contains(event.getX(), event.getY())) {
                    for (OnClickCourseInfoListener listener : clickCourseInfoListenerList) {
                        listener.onClick(courseList.get(i).course, courseList.get(i).courseInfo);
                    }
                    return true;
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < courseInfoRectList.size(); i++) {
                if (courseInfoRectList.get(i).contains(event.getX(), event.getY())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void addCourseDtoList(List<CourseDto> courseDtoList) {
        courseDtoList.addAll(courseDtoList);
        invalidate();
    }

    public void addCourseDto(CourseDto courseDto) {
        courseDtoList.add(courseDto);
        invalidate();
    }

    public void setCourseDtoList(List<CourseDto> courseDtoList) {
        this.courseDtoList = courseDtoList;
        invalidate();
    }

    private static final class CourseData {
        Course course;
        CourseInfo courseInfo;

        public CourseData(Course course, CourseInfo courseInfo) {
            this.course = course;
            this.courseInfo = courseInfo;
        }
    }
}

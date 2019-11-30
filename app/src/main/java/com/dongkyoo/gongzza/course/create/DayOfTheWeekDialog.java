package com.dongkyoo.gongzza.course.create;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.dongkyoo.gongzza.R;

public class DayOfTheWeekDialog {
    private Context context;

    public DayOfTheWeekDialog(Context context) {
        this.context = context;
    }

    //호출할 다이얼로그 정의
    public void callFunction(EditText editTextDayOfTheWeek) {
        //다이얼로그를 정의하기 위해 Dialog클래스 생성
        Dialog dig = new Dialog(context);

        //액티비티의 타이틀 바를 숨김
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //다이얼로그의 레이아웃 설정
        dig.setContentView(R.layout.dialog_day_of_the_week);

        //다이얼로그 노출
        dig.show();

        //다이얼로그의 각 위젯들을 정의
        Button monday = dig.findViewById(R.id.Monday);
        Button tuesday = dig.findViewById(R.id.Tuesday);
        Button wednesday = dig.findViewById(R.id.Wednesday);
        Button thursday = dig.findViewById(R.id.Thursday);
        Button friday = dig.findViewById(R.id.Friday);
        Button saturday = dig.findViewById(R.id.Saturday);
        Button sunday = dig.findViewById(R.id.Sunday);

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDayOfTheWeek.setText(monday.getText().toString());

                //다이얼로그 종료
                dig.dismiss();
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDayOfTheWeek.setText(tuesday.getText().toString());

                //다이얼로그 종료
                dig.dismiss();
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDayOfTheWeek.setText(wednesday.getText().toString());

                //다이얼로그 종료
                dig.dismiss();
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDayOfTheWeek.setText(thursday.getText().toString());

                //다이얼로그 종료
                dig.dismiss();
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDayOfTheWeek.setText(friday.getText().toString());

                //다이얼로그 종료
                dig.dismiss();
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDayOfTheWeek.setText(saturday.getText().toString());

                //다이얼로그 종료
                dig.dismiss();
            }
        });
        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDayOfTheWeek.setText(sunday.getText().toString());

                //다이얼로그 종료
                dig.dismiss();
            }
        });
    }
}

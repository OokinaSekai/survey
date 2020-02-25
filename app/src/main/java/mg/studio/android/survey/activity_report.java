package mg.studio.android.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;


public class activity_report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        MainActivity.activityList.add(this);

        TextView answer1=(TextView) findViewById(R.id.an1);
        TextView answer2=(TextView) findViewById(R.id.an2);
        TextView answer3=(TextView) findViewById(R.id.an3);
        TextView answer4=(TextView) findViewById(R.id.an4);
        TextView answer5=(TextView) findViewById(R.id.an5);
        TextView answer6=(TextView) findViewById(R.id.an6);
        TextView answer7=(TextView) findViewById(R.id.an7);
        TextView answer8=(TextView) findViewById(R.id.an8);
        TextView answer9=(TextView) findViewById(R.id.an9);
        TextView answer10=(TextView) findViewById(R.id.an10);
        TextView answer11=(TextView) findViewById(R.id.an11);
        TextView answer12=(TextView) findViewById(R.id.an12);
        transam ta=(transam) getApplication();

        //change the size depends on the length of text
        if(ta.str[3].length()>40)
            answer4.setTextSize(16);
        else if(ta.str[3].length()>0)
            answer4.setTextSize(12);
        if(ta.str[4].length()>40)
            answer5.setTextSize(16);
        else if(ta.str[3].length()>0)
            answer5.setTextSize(12);
        if(ta.str[6].length()>70)
            answer7.setTextSize(13);

        answer1.setText(ta.str[0]);
        answer2.setText(ta.str[1]);
        answer3.setText(ta.str[2]);
        answer4.setText(ta.str[3]);
        answer5.setText(ta.str[4]);
        answer6.setText(ta.str[5]);
        answer7.setText(ta.str[6]);
        answer8.setText(ta.str[7]);
        answer9.setText(ta.str[8]);
        answer10.setText(ta.str[9]);
        answer11.setText(ta.str[10]);
        answer12.setText(ta.str[11]);
    }

    public void clickend(View v)
    {
        MainActivity.exit();
    }
}

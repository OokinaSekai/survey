package mg.studio.android.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class activity_9th extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_nine);
        MainActivity.activityList.add(this);
    }

    public void btnClick(View v)
    {
        RadioGroup group= (RadioGroup) findViewById(R.id.RG);
        int selected = group.getCheckedRadioButtonId();
        Intent intent = new Intent();

        switch(selected){
            case R.id.rbt1:
                intent.setClass(activity_9th.this, activity_10th.class);
                startActivity(intent);;
                break;
            case R.id.rbt2:
                intent.setClass(activity_9th.this, activity_10th.class);
                startActivity(intent);;
                break;
            case R.id.rbt3:
                intent.setClass(activity_9th.this, activity_10th.class);
                startActivity(intent);;
                break;
            case R.id.rbt4:
                intent.setClass(activity_9th.this, activity_10th.class);
                startActivity(intent);;
                break;
            default:
                Toast ts = Toast.makeText(getBaseContext(),"please choose",Toast.LENGTH_LONG);
                ts.show();
        }
    }
}

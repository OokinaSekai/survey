package mg.studio.android.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class activity_thx extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_survey);
        MainActivity.activityList.add(this);
    }

    public void clickThx(View v)
    {
        MainActivity.exit();
    }
}

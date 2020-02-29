package mg.studio.android.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class activity_report extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        MainActivity.activityList.add(this);

        //ask for storage permision by class PermisionoUtils when first time run the app
        PermisionUtils.verifyStoragePermissions(this);

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

    //to chane a string group to json standard
    public String change(String str[])
    {
        String fin="{";
        for(int i=1;i<13;i++)
        {
            if(i<12)
                fin=fin+"\"question"+i+"\":"+"\""+str[i-1]+"\""+",";
            if(i==12)
                fin=fin+"\"question"+i+"\":"+"\""+str[i-1]+"\""+"}";
        }
        return fin;
    }

    public void clickend(View v)
    {
        // name the file with current time to prevent cover
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = format.format(new Date());
        String str2=".json";
        Context context = this;
        transam ta=(transam) getApplication();

        // save file in storage/emulated/0/Android/data/packagename/files
        String pathex = context.getExternalFilesDir(null).getAbsolutePath();
        File savedataex=new File(pathex,formatDate+str2);
        try {
            FileOutputStream foutex=new FileOutputStream(savedataex,true);
            try {
                foutex.write(change(ta.str).getBytes());
                foutex.flush();
                foutex.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // save file in data/data/packagename/files
        //File pathin=getFilesDir();
        try {
            FileOutputStream foutin = this.openFileOutput(formatDate+str2, MODE_PRIVATE);
            foutin.write(change(ta.str).getBytes());
            foutin.flush();
            foutin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainActivity.exit();
    }
}

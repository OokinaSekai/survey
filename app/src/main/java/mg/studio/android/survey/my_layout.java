package mg.studio.android.survey;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.res.AssetManager;
        import android.os.Bundle;
        import android.util.TypedValue;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
@SuppressWarnings("ResourceType")

public class my_layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout rootlayout=new RelativeLayout(this);
        rootlayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        rootlayout.setId(0);
        Context context=this;
        String pathex = context.getExternalFilesDir(null).getAbsolutePath();
        try {
            rootlayout=my_creat(rootlayout,pathex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(rootlayout);
        //setContentView(R.layout.activity_main);
    }

    //用来动态构建布局，保存问题的json文件需在asset目录下命名为sample.json
    public RelativeLayout my_creat(final RelativeLayout rootlayout, final String pathex) throws JSONException {
        String str;
        Context context = this;
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assets = context.getAssets();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("sample.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        str = stringBuilder.toString();

        //0层
        JSONObject obj = new JSONObject(str);//获取json

        //1层
        JSONObject survey=obj.getJSONObject("survey");//获取survey
        String id=survey.getString("id");//获取id
        String len=survey.getString("len");//获取len
        JSONArray questions=survey.getJSONArray("questions");//获取questions组
        //以上是关键信息？2层现用现调？2层能否动态构建？2层所有值都可以直接调，但用于布局该如何？循环创建？又该如何

        //尝试动态构建
        final int number=Integer.parseInt(len);//问题数
        TextView[]question=new TextView[number];//所有问题的文本内容
        RelativeLayout.LayoutParams []question_txt_Params=new RelativeLayout.LayoutParams[number];//所有问题文本的param
        final RadioGroup opts_question[]=new RadioGroup[number];//所有问题的选项
        RelativeLayout.LayoutParams []question_opts_Params=new RelativeLayout.LayoutParams[number];//所有问题选项的param

        //循环len次，创建布局以及提交按钮
        for(int i=0;i<number;i++)
        {
            //以下部分，用以获得当前问题的type，question，options
            JSONObject que=questions.getJSONObject(i);//获取当前问题的JSONObject
            String que_type=que.getString("type");//获取当前问题的type
            String que_que=que.getString("question");//获取当前问题的question内容
            JSONArray que_opt=que.getJSONArray("options");//获取当前问题的options组
            String []que_cho=new String[que_opt.length()];//新建string数组，保存当前问题的各个option
            for(int j=0;j<que_opt.length();j++)
            {
                que_cho[j]=que_opt.getJSONObject(j).getString(j+1+"");//获取当前问题每个option的文本
            }
            //以上部分，在切换问题时会自动更新

            //以下部分，尝试对每个问题的TextView动态创建设置
            question[i]=new TextView(this);//设置context
            question[i].setId(10*(i+1));//设置id为10*i
            question_txt_Params[i]=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//设置param
            //若为question1，则只margin-top，其他question需below于上一个question的opts，同时margin-left与margin-top
            if(i>0)
            {
                question_txt_Params[i].addRule(RelativeLayout.BELOW, opts_question[i - 1].getId());
                question_txt_Params[i].setMargins(dip2px(8),dip2px(15),0,0);
            }
            else if(i==0)
                question_txt_Params[i].setMargins(dip2px(8),0,0,0);
            question[i].setLayoutParams(question_txt_Params[i]);//绑定Param
            question[i].setText(que_que);//设置文字
            question[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,18);//设置文字大小

            //以下部分，尝试对RadioGroup动态创建设置
            opts_question[i]=new RadioGroup(this);//设置context
            question_opts_Params[i]=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//设置param
            question_opts_Params[i].addRule(RelativeLayout.BELOW, question[i].getId());//当前问题opts位置需低于当前问题question
            opts_question[i].setLayoutParams(question_opts_Params[i]);//绑定param
            opts_question[i].setId(20*(i+1)-1);//设置id，若问题一，则id=19
            int opts_len=que_opt.length();//当前问题的选项数
            RadioButton[]choices=new RadioButton[opts_len];//当前问题的选项组
            for(int k=0;k<opts_len;k++)
            {
                choices[k]=new RadioButton(this);//为每个选项设置context
                choices[k].setId(question[i].getId()+k+1);//为每个选项设置id，第一题选项id范围为11-18
                choices[k].setText(que_cho[k]);//设置每个选项的文本内容
                opts_question[i].addView(choices[k]);//把每个radiobutton添加到对应问题的radiongroup中
            }

            rootlayout.addView(question[i]);//把当前问题的文本内容添加进rootlayout
            rootlayout.addView(opts_question[i]);//把当前问题的选项添加进rootlayout
        }

        //提交按钮
        Button submit=new Button(this);
        submit.setId(10000);
        RelativeLayout.LayoutParams btn_Params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        btn_Params.setMargins(0,dip2px(542),0,0);
        btn_Params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        submit.setLayoutParams(btn_Params);
        submit.setText("fin");
        submit.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);

        //监听器，点击函数，将结果保存为result.json文件
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int []selected=new int[number]; //每道题目选中的选项号
                for(int i=0;i<number;i++)
                    selected[i]=opts_question[i].getCheckedRadioButtonId();

                //未被选中的题目会返回-1.检查是否所有题目都已被选择
                int flag=0;
                for(int i=0;i<number;i++)
                {
                    if(selected[i]==-1)
                        flag=1;
                }

                if(flag==1)     //若有未选择则提示
                {
                    Toast ts = Toast.makeText(getBaseContext(),"please choose",Toast.LENGTH_LONG);
                    ts.show();
                }
                else
                {
                    //获取选中按钮的内容
                    RadioButton []answer=new RadioButton[number];
                    for(int i=0;i<number;i++)
                        answer[i]=(RadioButton) findViewById(selected[i]);
                    String []result=new String[number];
                    for(int i=0;i<number;i++)
                        result[i]=answer[i].getText().toString();
                    String name="result.json";

                    //save file in storage/emulated/0/Android/data/packagename/files
                    String path = pathex;
                    File savedataex = new File(path, name);
                    FileOutputStream foutex = null;
                    try {
                        foutex = new FileOutputStream(savedataex, true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        foutex.write(change(result).getBytes());
                        foutex.flush();
                        foutex.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast ts = Toast.makeText(getBaseContext(),"save success!",Toast.LENGTH_LONG);
                    ts.show();
                }
            }
        });
        rootlayout.addView(submit);
        return rootlayout;
    }

    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //将string数组转换为符合json文件的格式
    public String change(String str[])
    {
        String fin="{";
        for(int i=1;i<str.length+1;i++)
        {
            if(i<str.length)
                fin=fin+"\"question"+i+"\":"+"\""+str[i-1]+"\""+",";
            if(i==str.length)
                fin=fin+"\"question"+i+"\":"+"\""+str[i-1]+"\""+"}";
        }
        return fin;
    }
}

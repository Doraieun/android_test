package doraieun.testapp4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by e900537 on 2015-12-03.
 */
public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        String id = getIntent().getStringExtra("inputId");
        System.out.println("입력ID : " + id);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("어서오세요. " + id + "님.");

        Button btnBack = (Button)findViewById(R.id.button);
        Button btnMapNaver = (Button)findViewById(R.id.button2);
        Button btnMapGoogle = (Button)findViewById(R.id.button4);
        Button btnTab = (Button)findViewById(R.id.button3);
        Button btnCamera = (Button) findViewById(R.id.button6);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMapNaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubActivity.this, MapNaverActivity.class));
                //finish();
            }
        });

        btnMapGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubActivity.this, MapGoogleActivity.class));
                //finish();
            }
        });

        btnTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubActivity.this, TabActivity.class));
                //finish();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubActivity.this, CameraActivity.class));
            }
        });
    }
}

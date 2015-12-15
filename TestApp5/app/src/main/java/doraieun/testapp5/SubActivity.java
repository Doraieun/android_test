package doraieun.testapp5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.nmapmodel.NMapError;

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
        Button btnMap = (Button)findViewById(R.id.button2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubActivity.this, MapActivity.class));
            }
        });
    }
}

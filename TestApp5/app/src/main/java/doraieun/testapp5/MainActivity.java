package doraieun.testapp5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Login Please!");

        Button btnLogin = (Button)findViewById(R.id.button);
        /*
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("clicked!");
                System.out.println(v.getId());

                //ID
                EditText editTextID = (EditText) findViewById(R.id.editTextID);
                //PASSWORD
                EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
                System.out.println(editTextID.getText());
                System.out.println(editTextPassword.getText());

                if (editTextID.equals(null) || editTextID.toString() == "") {
                    //alertDialog.setMessage("ID를 입력하시오.");
                }

                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("inputId", editTextID.getText().toString());
                startActivity(intent);

            }
        });
        */
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        System.out.println("view.getId() : " + view.getId());
        System.out.println("button Clicked!");

        //ID
        EditText editTextID = (EditText) findViewById(R.id.editTextID);
        //PASSWORD
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        System.out.println("ID : " + editTextID.getText());
        System.out.println("Password : " + editTextPassword.getText());

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("경고");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Dialog Button Clicked!");
                System.out.println("which : " + which);
            }
        });

        if (editTextID.equals(null) || "".equals(editTextID.getText().toString())) {
            System.out.println("Blank ID!");
            alertDialog.setMessage("ID를 입력하시오.");
            alertDialog.show();
        }else if (editTextPassword.equals(null) || "".equals(editTextPassword.getText().toString())) {
            System.out.println("Blank Password!");
            alertDialog.setMessage("Password를 입력하시오.");
            alertDialog.show();
        }else{
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            intent.putExtra("inputId", editTextID.getText().toString());
            startActivity(intent);
        }
    }
}

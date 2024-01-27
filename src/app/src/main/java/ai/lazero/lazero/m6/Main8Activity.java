package ai.lazero.lazero.m6;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.Toast;

import ai.lazero.lazero.MainActivity;
import ai.lazero.lazero.R;

public class Main8Activity extends AppCompatActivity {

    private Button button;
    private Button button_v0;
    private String random ="我未被劫持";

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main8);

        button = (Button) findViewById(R.id.button_v0);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Toast.makeText(Main8Activity.this, toastMessage(random), Toast.LENGTH_SHORT).show();

            }

        });
        button_v0 = (Button) findViewById(R.id.button_v1);

        button_v0.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Toast.makeText(Main8Activity.this, toastMessage_v2(random), Toast.LENGTH_SHORT).show();

            }

        });

    }

    public String toastMessage(String random) {

        return random;

    }
    public String toastMessage_v2(String random) {

        return random;

    }

}
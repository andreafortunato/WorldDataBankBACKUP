package it.apperol.group.worlddatabank.myactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.apperol.group.worlddatabank.R;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    public static TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn= findViewById(R.id.btn);
        tv1 = findViewById(R.id.tv1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FetchData process = new FetchData();
                //process.execute();
                Intent i = new Intent(MainActivity.this, CountryActivity.class);
                startActivity(i);
            }
        });
    }


}



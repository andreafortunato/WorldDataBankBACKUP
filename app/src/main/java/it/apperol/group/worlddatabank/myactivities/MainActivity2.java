package it.apperol.group.worlddatabank.myactivities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.apperol.group.worlddatabank.R;

public class MainActivity2 extends AppCompatActivity {

    private Button btn;

    public static TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn= findViewById(R.id.btn);
        tvTitle = findViewById(R.id.tvTitle);


        // Il seguente blocco, fino alla riga [93], serve solamente a colorare la scritta 'Google Play Services Api'
        tvTitle.measure(0,0); // Faccio partire le 'misure' (come fosse un righello) da 0
        Shader textShader = new LinearGradient(0, (float)tvTitle.getMeasuredHeight()/2, (float)tvTitle.getMeasuredWidth(), (float)tvTitle.getMeasuredHeight()/2,
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP); // Dalla riga [84] fino a questa riga creo un 'modello di pittura', cioè una sfumatura di colori
        tvTitle.getPaint().setShader(textShader); // Assegno alla MyTextView 'tvAppName' la sfumatura 'textShader' precedentemente creata
        tvTitle.setTextColor(Color.parseColor("#FFFFFF")); // Imposto un colore di base per la MyTextView 'tvAppName'



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FetchData process = new FetchData();
                //process.execute();
                Intent i = new Intent(MainActivity2.this, CountryActivity.class);
                startActivity(i);
            }
        });
    }


}



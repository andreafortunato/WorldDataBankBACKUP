package it.apperol.group.worlddatabank;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import it.apperol.group.worlddatabank.myactivities.CountryActivity;
import it.apperol.group.worlddatabank.myviews.MyTextView;

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private View myView;
    private MyTextView myTvTitle, myTvChooseInfo;
    private MaterialButton mbCoArIn, mbArInCo;
    private boolean isTextViewClicked = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.welcome_layout, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        myTvTitle = Objects.requireNonNull(getActivity()).findViewById(R.id.myTvTitle);
        myTvChooseInfo = Objects.requireNonNull(getActivity()).findViewById(R.id.myTvChooseInfo);
        mbCoArIn = Objects.requireNonNull(getActivity()).findViewById(R.id.mbCoArIn);
        mbArInCo = Objects.requireNonNull(getActivity()).findViewById(R.id.mbArInCo);

        myTvTitle.setText(String.format("%s App", myTvTitle.getText()));

        // Il seguente blocco, fino alla riga [93], serve solamente a colorare la scritta 'Google Play Services Api'
        myTvTitle.measure(0,0); // Faccio partire le 'misure' (come fosse un righello) da 0
        Shader textShader = new LinearGradient(0, (float)myTvTitle.getMeasuredHeight()/2, (float)myTvTitle.getMeasuredWidth(), (float)myTvTitle.getMeasuredHeight()/2,
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP); // Dalla riga [84] fino a questa riga creo un 'modello di pittura', cioè una sfumatura di colori
        myTvTitle.getPaint().setShader(textShader); // Assegno alla MyTextView 'tvAppName' la sfumatura 'textShader' precedentemente creata
        myTvTitle.setTextColor(Color.parseColor("#FFFFFF")); // Imposto un colore di base per la MyTextView 'tvAppName'

        myTvChooseInfo.setOnClickListener(this);
        mbCoArIn.setOnClickListener(this);
        mbArInCo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mbCoArIn:
                Toast.makeText(getActivity(),"mbCoArIn", Toast.LENGTH_SHORT).show();
                Intent countryIntent = new Intent(getActivity(), CountryActivity.class);
                startActivity(countryIntent);
                break;
            case R.id.mbArInCo:
                Toast.makeText(getActivity(),"mbArInCo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myTvChooseInfo:

                if(isTextViewClicked){
                    ObjectAnimator animation = ObjectAnimator.ofInt(
                            myTvChooseInfo,
                            "maxLines",
                            1);
                    animation.setDuration(300);
                    animation.start();
                    isTextViewClicked = false;
                } else {
                    ObjectAnimator animation = ObjectAnimator.ofInt(
                            myTvChooseInfo,
                            "maxLines",
                            myTvChooseInfo.getLineCount());
                    animation.setDuration(300);
                    animation.start();
                    isTextViewClicked = true;
                }
        }
    }
}

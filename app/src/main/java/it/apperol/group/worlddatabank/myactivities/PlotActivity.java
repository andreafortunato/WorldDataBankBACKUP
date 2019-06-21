package it.apperol.group.worlddatabank.myactivities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.myadapters.MyCountryAdapter;
import it.apperol.group.worlddatabank.myadapters.MyIndicatorAdapter;
import it.apperol.group.worlddatabank.myobjects.PlotObj;
import it.apperol.group.worlddatabank.mythreads.FetchData;

public class PlotActivity extends AppCompatActivity {

    private List<PlotObj> plotDatas = new ArrayList<>();
    LineChart mpLineChart;
    int colorArray[] = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    //String[] legendName = {"Cazzo","Buddha","PadrePio","Salveenee"};
    private JSONArray ja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mpLineChart =(LineChart) findViewById(R.id.line_chart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Dio Grafico 1");  //Grafico a linee 1
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();    // insieme di tutti i dati
        dataSets.add(lineDataSet1);         //aggiungo dati 1

        //mpLineChart.setBackgroundColor(Color.GREEN); // Colore sfondo
        mpLineChart.setNoDataText("No Data");        // NO DATA se non ci sono valori
        mpLineChart.setNoDataTextColor(Color.BLUE);  // Colore di No Data

        mpLineChart.setDrawGridBackground(true);
        mpLineChart.setDrawBorders(true);            // Bordo Grafico
        mpLineChart.setBorderColor(Color.RED);       // Colore Bordo Grafico
        mpLineChart.setBorderWidth(1);              // spessore bordo
        lineDataSet1.setColor(Color.RED);            // Colore di linea 1

        Legend legend = mpLineChart.getLegend();      //legenda del grafico
        legend.setEnabled(true);
        legend.setTextColor(Color.RED);
        legend.setTextSize(10);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10);
        legend.setXEntrySpace(5);                     //spazio tra 2 text nella legenda
        legend.setFormToTextSpace(10);                  //spazio tra icona e testo

        LegendEntry[] legendEntries = new LegendEntry[4];   //valori nella legenda

        for (int i=0; i<legendEntries.length; i++)
        {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colorArray[i];
            //entry.label = String.valueOf(legendName[i]);
            legendEntries[i] = entry;

        }

        //legend.setCustom(legendEntries);     //Customizzare la legenda con legendEntries

        Description description = new Description();       //Descrizione da aggiungere sul grafico
        description.setText("Porcoddio");
        description.setTextColor(Color.GREEN);
        description.setTextSize(15);
        mpLineChart.setDescription(description);         //rende visibile la descrizione sul grafico


        LineData data = new LineData(dataSets);    //grafio a linee
        mpLineChart.setData(data);                //imposta dati nel grafico
        mpLineChart.invalidate();
    }
    private ArrayList<Entry> dataValues1()                  //LISTA DI VALORI 1
    {
        getDatas();

        // [TEST] Stampa valori grafico
        String dataaa = "";
        for(int i=0; i < plotDatas.size(); i++) {
            dataaa += "Year: " + plotDatas.get(plotDatas.size()-i-1).getYear() + "Value: " + plotDatas.get(plotDatas.size()-i-1).getValue() + "\n";
        }
        Log.i("[TAG]", dataaa);
        // [TEST] Fine

        ArrayList<Entry> dataVals = new ArrayList<>();
        for(int i = 0; i < plotDatas.size(); i++) {
            dataVals.add(new Entry(Float.parseFloat(plotDatas.get(plotDatas.size()-i-1).getYear()), Float.parseFloat(plotDatas.get(plotDatas.size()-i-1).getValue())));
        }
        return dataVals;
    }

    private ArrayList<Entry> dataValues2(){              // LISTA DI VALORI 2

        ArrayList<Entry> dataVals = new ArrayList<>();

        dataVals.add(new Entry(0 , 12));
        dataVals.add(new Entry(2 , 16));
        dataVals.add(new Entry(3 , 23));
        dataVals.add(new Entry(5 , 1));
        dataVals.add(new Entry(7 , 18));
        dataVals.add(new Entry(9 , 10));
        return dataVals;
    }

    private void getDatas() {
        FetchData process = new FetchData("http://api.worldbank.org/v2/country/" + MyCountryAdapter.countryIso2Code + "/indicator/" + MyIndicatorAdapter.indicatorID + "?format=json");
        try {
            process.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ja = FetchData.ja;

        try {
            for(int i = 0; i < ja.getJSONArray(1).length(); i++){
                JSONObject jo = (JSONObject) ja.getJSONArray(1).get(i);
                if(!jo.get("value").equals("") && jo.get("value") != null && !jo.get("value").equals("null") && !jo.isNull("value")) {
                    plotDatas.add(new PlotObj(jo.get("date").toString(), jo.get("value").toString()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plot, menu);
        return true;
    }
}

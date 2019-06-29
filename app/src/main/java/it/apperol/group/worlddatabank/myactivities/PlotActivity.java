package it.apperol.group.worlddatabank.myactivities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import it.apperol.group.worlddatabank.R;
import it.apperol.group.worlddatabank.SaveShareDialog;
import it.apperol.group.worlddatabank.myadapters.MyCountryAdapter;
import it.apperol.group.worlddatabank.myadapters.MyIndicatorAdapter;
import it.apperol.group.worlddatabank.myobjects.PlotObj;
import it.apperol.group.worlddatabank.mythreads.FetchData;

public class PlotActivity extends AppCompatActivity {

    private static Context plotActivityContext;

    private static List<PlotObj> plotDatas = new ArrayList<>();
    static LineChart mpLineChart;
    static int[] colorArray = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    //String[] legendName = {"Cazzo","Buddha","PadrePio","Salveenee"};
    private static JSONArray ja;
    private static ArrayList<Entry> dataVals;

    private SaveShareDialog saveShareDialog = new SaveShareDialog();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        plotActivityContext = this;

        ja = MyIndicatorAdapter.ja;

        setContentView(R.layout.activity_plot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: MOSTRARE DIALOGBOX "SALVARE/CONDIVIDERE? SI/NO"

                saveShareDialog.show(getSupportFragmentManager(),"mySaveShareDialog");

                Snackbar.make(view, "Da eliminare", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mpLineChart =(LineChart) findViewById(R.id.line_chart);

        //FetchData process = new FetchData("http://api.worldbank.org/v2/country/" + MyCountryAdapter.countryIso2Code + "/indicator/" + MyIndicatorAdapter.indicatorID + "?format=json", this, 3);
        //process.execute();

        fetchPlot();

    }

    private static ArrayList<Entry> dataValues1()
    {
        getDatas();

        // Riordino i dati secondo l'anno (ordine crescente)
        Collections.sort(plotDatas, new Comparator<PlotObj>() {
            @Override
            public int compare(PlotObj o1, PlotObj o2) {
                return o1.getYear().compareToIgnoreCase(o2.getYear());
            }
        });

        ArrayList<Entry> dataVals = new ArrayList<>();
        for(int i = 0; i < plotDatas.size(); i++) {
            dataVals.add(new Entry(Float.parseFloat(plotDatas.get(i).getYear()), Float.parseFloat(plotDatas.get(i).getValue())));
        }

        return dataVals;
    }

    @Override
    protected void onPause() {
        super.onPause();
        plotDatas = new ArrayList<>();
    }

    @Override
    protected void onStop() {
        super.onStop();
        plotDatas = new ArrayList<>();
    }

    private static void getDatas() {

        try {
            for(int i = 0; i < ja.getJSONArray(1).length(); i++){
                JSONObject jo = (JSONObject) ja.getJSONArray(1).get(i);
                if(!jo.get("value").equals("") && jo.get("value") != null && !jo.get("value").equals("null") && !jo.isNull("value")) {
                    plotDatas.add(new PlotObj(jo.get("date").toString(), jo.get("value").toString()));
                }
            }
        }catch (Exception e){
            Log.i("[CRASH]", "CRASH");
            e.printStackTrace();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plot, menu);
        return true;
    }

    private void fetchPlot() {

        dataVals = dataValues1();
        LineDataSet lineDataSet1 = new LineDataSet(dataVals, "Dio Grafico 1");  //Grafico a linee 1
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
        mpLineChart.setDescription(description);             //rende visibile la descrizione sul grafico

        // Imposta con che grana verranno mostrati gli anni ('1f' corrissponde a: di anno in anno, '2f' un anno si ed uno no, '3f' un anno si e tre no, ecc...)
        XAxis xAxis = mpLineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(true);


        LineData data = new LineData(dataSets);    //grafio a linee
        mpLineChart.setData(data);                //imposta dati nel grafico
        mpLineChart.invalidate();
    }
}

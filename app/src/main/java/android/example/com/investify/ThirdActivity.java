package android.example.com.investify;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    ArrayList<Integer> source = new ArrayList<Integer>();
    RadioGroup radioGroup;
    RadioButton oneYear;
    RadioButton threeYear;
    RadioButton fiveYear;
    GraphView graph;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        
        fillSpinnerData();

        final Spinner spin = (Spinner) findViewById(R.id.spinNumber);


        graph = (GraphView) findViewById(R.id.graph);
        radioGroup = (RadioGroup) findViewById(R.id.rgYearChoice);
//        oneYear = (RadioButton) findViewById(R.id.rbOneYear);
//        threeYear = (RadioButton) findViewById(R.id.rbThreeYear);
//        fiveYear = (RadioButton) findViewById(R.id.rbFiveYear);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbOneYear)
                    oneYear();
                    else if(checkedId == R.id.rbThreeYear)
                Toast.makeText(getApplicationContext(),"Three Year",Toast.LENGTH_LONG).show();
                    else
                Toast.makeText(getApplicationContext(),"Five Year",Toast.LENGTH_LONG).show();

            }
        });



        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, source);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);




    }

    private void oneYear() {
        List<Float> values = new ArrayList<>();
        values.add(-1.96f);
        values.add(4.61f);
        values.add(-3.71f);
        values.add(-3.32f);
        values.add(-2.37f);
        values.add(-6.94f);
        values.add(2.37f);
        values.add(5.07f);
        values.add(-4.45f);
        values.add(5.90f);
        values.add(0.36f);
        values.add(-0.02f);


        // This will display the line in the chart
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(1, values.get(0)),
                new DataPoint(2, values.get(1)),
                new DataPoint(3, values.get(2)),
                new DataPoint(4, values.get(3)),
                new DataPoint(5, values.get(4)),
                new DataPoint(6, values.get(5)),
                new DataPoint(7, values.get(6)),
                new DataPoint(8, values.get(7)),
                new DataPoint(9, values.get(8)),
                new DataPoint(10, values.get(9)),
                new DataPoint(11, values.get(10)),
                new DataPoint(12, values.get(11))

        });

        series.setDrawBackground(true); // Shaded the area under the line
        series.setDrawDataPoints(true); // To draw the points for the line
        series.setAnimated(true);
        series.setColor(Color.rgb(186, 1, 38));

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getViewport().setScrollable(true);

        graph.addSeries(series);

    }


    private void fillSpinnerData() {
        source.add(1);
        source.add(3);
        source.add(5);
    }

    //To activate the menu on this activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.second_menu,menu);
        return true;
    }


    // A temporary method for the selected item from the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us_id:
                Toast.makeText(this, "About Us ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact_us_id:
                Toast.makeText(this, "Contact Us ", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package android.example.com.investify;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity  {

    double [][]value = {{1,-1.96},{2,4.61},{3,-3.71},{4,-3.32},{5,-2.37},{6,-6.94},{7,2.37},{8,5.07},{9,-4.45},{10,5.90},{11,0.36},{12,-0.02}};
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

        Intent i = getIntent();
        Company selectedCompany = (Company) i.getSerializableExtra("company");
        Toast.makeText(this,selectedCompany.getName().toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,selectedCompany.perfValues.get("2017").get(11).toString(),Toast.LENGTH_LONG).show();

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
                threeYear();
                    else
                Toast.makeText(getApplicationContext(),"Five Year",Toast.LENGTH_LONG).show();

            }
        });



        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, source);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);




    }

    private void threeYear() {

        graph.removeAllSeries();
        List<Float> values2 = new ArrayList<>();
        values2.add(-1.96f);
        values2.add(4.61f);
        values2.add(-3.71f);
        values2.add(-3.32f);
        values2.add(-2.37f);
        values2.add(-6.94f);
        values2.add(2.37f);
        values2.add(5.07f);
        values2.add(-4.45f);
        values2.add(5.90f);
        values2.add(0.36f);
        values2.add(-0.02f);
//
        values2.add(2.16f);
        values2.add(3.49f);
        values2.add(-0.99f);
        values2.add(-0.08f);
        values2.add(-4.59f);
        values2.add(10.23f);
        values2.add(2.73f);
        values2.add(-5.21f);
        values2.add(-1.64f);
        values2.add(-6.29f);
        values2.add(-2.97f);
        values2.add(0.05f);
//
        values2.add(6.11f);
        values2.add(0.32f);
        values2.add(3.53f);
        values2.add(-6.18f);
        values2.add(-1.64f);
        values2.add(-6.06f);
        values2.add(4.11f);
        values2.add(-7f);
        values2.add(2.2f);
        values2.add(-1.68f);
        values2.add(2.98f);
        values2.add(-3.85f);



        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, values2.get(0)),
                new DataPoint(2, values2.get(1)),
                new DataPoint(3, values2.get(2)),
                new DataPoint(4, values2.get(3)),
                new DataPoint(5, values2.get(4)),
                new DataPoint(6, values2.get(5)),
                new DataPoint(7, values2.get(6)),
                new DataPoint(8, values2.get(7)),
                new DataPoint(9, values2.get(8)),
                new DataPoint(10, values2.get(9)),
                new DataPoint(11, values2.get(10)),
                new DataPoint(12, values2.get(11)),
                new DataPoint(13, values2.get(12)),
                new DataPoint(14, values2.get(12)),
                new DataPoint(15, values2.get(14)),
                new DataPoint(16, values2.get(15)),
                new DataPoint(17, values2.get(16)),
                new DataPoint(18, values2.get(17)),
                new DataPoint(19, values2.get(18)),
                new DataPoint(20, values2.get(19)),
                new DataPoint(21, values2.get(20)),
                new DataPoint(22, values2.get(21)),
                new DataPoint(23, values2.get(22)),
                new DataPoint(24, values2.get(23)),
                new DataPoint(25, values2.get(24)),
                new DataPoint(26, values2.get(25)),
                new DataPoint(27, values2.get(26)),
                new DataPoint(28, values2.get(27)),
                new DataPoint(29, values2.get(28)),
                new DataPoint(30, values2.get(29)),
                new DataPoint(31, values2.get(30)),
                new DataPoint(32, values2.get(31)),
                new DataPoint(33, values2.get(32)),
                new DataPoint(34, values2.get(33)),
                new DataPoint(35, values2.get(34)),
                new DataPoint(36, values2.get(35))

        });

        SimpleRegression simpleRegression3 = new SimpleRegression();

        for(int i=1;i<=values2.size();i++)
            simpleRegression3.addData(i,values2.get(i-1));

        double intercept3 = simpleRegression3.getIntercept();
        double slope3 = simpleRegression3.getSlope();
        double y3 = intercept3 + slope3 * 36 ;
        Double[] xAxis3 = new Double[2];
        Double[] yAxis3 = new Double[2];
        xAxis3[0]=0d;    yAxis3[0]=intercept3;
        xAxis3[1]=36d;   yAxis3[1]=y3;

        GraphView line_graph3 = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoints3 = new DataPoint[xAxis3.length];
        for (int i = 0; i < xAxis3.length; i++)
        {
            dataPoints3[i] = new DataPoint(xAxis3[i],yAxis3[i]);
        }
        LineGraphSeries<DataPoint> line_series3 = new LineGraphSeries<DataPoint>(dataPoints3);
        line_series3.setColor(Color.rgb(255, 0, 0));
        line_graph3.addSeries(line_series3);





        series2.setDrawBackground(true); // Shaded the area under the line
        //series2.setDrawDataPoints(true); // To draw the points for the line
        series2.setAnimated(true);
        series2.setColor(Color.rgb(0, 0, 255));


        graph.getViewport().setXAxisBoundsManual(true);
        //graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(36);
        graph.getViewport().setScrollable(true);



        StaticLabelsFormatter staticLabelsFormatter2 = new StaticLabelsFormatter(graph);
        //staticLabelsFormatter.setHorizontalLabels(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);



        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX) + "m";
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + "%";
                }
            }
        });


        graph.addSeries(series2);

    }

    private void oneYear() {

        graph.removeAllSeries();
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
        series.setDrawDataPoints(false); // To draw the points for the line
        series.setAnimated(true);
        series.setColor(Color.rgb(186, 1, 38));

        // Best fit line

        SimpleRegression simpleRegression = new SimpleRegression();
        simpleRegression.addData(value);
        double intercept = simpleRegression.getIntercept();
        double slope = simpleRegression.getSlope();
        double y = intercept + slope * 12 ;
        Double[] xAxis = new Double[2];
        Double[] yAxis = new Double[2];
        xAxis[0]=0d;    yAxis[0]=intercept;
        xAxis[1]=12d;   yAxis[1]=y;


        GraphView line_graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoints = new DataPoint[xAxis.length];
        for (int i = 0; i < xAxis.length; i++)
        {
            dataPoints[i] = new DataPoint(xAxis[i],yAxis[i]);
        }
        LineGraphSeries<DataPoint> line_series = new LineGraphSeries<DataPoint>(dataPoints);
        series.setColor(Color.rgb(0,0,255));
        line_series.setColor(Color.rgb(255, 0, 0));
        line_graph.addSeries(line_series);

        // End of best fit line

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        //staticLabelsFormatter.setHorizontalLabels(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX) + "m";
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + "%";
                }
            }
        });

        graph.getViewport().setScrollable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(12);
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

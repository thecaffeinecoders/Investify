package android.example.com.investify;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    double [][]value = {{1,-1.96},{2,4.61},{3,-3.71},{4,-3.32},{5,-2.37},{6,-6.94},{7,2.37},{8,5.07},{9,-4.45},{10,5.90},{11,0.36},{12,-0.02}};
    ArrayList<Integer> source = new ArrayList<Integer>();
    RadioGroup radioGroup;
    RadioButton oneYear;
    RadioButton threeYear;
    RadioButton fiveYear;
    GraphView graph;
    TextView tvCompDesc;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        
        fillSpinnerData();

        final Spinner spin = (Spinner) findViewById(R.id.spinNumber);

        tvCompDesc=(TextView) findViewById(R.id.tvCompDesc);
        tvCompDesc.setMovementMethod(new ScrollingMovementMethod());
        tvCompDesc.setText("Lynx Asset Management was founded in 1999 and is today one of the world's leading firms in model-based asset management.\n" +
                "\n" +
                "Our investment process is entirely systematic and based on proprietary developed models that identify trends and other patterns in financial markets. Our objective is to deliver high risk-adjusted returns with low correlation to traditional asset classes.");


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
                    fiveYear();

            }
        });



        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, source);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);




    }

    private void fiveYear(){
        graph.removeAllSeries();
        List<Float> values5 = new ArrayList<>();
        values5.add(-1.96f);
        values5.add(4.61f);
        values5.add(-3.71f);
        values5.add(-3.32f);
        values5.add(-2.37f);
        values5.add(-6.94f);
        values5.add(2.37f);
        values5.add(5.07f);
        values5.add(-4.45f);
        values5.add(5.90f);
        values5.add(0.36f);
        values5.add(-0.02f);
//
        values5.add(2.16f);
        values5.add(3.49f);
        values5.add(-0.99f);
        values5.add(-0.08f);
        values5.add(-4.59f);
        values5.add(10.23f);
        values5.add(2.73f);
        values5.add(-5.21f);
        values5.add(-1.64f);
        values5.add(-6.29f);
        values5.add(-2.97f);
        values5.add(0.05f);
//
        values5.add(6.11f);
        values5.add(0.32f);
        values5.add(3.53f);
        values5.add(-6.18f);
        values5.add(-1.64f);
        values5.add(-6.06f);
        values5.add(4.11f);
        values5.add(-7f);
        values5.add(2.2f);
        values5.add(-1.68f);
        values5.add(2.98f);
        values5.add(-3.85f);
//
        values5.add(-5.29f);
        values5.add(4.14f);
        values5.add(-3f);
        values5.add(0.80f);
        values5.add(3.09f);
        values5.add(0.88f);
        values5.add(0.15f);
        values5.add(9.17f);
        values5.add(2.93f);
        values5.add(1.55f);
        values5.add(9.89f);
        values5.add(1.29f);
//
        values5.add(3.60f);
        values5.add(-0.19f);
        values5.add(0.73f);
        values5.add(3.30f);
        values5.add(-1.69f);
        values5.add(-5.39f);
        values5.add(0.56f);
        values5.add(-2.78f);
        values5.add(0.60f);
        values5.add(4.71f);
        values5.add(5.32f);
        values5.add(3.30f);

        LineGraphSeries<DataPoint> series5 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, values5.get(0)),
                new DataPoint(2, values5.get(1)),
                new DataPoint(3, values5.get(2)),
                new DataPoint(4, values5.get(3)),
                new DataPoint(5, values5.get(4)),
                new DataPoint(6, values5.get(5)),
                new DataPoint(7, values5.get(6)),
                new DataPoint(8, values5.get(7)),
                new DataPoint(9, values5.get(8)),
                new DataPoint(10, values5.get(9)),
                new DataPoint(11, values5.get(10)),
                new DataPoint(12, values5.get(11)),
                new DataPoint(13, values5.get(12)),
                new DataPoint(14, values5.get(12)),
                new DataPoint(15, values5.get(14)),
                new DataPoint(16, values5.get(15)),
                new DataPoint(17, values5.get(16)),
                new DataPoint(18, values5.get(17)),
                new DataPoint(19, values5.get(18)),
                new DataPoint(20, values5.get(19)),
                new DataPoint(21, values5.get(20)),
                new DataPoint(22, values5.get(21)),
                new DataPoint(23, values5.get(22)),
                new DataPoint(24, values5.get(23)),
                new DataPoint(25, values5.get(24)),
                new DataPoint(26, values5.get(25)),
                new DataPoint(27, values5.get(26)),
                new DataPoint(28, values5.get(27)),
                new DataPoint(29, values5.get(28)),
                new DataPoint(30, values5.get(29)),
                new DataPoint(31, values5.get(30)),
                new DataPoint(32, values5.get(31)),
                new DataPoint(33, values5.get(32)),
                new DataPoint(34, values5.get(33)),
                new DataPoint(35, values5.get(34)),
                new DataPoint(36, values5.get(35)),
                new DataPoint(37, values5.get(36)),
                new DataPoint(38, values5.get(37)),
                new DataPoint(39, values5.get(38)),
                new DataPoint(40, values5.get(39)),
                new DataPoint(41, values5.get(40)),
                new DataPoint(42, values5.get(41)),
                new DataPoint(43, values5.get(42)),
                new DataPoint(44, values5.get(43)),
                new DataPoint(45, values5.get(44)),
                new DataPoint(46, values5.get(45)),
                new DataPoint(47, values5.get(46)),
                new DataPoint(48, values5.get(47)),
                new DataPoint(49, values5.get(48)),
                new DataPoint(50, values5.get(49)),
                new DataPoint(51, values5.get(50)),
                new DataPoint(52, values5.get(51)),
                new DataPoint(53, values5.get(52)),
                new DataPoint(54, values5.get(53)),
                new DataPoint(55, values5.get(54)),
                new DataPoint(56, values5.get(55)),
                new DataPoint(57, values5.get(56)),
                new DataPoint(58, values5.get(57)),
                new DataPoint(59, values5.get(58)),
                new DataPoint(60, values5.get(59))

        });


        SimpleRegression simpleRegression5 = new SimpleRegression();

        for(int i=1;i<=values5.size();i++)
            simpleRegression5.addData(i,values5.get(i-1));

        double intercept5 = simpleRegression5.getIntercept();
        double slope5 = simpleRegression5.getSlope();
        double y5 = intercept5 + slope5 * 60 ;
        Double[] xAxis5 = new Double[2];
        Double[] yAxis5 = new Double[2];
        xAxis5[0]=0d;    yAxis5[0]=intercept5;
        xAxis5[1]=60d;   yAxis5[1]=y5;

        GraphView line_graph5 = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoints5 = new DataPoint[xAxis5.length];
        for (int i = 0; i < xAxis5.length; i++)
        {
            dataPoints5[i] = new DataPoint(xAxis5[i],yAxis5[i]);
        }
        LineGraphSeries<DataPoint> line_series5 = new LineGraphSeries<DataPoint>(dataPoints5);
        line_series5.setColor(Color.rgb(255, 0, 0));
        line_graph5.addSeries(line_series5);





        series5.setDrawBackground(true); // Shaded the area under the line
        //series2.setDrawDataPoints(true); // To draw the points for the line
        series5.setAnimated(true);
        series5.setColor(Color.rgb(0, 0, 255));


        graph.getViewport().setXAxisBoundsManual(true);
        //graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(60);
        graph.getViewport().setScrollable(true);



        StaticLabelsFormatter staticLabelsFormatter5 = new StaticLabelsFormatter(graph);
        //staticLabelsFormatter.setHorizontalLabels(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter5);



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


        graph.addSeries(series5);


    }

    private void threeYear() {

        graph.removeAllSeries();
        List<Float> values3 = new ArrayList<>();
        values3.add(-1.96f);
        values3.add(4.61f);
        values3.add(-3.71f);
        values3.add(-3.32f);
        values3.add(-2.37f);
        values3.add(-6.94f);
        values3.add(2.37f);
        values3.add(5.07f);
        values3.add(-4.45f);
        values3.add(5.90f);
        values3.add(0.36f);
        values3.add(-0.02f);
//
        values3.add(2.16f);
        values3.add(3.49f);
        values3.add(-0.99f);
        values3.add(-0.08f);
        values3.add(-4.59f);
        values3.add(10.23f);
        values3.add(2.73f);
        values3.add(-5.21f);
        values3.add(-1.64f);
        values3.add(-6.29f);
        values3.add(-2.97f);
        values3.add(0.05f);
//
        values3.add(6.11f);
        values3.add(0.32f);
        values3.add(3.53f);
        values3.add(-6.18f);
        values3.add(-1.64f);
        values3.add(-6.06f);
        values3.add(4.11f);
        values3.add(-7f);
        values3.add(2.2f);
        values3.add(-1.68f);
        values3.add(2.98f);
        values3.add(-3.85f);



        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, values3.get(0)),
                new DataPoint(2, values3.get(1)),
                new DataPoint(3, values3.get(2)),
                new DataPoint(4, values3.get(3)),
                new DataPoint(5, values3.get(4)),
                new DataPoint(6, values3.get(5)),
                new DataPoint(7, values3.get(6)),
                new DataPoint(8, values3.get(7)),
                new DataPoint(9, values3.get(8)),
                new DataPoint(10, values3.get(9)),
                new DataPoint(11, values3.get(10)),
                new DataPoint(12, values3.get(11)),
                new DataPoint(13, values3.get(12)),
                new DataPoint(14, values3.get(12)),
                new DataPoint(15, values3.get(14)),
                new DataPoint(16, values3.get(15)),
                new DataPoint(17, values3.get(16)),
                new DataPoint(18, values3.get(17)),
                new DataPoint(19, values3.get(18)),
                new DataPoint(20, values3.get(19)),
                new DataPoint(21, values3.get(20)),
                new DataPoint(22, values3.get(21)),
                new DataPoint(23, values3.get(22)),
                new DataPoint(24, values3.get(23)),
                new DataPoint(25, values3.get(24)),
                new DataPoint(26, values3.get(25)),
                new DataPoint(27, values3.get(26)),
                new DataPoint(28, values3.get(27)),
                new DataPoint(29, values3.get(28)),
                new DataPoint(30, values3.get(29)),
                new DataPoint(31, values3.get(30)),
                new DataPoint(32, values3.get(31)),
                new DataPoint(33, values3.get(32)),
                new DataPoint(34, values3.get(33)),
                new DataPoint(35, values3.get(34)),
                new DataPoint(36, values3.get(35))

        });

        SimpleRegression simpleRegression3 = new SimpleRegression();

        for(int i=1;i<=values3.size();i++)
            simpleRegression3.addData(i,values3.get(i-1));

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
        line_series.setTitle("Best fit line");
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

package android.example.com.investify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import android.text.method.ScrollingMovementMethod;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class ThirdActivity extends AppCompatActivity  {

    public Company selectedCompany; // The selected company from the list in the 2nd Activity object
    ArrayList<Integer> source = new ArrayList<Integer>(); // The Spinner data
    List<Double> values=new ArrayList<>(); // The arraylist contains the datapoint for the simple re
    RadioGroup radioGroup;
    GraphView graph;
    TextView tvCompDesc;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        
        fillSpinnerData();
        final Spinner spin = (Spinner) findViewById(R.id.spinNumber);

        Intent intent = getIntent();
        selectedCompany = (Company) intent.getSerializableExtra("company"); // The receiving onject from the 2nd activity


        TextView tvComName = (TextView)findViewById(R.id.tvComName); // Company name TextView
        tvComName.setText(selectedCompany.name);

        ImageView imgCompLogo = (ImageView)findViewById(R.id.imgCompLogo); // Company Logo ImageView
        Glide.with(this).asBitmap().load(selectedCompany.logoLInk).apply(fitCenterTransform()).into(imgCompLogo); // Load the image

        tvCompDesc=(TextView) findViewById(R.id.tvCompDesc); // Company Description TextView
        tvCompDesc.setMovementMethod(new ScrollingMovementMethod());
        tvCompDesc.setText(selectedCompany.description);

        graph = (GraphView) findViewById(R.id.graph);  // The Chart graph
        radioGroup = (RadioGroup) findViewById(R.id.rgYearChoice);  // Radio buttons for the year options

        /**
         * A method to call the chartDispaly() based on the selected year option to draw the chart points and line
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbOneYear)
                    chartDisplay(1);
                else if(checkedId == R.id.rbThreeYear)
                    chartDisplay(3);
                else
                    chartDisplay(5);

            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, source); // Spinner adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }


    /**
     * A method to draw the chart series and the lines using the SimpleRegression Class
     * @param year Integer value to select drawing the chart based on the year choice
     */
    private void chartDisplay(int year) {

        graph.removeAllSeries(); // clear the graph each time calling the method
        values.clear(); // Clear the datapoint values to re fill it again

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        HashMap<Integer,ArrayList<Double>> perValues = selectedCompany.performance();

        switch (year)
        {
            case 1:
                for(int j=year ; j >0; j--)
                    for(int i=0;i<12;i++)
                        values.add(perValues.get(currentYear-j).get(i)); //Filling the datapoint values ArrayList
                break;
            case 3:
                for(int j=year ; j >0; j--)
                    for(int i=0;i<12;i++)
                        values.add(perValues.get(currentYear-j).get(i)); //Filling the datapoint values ArrayList
                break;
            case 5:
                for(int j=year ; j >0; j--)
                    for(int i=0;i<12;i++)
                        values.add(perValues.get(currentYear-j).get(i)); //Filling the datapoint values ArrayList
                break;
        }


        SimpleRegression simpleRegression = new SimpleRegression();
        DataPoint[] dataPoints = new DataPoint[values.size()]; // Creating the DataPoint based the year option
        for (int i = 0; i < values.size(); i++) {
            dataPoints[i] = new DataPoint(i + 1, values.get(i)); // Filling the Data Point [X][Y] from the values ArrayList
            simpleRegression.addData(i+1,values.get(i)); // Feeding the simpleRegression with the data to get the slope and the intercept
        }

        // This will display the line in the chart
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        series.setDrawBackground(true); // Shaded the area under the line
        series.setDrawDataPoints(false); // To draw the points for the line
        series.setAnimated(true);
        series.setColor(Color.rgb(186, 1, 38));
        series.setOnDataPointTapListener(new OnDataPointTapListener() {

            /**
             * A method to show a Toast message on click on apoint on the chart
             * @param series
             * @param dataPoint
             */
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "Performance: On Data Point clicked [month/profit%]: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        // Best fit line calculation and drawing
        double intercept = simpleRegression.getIntercept();
        double slope = simpleRegression.getSlope();
        double y = intercept + slope * values.size() ;
        Double[] xAxis = new Double[2];
        Double[] yAxis = new Double[2];
        xAxis[0]=0d;
        yAxis[0]=intercept;
        xAxis[1]= Double.valueOf(values.size());
        yAxis[1]=y;

        GraphView line_graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoint = new DataPoint[xAxis.length];

        for (int i = 0; i < xAxis.length; i++)
            dataPoint[i] = new DataPoint(xAxis[i],yAxis[i]);

        LineGraphSeries<DataPoint> line_series = new LineGraphSeries<DataPoint>(dataPoint);

        series.setColor(Color.rgb(0,0,255));
        line_series.setColor(Color.rgb(255, 0, 0));
        line_series.setTitle("Best fit line");
        line_graph.addSeries(line_series);

        // End of best fit line

        /**
         * A method to label the Xasis and Yasis
         */
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
        graph.getViewport().setMaxX(values.size());

        graph.getGridLabelRenderer().setVerticalAxisTitle("Performance");
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.rgb(0,0,205));
        graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(40f);
        graph.getGridLabelRenderer().setLabelVerticalWidth(25);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Months");
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.rgb(0,0,205));
        graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(40f);



        //graph.getGridLabelRenderer().setHorizontalLabelsAngle(22);
        graph.getGridLabelRenderer().setPadding(50);

        graph.addSeries(series); // Write the series to the graph

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


    /**
     * A method for the Action menu selected item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us_id:
                Intent intent = new Intent(this,AboutUs.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A method for the button "visit the company web page" to open the webview activity
     * @param view
     */
    public void viewWebPage(View view) {

        Intent webIntent = new Intent(this,WebViewActivity.class);
        webIntent.putExtra("url",selectedCompany.url);
        startActivity(webIntent);
    }
}

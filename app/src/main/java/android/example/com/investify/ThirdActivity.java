package android.example.com.investify;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class ThirdActivity extends AppCompatActivity {

    public Company selectedCompany;
    List<Double> values = new ArrayList<>();
    ScrollView scrollViewPage;
    RadioGroup radioGroup;
    RadioButton oneYear;
    RadioButton threeYear;
    RadioButton fiveYear;
    GraphView graph;
    TextView tvCompDesc;
    TextView tvAboutComp;
    ArrayList<Double> profitCalculationSource = new ArrayList<>(60);
    private static DecimalFormat decimalFormat = new DecimalFormat(".##");
    Spinner spinner;
    double amount;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoom_entry, R.anim.zoom_exit);

        setContentView(R.layout.activity_third);

        Intent i = getIntent();
        // Get the company object from second activity,
        // it is sent as an object it should be getSerializableExtra
        selectedCompany = (Company) i.getSerializableExtra("company");
        this.amount = i.getDoubleExtra("Principal", 0);
        TextView tvComName = (TextView) findViewById(R.id.tvComName);
        tvComName.setText(selectedCompany.name);
        ImageView imgCompLogo = (ImageView) findViewById(R.id.imgCompLogo); // Company Logo ImageView
        Glide.with(this).asBitmap().load(selectedCompany.logoLInk).apply(fitCenterTransform()).into(imgCompLogo); // Load the image

        tvAboutComp = (TextView) findViewById(R.id.tvAboutComp); // TextView for th upper part for the company description
        tvAboutComp.setText("About " + selectedCompany.name);

        tvCompDesc = (TextView) findViewById(R.id.tvCompDesc); // Company Description TextView
        tvCompDesc.setMovementMethod(new ScrollingMovementMethod());
        tvCompDesc.setText(selectedCompany.description);

        graph = (GraphView) findViewById(R.id.graph);  // The Chart graph
        radioGroup = (RadioGroup) findViewById(R.id.rgYearChoice);  // Radio buttons for the year options
        oneYear = (RadioButton) findViewById(R.id.rbOneYear);
        threeYear = (RadioButton) findViewById(R.id.rbThreeYear);
        fiveYear = (RadioButton) findViewById(R.id.rbFiveYear);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        oneYear.setText(String.valueOf(currentYear - 1));
        threeYear.setText(String.valueOf(currentYear - 3) + " - " + String.valueOf((currentYear - 1)));
        fiveYear.setText(String.valueOf(currentYear - 5) + " - " + String.valueOf((currentYear - 1)));
        //Set the webview button text
        Button mButton = (Button) findViewById(R.id.btnWebView);
        mButton.setText("Visit " + selectedCompany.name);

        //Calls thechartDisplay() based on user choice to draw chart points and line
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbOneYear)
                    chartDisplay(1);
                else if (checkedId == R.id.rbThreeYear)
                    chartDisplay(3);
                else
                    chartDisplay(5);

            }
        });

        mostRecent60Months();

        spinner = (Spinner) findViewById(R.id.spinNumber);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_content, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        SpinnerOnItemSelectedListener listener = new SpinnerOnItemSelectedListener();
        spinner.setOnItemSelectedListener(listener);

        //
        scrollViewPage = (ScrollView) findViewById(R.id.scrollViewPage);
        scrollViewPage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tvCompDesc.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });

        tvCompDesc.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tvCompDesc.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
        //
    }

    /**
     * A method to draw the chart series and the lines using the SimpleRegression Class
     *
     * @param year Integer value to select drawing the chart based on the year choice
     */
    private void chartDisplay(int year) {

        graph.removeAllSeries(); // clear the graph each time calling the method
        values.clear(); // Clear the datapoint values to re fill it again

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        HashMap<Integer, ArrayList<Double>> perValues = selectedCompany.performance();

        switch (year) {
            case 1:
                for (int j = year; j > 0; j--)
                    for (int i = 0; i < 12; i++)
                        values.add(perValues.get(currentYear - j).get(i)); //Filling the datapoint values ArrayList
                break;
            case 3:
                for (int j = year; j > 0; j--)
                    for (int i = 0; i < 12; i++)
                        values.add(perValues.get(currentYear - j).get(i)); //Filling the datapoint values ArrayList
                break;
            case 5:
                for (int j = year; j > 0; j--)
                    for (int i = 0; i < 12; i++)
                        values.add(perValues.get(currentYear - j).get(i)); //Filling the datapoint values ArrayList
                break;
        }


        SimpleRegression simpleRegression = new SimpleRegression();
        DataPoint[] dataPoints = new DataPoint[values.size()]; // Creating the DataPoint based the year option
        for (int i = 0; i < values.size(); i++) {
            dataPoints[i] = new DataPoint(i + 1, values.get(i)); // Filling the Data Point [X][Y] from the values ArrayList
            simpleRegression.addData(i + 1, values.get(i)); // Feeding the simpleRegression with the data to get the slope and the intercept
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
                Toast.makeText(getApplicationContext(), "Performance: On Data Point clicked [month/profit%]: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        // Best fit line calculation and drawing
        double intercept = simpleRegression.getIntercept();
        double slope = simpleRegression.getSlope();
        double y = intercept + slope * values.size();
        Double[] xAxis = new Double[2];
        Double[] yAxis = new Double[2];
        xAxis[0] = 0d;
        yAxis[0] = intercept;
        xAxis[1] = Double.valueOf(values.size());
        yAxis[1] = y;

        GraphView line_graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoint = new DataPoint[xAxis.length];

        for (int i = 0; i < xAxis.length; i++)
            dataPoint[i] = new DataPoint(xAxis[i], yAxis[i]);

        LineGraphSeries<DataPoint> line_series = new LineGraphSeries<DataPoint>(dataPoint);

        series.setColor(Color.rgb(0, 0, 255));
        line_series.setColor(Color.rgb(255, 0, 0));
        line_series.setTitle("Best fit line");
        line_graph.addSeries(line_series);
        // End of best fit line

        //A method to label the Xasis and Yasis
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
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.rgb(0, 0, 205));
        graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(40f);
        graph.getGridLabelRenderer().setLabelVerticalWidth(25);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Months");
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.rgb(0, 0, 205));
        graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(40f);


        //graph.getGridLabelRenderer().setHorizontalLabelsAngle(22);
        graph.getGridLabelRenderer().setPadding(50);

        graph.addSeries(series); // Write the series to the graph

    }

    //To activate the menu on this activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.second_menu, menu);
        return true;
    }


    /**
     * A method for the Action menu selected item
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us_id:
                Intent intent = new Intent(this, AboutUs.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Launches WebView activity
     * to display the home page of the already selected company .
     *
     * @param view
     */
    public void viewWebPage(View view) {
        Intent webIntent = new Intent(this, WebViewActivity.class);
        webIntent.putExtra("url", selectedCompany.url);
        startActivity(webIntent);
    }

    /**
     * Collates the 60 most recent data points.
     * Order of addition does affect our result.
     *
     * @return ArrayList of data points.
     */
    public ArrayList<Double> mostRecent60Months() {
        profitCalculationSource.clear();
        int balanceMonthFromCurrentYear;
        int numberOfMonthsFromCurrentYear = Calendar.getInstance().get(Calendar.MONTH);//Nov, 10
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        balanceMonthFromCurrentYear = (12 - numberOfMonthsFromCurrentYear);//2months

        //Data from the 5th year
        for (int i = balanceMonthFromCurrentYear; i > 0; i--) {
            int j = 12 - i;
            profitCalculationSource.add(selectedCompany.performance().get(currentYear - 5).get(j));
        }

        //Data from the past 4 years
        for (int i = 1; i < 5; i++) {
            int j = 5 - i;
            ArrayList<Double> annualPerformanceValue = selectedCompany.performance().get(currentYear - j);
            for (Double monthlyValue : annualPerformanceValue) {
                profitCalculationSource.add(monthlyValue);
            }
        }

        //Data from current year
        for (int i = 0; i < numberOfMonthsFromCurrentYear; i++) {
            profitCalculationSource.add(selectedCompany.performance().get(currentYear).get(i));
        }
        return profitCalculationSource;
    }

    /**
     * Estimates profit based on past 12 months' performance
     *
     * @return Estimated profit
     */
    public double profitEstimateBasedOnPast12Months() {
        SimpleRegression recentYearData = new SimpleRegression();
        double slope;
        double intercept;

        for (int i = 0; i < 12; i++) {
            int j = 59 - i;
            double[][] value = {{i, profitCalculationSource.get(j)}};
            recentYearData.addData(value);
        }
        intercept = recentYearData.getIntercept();
        slope = recentYearData.getSlope();
        return (amount / 100) * ((12 * slope) + intercept);
    }

    /**
     * Collates data depending on years chosen
     *
     * @param numberOfYears choices in Spinner
     * @return Profit Calculated
     */
    public double yearEstimateBasedOnVaryingMonths(int numberOfYears) {
        SimpleRegression recentData = new SimpleRegression();
        double slope;
        double intercept;

        for (int i = 0; i < 12 * numberOfYears; i++) {
            int j = 59 - i;
            double[][] value = {{i, profitCalculationSource.get(j)}};
            recentData.addData(value);
        }
        intercept = recentData.getIntercept();
        slope = recentData.getSlope();
        return (amount / 100) * ((12 * slope) + intercept);
    }

    /**
     * Waits for user to choose selection on Spinner and gives the relevant result
     */
    public class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

            TextView tv = (TextView) findViewById(R.id.tvEstimatedProfit);
            int choice = Integer.valueOf(parent.getSelectedItem().toString());
            switch (pos) {
                case 0:
                    //tv.setText(String.valueOf(decimalFormat.format(profitEstimateBasedOnPast12Months())));
                    tv.setText(String.valueOf(String.format( "%.2f", profitEstimateBasedOnPast12Months())));
                    break;

                case 1:
                    //tv.setText(String.valueOf(decimalFormat.format(yearEstimateBasedOnVaryingMonths(choice))));
                    tv.setText(String.valueOf(String.format( "%.2f", yearEstimateBasedOnVaryingMonths(choice))));
                    break;

                case 2:
                    //tv.setText(String.valueOf(decimalFormat.format(yearEstimateBasedOnVaryingMonths(choice))));
                    tv.setText(String.valueOf(String.format( "%.2f", yearEstimateBasedOnVaryingMonths(choice))));
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}

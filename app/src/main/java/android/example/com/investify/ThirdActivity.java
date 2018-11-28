package android.example.com.investify;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class ThirdActivity extends AppCompatActivity  {

    public Company selectedCompany;
    List<Double> values=new ArrayList<>();
    RadioGroup radioGroup;
    RadioButton oneYear;
    RadioButton threeYear;
    RadioButton fiveYear;
    GraphView graph;
    TextView tvCompDesc;
    ArrayList<Double> profitCalculationSource = new ArrayList<>(60);
    private static DecimalFormat decimalFormat = new DecimalFormat(".##");
    Spinner spinner;
    int amount;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent i = getIntent();
        selectedCompany = (Company) i.getSerializableExtra("company");
        this.amount = i.getIntExtra("principal",0);
        //Toast.makeText(this,selectedCompany.logoLInk,Toast.LENGTH_LONG).show();
//        int x = selectedCompany.perfValues.get("2017").size();
//        Toast.makeText(this,selectedCompany.perfValues.get("2017").size(),Toast.LENGTH_LONG).show();

        TextView tvComName = (TextView)findViewById(R.id.tvComName);
        tvComName.setText(selectedCompany.name);

        ImageView imgCompLogo = (ImageView)findViewById(R.id.imgCompLogo);
        Glide.with(this).asBitmap().load(selectedCompany.logoLInk).apply(fitCenterTransform()).into(imgCompLogo);

        tvCompDesc=(TextView) findViewById(R.id.tvCompDesc);
        tvCompDesc.setMovementMethod(new ScrollingMovementMethod());
        tvCompDesc.setText(selectedCompany.description);
        /*tvCompDesc.setText("Lynx Asset Management was founded in 1999 and is today one of the world's leading firms in model-based asset management.\n" +
                "\n" +
                "Our investment process is entirely systematic and based on proprietary developed models that identify trends and other patterns in financial markets. Our objective is to deliver high risk-adjusted returns with low correlation to traditional asset classes.");
*/

        graph = (GraphView) findViewById(R.id.graph);
        radioGroup = (RadioGroup) findViewById(R.id.rgYearChoice);
//        oneYear = (RadioButton) findViewById(R.id.rbOneYear);
//        threeYear = (RadioButton) findViewById(R.id.rbThreeYear);
//        fiveYear = (RadioButton) findViewById(R.id.rbFiveYear);

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

        mostRecent60Months();

        spinner = (Spinner) findViewById(R.id.spinNumber);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_content, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        SpinnerOnItemSelectedListener listener = new SpinnerOnItemSelectedListener();
        spinner.setOnItemSelectedListener(listener);
    }

    private void chartDisplay(int year) {

        graph.removeAllSeries();
        values.clear();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth= Calendar.getInstance().get(Calendar.MONTH);



        switch (year)
        {
            case 1:

                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-1)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-1)).get(i)).replace(",",".")));
                break;
            case 3:

                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-3)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-3)).get(i)).replace(",",".")));
                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-2)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-2)).get(i)).replace(",",".")));
                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-1)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-1)).get(i)).replace(",",".")));
                break;
            case 5:

                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-5)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-5)).get(i)).replace(",",".")));
                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-4)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-4)).get(i)).replace(",",".")));
                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-3)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-3)).get(i)).replace(",",".")));
                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-2)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-2)).get(i)).replace(",",".")));
                for(int i=0;i<selectedCompany.perfValues.get(String.valueOf(currentYear-1)).size();i++)
                    values.add(Double.parseDouble(((String)selectedCompany.perfValues.get(String.valueOf(currentYear-1)).get(i)).replace(",",".")));
                break;
        }



        SimpleRegression simpleRegression = new SimpleRegression();
        DataPoint[] dataPoints = new DataPoint[values.size()];
        for (int i = 0; i < values.size(); i++) {
            dataPoints[i] = new DataPoint(i + 1, values.get(i));
            simpleRegression.addData(i+1,values.get(i));
        }

        // This will display the line in the chart
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        series.setDrawBackground(true); // Shaded the area under the line
        series.setDrawDataPoints(false); // To draw the points for the line
        series.setAnimated(true);
        series.setColor(Color.rgb(186, 1, 38));

        // Best fit line

        double intercept = simpleRegression.getIntercept();
        double slope = simpleRegression.getSlope();
        double y = intercept + slope * values.size() ;
        Double[] xAxis = new Double[2];
        Double[] yAxis = new Double[2];
        xAxis[0]=0d;    yAxis[0]=intercept;
        xAxis[1]= Double.valueOf(values.size());   yAxis[1]=y;
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
        graph.addSeries(series);

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

    public void viewWebPage(View view) {

        Intent webIntent = new Intent(this,WebViewActivity.class);
        webIntent.putExtra("url",selectedCompany.url);
        startActivity(webIntent);
    }

    /**
     * Collates the 60 most recent data points.
     * Order of addition does affect our result.
     * @return ArrayList of data points.
     */
    public ArrayList<Double> mostRecent60Months(){

        profitCalculationSource.clear();
        int balanceMonthFromCurrentYear;
        int numberOfMonthsFromCurrentYear=0;
        int currentMonth=Calendar.getInstance().get(Calendar.MONTH);
        String currentYearAsKey=Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        int currentYear=Calendar.getInstance().get(Calendar.YEAR);

        for(int i=0; i<currentMonth; i++){
            numberOfMonthsFromCurrentYear++;
        }

        balanceMonthFromCurrentYear=(11-numberOfMonthsFromCurrentYear);

        //Data from current year
        for(int i=0; i<=numberOfMonthsFromCurrentYear; i++){
            ArrayList<Object> perfValuesOfEntireYear = selectedCompany.getPerfValues().get(currentYearAsKey);
            Object monthValue = perfValuesOfEntireYear.get(i);
            double valueOfMonth = Double.parseDouble(String.valueOf(monthValue).replace(",","."));
            profitCalculationSource.add(valueOfMonth);
        }

        //Data from the past 4 years
        for( int i=1; i<5; i++){
            ArrayList<Object> perfValuesOfEntireYear=selectedCompany.getPerfValues().get(Integer.toString(currentYear-i));
            for(Object object : perfValuesOfEntireYear){
                double monthValue = Double.parseDouble(String.valueOf(object).replace(",","."));
                profitCalculationSource.add(monthValue);
            }
        }

        //Data from the 5th year
        for (int i = 0; i<balanceMonthFromCurrentYear; i++) {
            int j=11-i;
            ArrayList<Object> perfValuesOfEntireYear = selectedCompany.getPerfValues().get(Integer.toString(currentYear - 5));
            Object monthValue = perfValuesOfEntireYear.get(j);
            double valueOfMonth = Double.parseDouble(String.valueOf(monthValue).replace(",", "."));
            profitCalculationSource.add(valueOfMonth);
        }
        return profitCalculationSource;
    }

    /**
     * Estimates profit based on past 12 months' performance
     * @return Estimated profit
     */
    public double profitEstimateBasedOnPast12Months(){

        SimpleRegression recentYearData = new SimpleRegression();
        double slope;
        double intercept;

        for(int i=0; i<12; i++){
            double [][]value={{i,profitCalculationSource.get(i)}};
            recentYearData.addData(value);
        }
        intercept=recentYearData.getIntercept();
        slope=recentYearData.getSlope();

        return (amount/100)*((12*slope)+intercept);
    }

    public double yearEstimateBasedOnVaryingMonths(int numberOfYears) {

        SimpleRegression recentData = new SimpleRegression();
        double slope;
        double intercept;

        for (int i = 0; i < 12 * numberOfYears; i++) {
            double[][] value = {{i, profitCalculationSource.get(i)}};
            recentData.addData(value);
        }
        intercept = recentData.getIntercept();
        slope = recentData.getSlope();

        return (amount/ 100) * ((12 * slope) + intercept);
    }

    //The value varies based on the different slope and intercept value. The value is NOT profit for 3 years!

    /*public void spinnerProfitResult(int choice){
        TextView tv = (TextView) findViewById(R.id.tvEstimatedProfit);

        switch (choice)
        {
            case 1:
                tv.setText(String.valueOf(decimalFormat.format(profitEstimateBasedOnPast12Months())));
                break;

            case 3:
                tv.setText(String.valueOf(decimalFormat.format(yearEstimateBasedOnVaryingMonths(choice))));
                break;

            case 5:
                tv.setText(String.valueOf(decimalFormat.format(yearEstimateBasedOnVaryingMonths(choice))));
                break;
        }
    }*/

    public class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            TextView tv = (TextView) findViewById(R.id.tvEstimatedProfit);
            int choice = Integer.valueOf(parent.getSelectedItem().toString());
            switch (pos)
            {
                case 0:
                    tv.setText(String.valueOf(decimalFormat.format(profitEstimateBasedOnPast12Months())));
                    break;

                case 1:
                    tv.setText(String.valueOf(decimalFormat.format(yearEstimateBasedOnVaryingMonths(choice))));
                    break;

                case 2:
                    tv.setText(String.valueOf(decimalFormat.format(yearEstimateBasedOnVaryingMonths(choice))));
                    break;
            }

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
}

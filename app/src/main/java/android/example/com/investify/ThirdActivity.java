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
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class ThirdActivity extends AppCompatActivity  {

    public Company selectedCompany;
    double [][]value = {{1,-1.96},{2,4.61},{3,-3.71},{4,-3.32},{5,-2.37},{6,-6.94},{7,2.37},{8,5.07},{9,-4.45},{10,5.90},{11,0.36},{12,-0.02}};
    ArrayList<Integer> source = new ArrayList<Integer>();
    List<Double> values=new ArrayList<>();
    RadioGroup radioGroup;
    RadioButton oneYear;
    RadioButton threeYear;
    RadioButton fiveYear;
    GraphView graph;
    TextView tvCompDesc;







    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoom_entry,R.anim.zoom_exit);

        setContentView(R.layout.activity_third);
        
        fillSpinnerData();
        final Spinner spin = (Spinner) findViewById(R.id.spinNumber);



        Intent i = getIntent();
        selectedCompany = (Company) i.getSerializableExtra("company");

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



        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, source);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);



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

    public void viewWebPage(View view) {

        Intent webIntent = new Intent(this,WebViewActivity.class);
        webIntent.putExtra("url",selectedCompany.url);
        startActivity(webIntent);
    }
}

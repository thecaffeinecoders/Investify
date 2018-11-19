package android.example.com.investify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        double [][]value = {{1,-1.96},{2,4.61},{3,-3.71},{4,-3.32},{5,-2.37f},{6,-6.94},{7,2.37},{8,5.07},{9,-4.45},{10,5.90},{11,0.36},{12,-0.02}};

        SimpleRegression simpleRegression = new SimpleRegression();
        simpleRegression.addData(value);
        double intercept = simpleRegression.getIntercept();
        double slope = simpleRegression.getSlope();
        double sig = simpleRegression.getSignificance();
        //


    }



    //To activate the menu on this activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }


    // A temporary method to move to next activity
    public void moveNext(View view) {
        startActivity(new Intent(this,ThirdActivity.class));
    }


    // A temporary method for the selected item from the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_us_id:
                Toast.makeText(this,"About Us ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact_us_id:
                Toast.makeText(this,"Contact Us ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_id:
                Toast.makeText(this,"Search ",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

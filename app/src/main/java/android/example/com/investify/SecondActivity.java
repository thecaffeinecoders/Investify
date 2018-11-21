package android.example.com.investify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int revenue = (int) getIntent().getDoubleExtra("Revenue",0);
        int amount = (int) getIntent().getDoubleExtra("Amount",0);
        TextView tvRevenue = (TextView) findViewById(R.id.et_maxProfit);
        tvRevenue.setText("If you invest " + amount + " your estimate profit will be "+String.valueOf(revenue-amount));
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

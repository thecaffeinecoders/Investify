package android.example.com.investify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class MainActivity extends AppCompatActivity {

    double intercept;
    double slope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_entry,R.anim.left_exit);

        setContentView(R.layout.activity_main);

        double [][]value = {{1,-1.96},{2,4.61},{3,-3.71},{4,-3.32},{5,-2.37f},{6,-6.94},{7,2.37},{8,5.07},{9,-4.45},{10,5.90},{11,0.36},{12,-0.02}};

        SimpleRegression simpleRegression = new SimpleRegression();
        simpleRegression.addData(value);
        this.intercept = simpleRegression.getIntercept();
        this.slope = simpleRegression.getSlope();
    }

    // A temporary method to move to next activity
    public void moveNext(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        double amount = readPrincipal(findViewById(R.id.teAmountEnteredToInvest));
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra("Amount", amount);
        intent.putExtra("Revenue", revenue());
        startActivity(intent);

        //To activate the menu on this activity

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.info_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this,Information.class));
        return super.onOptionsItemSelected(item);
    }


    /**
     * Takes value user inputs for computational use
     * @param view Takes in String from specified View
     * @return Integer value
     */
    private double readPrincipal(View view) {
        try {
            EditText input = (EditText) findViewById(R.id.teAmountEnteredToInvest);
            String readInput = input.getText().toString();
            double principal = Integer.parseInt(readInput);
            return principal;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double revenue(){
        double revenue;
        double principal = readPrincipal(findViewById(R.id.teAmountEnteredToInvest));
        revenue = (principal/100) * ((12*slope)+intercept+100);
        return revenue;
    }
}

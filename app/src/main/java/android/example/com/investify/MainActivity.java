package android.example.com.investify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import org.apache.commons.math3.stat.regression.SimpleRegression;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_entry,R.anim.left_exit);

        setContentView(R.layout.activity_main);
    }

    //
    public void moveNext(View view) {

        double principal = readPrincipal(findViewById(R.id.teAmountEnteredToInvest));
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra("Principal", principal);
        startActivity(intent);
    }

    /**
     * Takes value user inputs for computational use
     * @param view Takes in String from specified View
     * @return Integer value
     */
    public double readPrincipal(View view) {
        try {
            EditText input = (EditText) findViewById(R.id.teAmountEnteredToInvest);
            String readInput = input.getText().toString();
            double principal = Integer.parseInt(readInput);
            return principal;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

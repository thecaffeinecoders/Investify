package android.example.com.investify;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";

    private ArrayList<Company> companiesList= new ArrayList<>();
    private RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private SearchView searchView;
    private static DecimalFormat decimalFormat = new DecimalFormat(".##");

    FirebaseDatabase database = FirebaseDatabase.getInstance();// database Instance
    DatabaseReference myRef = database.getReference("CompanyData");// database reference to particular child in the database
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_entry,R.anim.right_exit);
        setContentView(R.layout.activity_second);

        double revenue = getIntent().getDoubleExtra("Revenue",0);

        final int amount = (int) getIntent().getDoubleExtra("Amount",0);
        TextView tvInvested = (TextView) findViewById(R.id.tv_valueOfInvestment);
        TextView tvRevenue = (TextView) findViewById(R.id.et_maxProfit);
        tvInvested.setText(String.valueOf(amount));
        tvRevenue.setText(String.valueOf(decimalFormat.format(revenue-amount)));
      
        /**
         * the eventlistener is related to the database reference
         * it triggers whenever some values change
         */
        myRef.addValueEventListener(new ValueEventListener() {
            @Override

            /**This method is called once with the initial value and again
             * whenever data at this location is updated.
             * @param DataSnapshot is the current snapshot of the Firebase Database
             */
            public void onDataChange(DataSnapshot dataSnapshot) {

                companiesList.clear();// to clear the Arraylist so no duplications occur
                /* this loop is to read through the database and cast each child in the
                   Database reference into a company Object
                   then add this new object to the companiesList
                */
                for (DataSnapshot companySnapshot : dataSnapshot.getChildren()) {
                    Company company = companySnapshot.getValue(Company.class);
                    company.setName(companySnapshot.getKey());
                    companiesList.add(company);
                    Log.d(TAG, "Value is: " + company.toString());// Logging purposes
                }
                /* 1. Find the recyclerView
                   2. create a new instance of the recyclerView adapter with passing the current activity
                      and companiesList and the amount from the first activity
                   3.set the adapter to recyclerView
                 */
                recyclerView = findViewById(R.id.reviewCompanyList);
                adapter = new RecyclerViewAdapter(SecondActivity.this, companiesList,amount);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(SecondActivity.this));
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    protected void onStart() {
        super.onStart();

    }
    
    //To activate the menu on this activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        /**
         * Associate searchable configuration with the SearchView         *
         */
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        /**
         * listening to search query text change
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }


    // A temporary method to move to next activity
    public void moveNext(View view) {
        startActivity(new Intent(this,ThirdActivity.class));
    }

    // A method for the menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_us_id:
                Intent intent = new Intent(this,AboutUs.class);

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * onBackPressed() method: customized for searchView
     */
    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    public Company getCompany(){
        return null;

    }
}

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
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private ArrayList<Company> companiesList= new ArrayList<>();
    private RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private SearchView searchView;
   // private ArrayList<String> companyNameList= new ArrayList<>();
    //private ArrayList <String> companyLogoList= new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CompanyData");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //final RecyclerView recyclerView = findViewById(R.id.reviewCompanyList);
        //final RecyclerViewAdapter adapter = new RecyclerViewAdapter(SecondActivity.this, companiesList);


        int revenue = (int) getIntent().getDoubleExtra("Revenue",0);
        int amount = (int) getIntent().getDoubleExtra("Amount",0);
        TextView tvRevenue = (TextView) findViewById(R.id.et_maxProfit);

        tvRevenue.setText(String.valueOf(revenue));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                companiesList.clear();
                for (DataSnapshot companySnapshot : dataSnapshot.getChildren()) {
                    Company company = companySnapshot.getValue(Company.class);
                    company.setName(companySnapshot.getKey());
                    companiesList.add(company);
                    Log.d(TAG, "Value is: " + company.toString());
                }
                recyclerView = findViewById(R.id.reviewCompanyList);
                adapter = new RecyclerViewAdapter(SecondActivity.this, companiesList);
                recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(SecondActivity.this));

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        int x = 0;
        //adapter.notifyDataSetChanged();

        tvRevenue.setText("If you invest " + amount + " your estimate profit will be "+String.valueOf(revenue-amount));

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

        // listening to search query text change
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
            case R.id.action_search:
                Toast.makeText(this,"Search ",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}

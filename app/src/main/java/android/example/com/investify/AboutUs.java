package android.example.com.investify;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_entry, R.anim.left_exit);

        setContentView(R.layout.activity_about_us);
    }

    public void onClick(View v) {
        String profileLink = null;

        switch (v.getId()) {
            case R.id.tvAdem:
                profileLink = "https://www.linkedin.com/in/adem-s-628064172/";
                break;
            case R.id.tvDima:
                profileLink = "https://www.linkedin.com/in/dimaalissa/";
                break;
            case R.id.tvHossin:
                profileLink = "https://www.linkedin.com/in/hossin-algerf-231b9816b/";
                break;
            case R.id.tvRadhika:
                profileLink = "https://www.linkedin.com/in/radhika-jayaraman-64b27a146/";
                break;
            case R.id.tvTamer:
                profileLink = "https://www.linkedin.com/in/tamer-alnoami-ba398713b";
                break;
            case R.id.tvZhi:
                profileLink = "https://www.linkedin.com/in/chen-zhi-91b51a97/";
                break;

        }

        Intent viewLinkedInProfile = new Intent(Intent.ACTION_VIEW, Uri.parse(profileLink));
        startActivity(viewLinkedInProfile);
    }

}

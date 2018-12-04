package android.example.com.investify;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.VideoView;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class MainActivity extends AppCompatActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_entry,R.anim.left_exit);

        setContentView(R.layout.activity_main);

        /**
         * Background Video
         */
        mVideoView = (VideoView) findViewById(R.id.bgVideoView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.investfy);

        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }

    // For VideoView
    @Override
    protected void onResume() {
        super.onResume();
        // to restart the video after coming from other activity like Sing up
        mVideoView.start();
    }

    // A temporary method to move to next activity
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
            double principal = Double.parseDouble(readInput);
            return principal;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

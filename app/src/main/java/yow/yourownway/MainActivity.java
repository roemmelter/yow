package yow.yourownway;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startBackgroundUpdater();



    }


    public void onBook(){

    }

    public void startBackgroundUpdater(){
        Intent backgroundUpdater = new Intent(this, BackgroundUpdater.class);
        this.startService(backgroundUpdater);
    }

}

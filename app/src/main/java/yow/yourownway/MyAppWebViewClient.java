package yow.yourownway;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Erik Römmelt , last edited on 18.05.2017.
 */

public class MyAppWebViewClient extends WebViewClient {

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().endsWith("https://xd.adobe.com/view/730f4c2a-6ea5-4709-9784-b672b15dc76d/?fullscreen")) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }
}

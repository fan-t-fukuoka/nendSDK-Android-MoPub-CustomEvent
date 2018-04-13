package net.nend.customevent.mopub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

public class InterstitialActivity extends AppCompatActivity {

    private static final String MOPUB_AD_UNIT_ID = "3eff80f4d3854da188efaa7991a812aa";
    private MoPubInterstitial interstitial;

    private MoPubInterstitial.InterstitialAdListener adListener = new MoPubInterstitial.InterstitialAdListener() {
        @Override
        public void onInterstitialLoaded(MoPubInterstitial interstitial) {
            showToast("Interstitial Load Success: " + interstitial, Toast.LENGTH_SHORT);
        }

        @Override
        public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
            showToast("Interstitial Load Failure: " + interstitial + "\n errorCode: " + errorCode, Toast.LENGTH_LONG);
        }

        @Override
        public void onInterstitialShown(MoPubInterstitial interstitial) {
            showToast("Interstitial shown: " + interstitial, Toast.LENGTH_SHORT);
        }

        @Override
        public void onInterstitialClicked(MoPubInterstitial interstitial) {
            showToast("Interstitial clicked: " + interstitial, Toast.LENGTH_SHORT);
        }

        @Override
        public void onInterstitialDismissed(MoPubInterstitial interstitial) {
            showToast("Interstitial dismissed: " + interstitial, Toast.LENGTH_SHORT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        findViewById(R.id.bt_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interstitial = new MoPubInterstitial(InterstitialActivity.this, MOPUB_AD_UNIT_ID);
                interstitial.setInterstitialAdListener(adListener);
                interstitial.load();
            }
        });

        findViewById(R.id.bt_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitial != null && interstitial.isReady()) {
                    interstitial.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (interstitial != null) {
            interstitial.destroy();
        }
        super.onDestroy();
    }

    private void showToast(String message, int duration) {
        Toast.makeText(InterstitialActivity.this, message, duration).show();
    }
}

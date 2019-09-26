package net.nend.customevent.mopub;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

public class BannerActivity extends AppCompatActivity {

    private static final String MOPUB_AD_UNIT_ID = "YOUR_BANNER_UNIT_ID";
    private static final String TAG = "BannerActivity";

    private MoPubView mopubView;

    private MoPubView.BannerAdListener adListener = new MoPubView.BannerAdListener() {
        @Override
        public void onBannerLoaded(MoPubView banner) {
            Log.d(TAG, "onBannerLoaded:" + banner);
        }

        @Override
        public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
            Log.d(TAG, "onBannerFailed:" + banner + "/ errorCode:" + errorCode);
        }

        @Override
        public void onBannerClicked(MoPubView banner) {
            Log.d(TAG, "onBannerClicked:" + banner);
        }

        @Override
        public void onBannerExpanded(MoPubView banner) {
            Log.d(TAG, "onBannerExpanded:" + banner);
        }

        @Override
        public void onBannerCollapsed(MoPubView banner) {
            Log.d(TAG, "onBannerCollapsed:" + banner);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mopubView = findViewById(R.id.adview);
        mopubView.setAdUnitId(MOPUB_AD_UNIT_ID);
        mopubView.setBannerAdListener(adListener);
        mopubView.loadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mopubView.setBannerAdListener(null);
        mopubView.destroy();
        ViewGroup parent = (ViewGroup) mopubView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
    }
}

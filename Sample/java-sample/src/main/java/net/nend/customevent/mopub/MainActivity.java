package net.nend.customevent.mopub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;

import net.nend.android.mopub.customevent.NendRewardedVideoCustomEvent;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String MOPUB_AD_UNIT_ID = "your ad unit id";

    private MoPubRewardedVideoListener mMoPubRewardedVideoListener = new MoPubRewardedVideoListener() {
        @Override
        public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
            showToast("Load Success adUnitId: " + adUnitId, Toast.LENGTH_SHORT);
        }

        @Override
        public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
            showToast("Load Failure adUnitId: " + adUnitId + "\n errorCode: " + errorCode, Toast.LENGTH_LONG);
        }

        @Override
        public void onRewardedVideoStarted(@NonNull String adUnitId) {
            showToast("Video Started adUnitId: " + adUnitId, Toast.LENGTH_SHORT);
        }

        @Override
        public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
            showToast("Video PlaybackError adUnitId: " + adUnitId + "\n errorCode: " + errorCode, Toast.LENGTH_LONG);
        }

        @Override
        public void onRewardedVideoClicked(@NonNull String adUnitId) {
            showToast("Ad Clicked adUnitId: " + adUnitId, Toast.LENGTH_SHORT);
        }

        @Override
        public void onRewardedVideoClosed(@NonNull String adUnitId) {
            showToast("Ad Closed adUnitId: " + adUnitId, Toast.LENGTH_SHORT);
        }

        @Override
        public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
            showToast("Video Completed adUnitId: " + adUnitIds.toString() + "\n Reward_label: " + reward.getLabel() + "\n Reward_amount: " + reward.getAmount(), Toast.LENGTH_LONG);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoPubRewardedVideos.initializeRewardedVideo(MainActivity.this);
        MoPubRewardedVideos.setRewardedVideoListener(mMoPubRewardedVideoListener);

        findViewById(R.id.bt_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NendRewardedVideoCustomEvent.NendInstanceMediationSettings settings = new NendRewardedVideoCustomEvent.NendInstanceMediationSettings("your user id");
                MoPubRewardedVideos.loadRewardedVideo(MOPUB_AD_UNIT_ID, settings);
            }
        });

        findViewById(R.id.bt_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoPubRewardedVideos.hasRewardedVideo(MOPUB_AD_UNIT_ID)) {
                    MoPubRewardedVideos.showRewardedVideo(MOPUB_AD_UNIT_ID);
                }
            }
        });
    }

    private void showToast(String message, int duration) {
        Toast.makeText(MainActivity.this, message, duration).show();
    }
}

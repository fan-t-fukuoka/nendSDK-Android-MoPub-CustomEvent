package net.nend.customevent.mopub

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mopub.common.MoPub
import com.mopub.common.MoPubReward
import com.mopub.common.SdkConfiguration
import com.mopub.common.SdkInitializationListener
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubRewardedVideoListener
import com.mopub.mobileads.MoPubRewardedVideos
import net.nend.android.mopub.customevent.NendMediationSettings

class RewardedVideoActivity : AppCompatActivity() {

    private val mopubRewardedListener = object : MoPubRewardedVideoListener {
        override fun onRewardedVideoLoadSuccess(adUnitId: String) {
            toast("Load Success adUnitId: $adUnitId", Toast.LENGTH_SHORT)
        }

        override fun onRewardedVideoLoadFailure(adUnitId: String, errorCode: MoPubErrorCode) {
            toast("Load Failure adUnitId: $adUnitId\n errorCode: $errorCode", Toast.LENGTH_LONG)
        }

        override fun onRewardedVideoStarted(adUnitId: String) {
            toast("Video Started adUnitId: $adUnitId", Toast.LENGTH_SHORT)
        }

        override fun onRewardedVideoPlaybackError(adUnitId: String, errorCode: MoPubErrorCode) {
            toast("Video PlaybackError adUnitId: $adUnitId\n errorCode: $errorCode", Toast.LENGTH_LONG)
        }

        override fun onRewardedVideoClicked(adUnitId: String) {
            toast("Ad Clicked adUnitId: $adUnitId", Toast.LENGTH_SHORT)
        }

        override fun onRewardedVideoClosed(adUnitId: String) {
            toast("Ad Closed adUnitId: $adUnitId", Toast.LENGTH_SHORT)
        }

        override fun onRewardedVideoCompleted(adUnitIds: MutableSet<String>, reward: MoPubReward) {
            toast("Video Completed adUnitId: $adUnitIds \n Reward_label: ${reward.label} \n Reward_amount: ${reward.amount}", Toast.LENGTH_LONG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_video)

        val sdkConfiguration = SdkConfiguration.Builder(MOPUB_AD_UNIT_ID)
                .build()
        MoPub.initializeSdk(this, sdkConfiguration, initMoPubSdkListener())

        MoPubRewardedVideos.setRewardedVideoListener(mopubRewardedListener)

        findViewById<View>(R.id.bt_load).setOnClickListener {
            val settings = NendMediationSettings.Builder()
                    .setUserId("you user id")
                    .setAge(18)
                    .setBirthday(2000, 1, 1)
                    .setGender(NendMediationSettings.GENDER_MALE)
                    .addCustomFeature("customIntParam", 123)
                    .addCustomFeature("customDoubleParam", 123.45)
                    .addCustomFeature("customStringParam", "test")
                    .addCustomFeature("customBooleanParam", true)
                    .build()
            MoPubRewardedVideos.loadRewardedVideo(MOPUB_AD_UNIT_ID, settings)
        }

        findViewById<View>(R.id.bt_show).setOnClickListener {
            if (MoPubRewardedVideos.hasRewardedVideo(MOPUB_AD_UNIT_ID)) {
                MoPubRewardedVideos.showRewardedVideo(MOPUB_AD_UNIT_ID)
            }
        }
    }

    private fun Context.toast(message: CharSequence, duration: Int) = Toast.makeText(this, message, duration).show()

    private fun initMoPubSdkListener() = SdkInitializationListener {
        toast("MoPub SDK initialized.", Toast.LENGTH_SHORT)
    }

    companion object {
        const val MOPUB_AD_UNIT_ID = "YOUR_UNIT_ID"
    }
}

package net.nend.customevent.mopub

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mopub.common.MoPubReward
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubRewardedVideoListener
import com.mopub.mobileads.MoPubRewardedVideos
import kotlinx.android.synthetic.main.activity_main.*
import net.nend.android.mopub.customevent.NendRewardedVideoCustomEvent

private const val MOPUB_AD_UNIT_ID = "your ad unit id"

class MainActivity : AppCompatActivity() {

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

    private fun Context.toast(message: CharSequence, duration: Int) = Toast.makeText(this, message, duration).show()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MoPubRewardedVideos.initializeRewardedVideo(this)
        MoPubRewardedVideos.setRewardedVideoListener(mopubRewardedListener)

        bt_load.setOnClickListener{
            val setting = NendRewardedVideoCustomEvent.NendInstanceMediationSettings("your user id")
            MoPubRewardedVideos.loadRewardedVideo(MOPUB_AD_UNIT_ID, setting)
        }

        bt_show.setOnClickListener {
            if (MoPubRewardedVideos.hasRewardedVideo(MOPUB_AD_UNIT_ID)) {
                MoPubRewardedVideos.showRewardedVideo(MOPUB_AD_UNIT_ID)
            }
        }
    }
}

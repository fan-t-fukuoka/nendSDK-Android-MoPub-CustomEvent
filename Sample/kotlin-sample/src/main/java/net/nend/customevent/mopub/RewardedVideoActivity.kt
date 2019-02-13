package net.nend.customevent.mopub

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mopub.common.MoPubReward
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubRewardedVideoListener
import com.mopub.mobileads.MoPubRewardedVideos
import net.nend.android.mopub.customevent.NendMediationSettings

/*
  This sample uses location data as an option for ad supply.
*/
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

        // If you use location in your app, but would like to disable location passing.
//        MoPub.setLocationAwareness(MoPub.LocationAwareness.DISABLED)

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

    override fun onStart() {
        super.onStart()
        if (!verifyPermissions()) {
            requestPermissions()
        }
    }

    private fun Context.toast(message: CharSequence, duration: Int) = Toast.makeText(this, message, duration).show()

    private fun verifyPermissions(): Boolean {
        val state = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return state == PackageManager.PERMISSION_GRANTED
    }

    private fun showRequestPermissionDialog() = ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)

    private fun requestPermissions() {
        val shouldRequest = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (shouldRequest) {
            Snackbar.make(findViewById(R.id.base_layout),
                    "Location permission is needed for get the last Location. It's a demo that uses location data.",
                    Snackbar.LENGTH_LONG).setAction(android.R.string.ok) {
                showRequestPermissionDialog()
            }.show()
        } else {
            showRequestPermissionDialog()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> Snackbar.make(findViewById(R.id.base_layout),
                        "User interaction was cancelled.", Snackbar.LENGTH_LONG).show()
                grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                    Snackbar.make(findViewById(R.id.base_layout),
                            "Permission granted.", Snackbar.LENGTH_LONG).show()
                else -> Snackbar.make(findViewById(R.id.base_layout),
                        "Permission denied.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val MOPUB_AD_UNIT_ID = "YOUR_UNIT_ID"
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    }
}

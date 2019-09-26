package net.nend.customevent.mopub

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial
import net.nend.android.mopub.customevent.NendMediationSettings

/*
  This sample uses location data as an option for ad supply.
*/
class InterstitialVideoActivity : AppCompatActivity() {

    private var interstitial: MoPubInterstitial? = null

    private val adListener = object : MoPubInterstitial.InterstitialAdListener {
        override fun onInterstitialLoaded(interstitial: MoPubInterstitial) {
            showToast("Interstitial Load Success: $interstitial", Toast.LENGTH_SHORT)
        }

        override fun onInterstitialFailed(interstitial: MoPubInterstitial, errorCode: MoPubErrorCode) {
            showToast("Interstitial Load Failure: $interstitial\n errorCode: $errorCode", Toast.LENGTH_LONG)
        }

        override fun onInterstitialShown(interstitial: MoPubInterstitial) {
            showToast("Interstitial shown: $interstitial", Toast.LENGTH_SHORT)
        }

        override fun onInterstitialClicked(interstitial: MoPubInterstitial) {
            showToast("Interstitial clicked: $interstitial", Toast.LENGTH_SHORT)
        }

        override fun onInterstitialDismissed(interstitial: MoPubInterstitial) {
            showToast("Interstitial dismissed: $interstitial", Toast.LENGTH_SHORT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_video)

        // If you use location in your app, but would like to disable location passing.
//        MoPub.setLocationAwareness(MoPub.LocationAwareness.DISABLED)

        findViewById<View>(R.id.bt_load).setOnClickListener {
            if (interstitial == null) {
                interstitial = MoPubInterstitial(this, MOPUB_AD_UNIT_ID).apply {
                    interstitialAdListener = adListener

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
                }
            }
            interstitial!!.load()
        }

        findViewById<View>(R.id.bt_show).setOnClickListener {
            interstitial?.let {
                if (it.isReady) {
                    it.show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!verifyPermissions()) {
            requestPermissions()
        }
    }

    override fun onDestroy() {
        interstitial?.destroy()
        super.onDestroy()
    }

    private fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    private fun verifyPermissions(): Boolean {
        val state = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return state == PackageManager.PERMISSION_GRANTED
    }

    private fun showRequestPermissionDialog() = ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), InterstitialVideoActivity.REQUEST_PERMISSIONS_REQUEST_CODE)

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
        if (requestCode == InterstitialVideoActivity.REQUEST_PERMISSIONS_REQUEST_CODE) {
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

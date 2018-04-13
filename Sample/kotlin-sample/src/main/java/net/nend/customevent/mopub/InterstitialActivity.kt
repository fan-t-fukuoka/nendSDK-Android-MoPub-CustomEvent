package net.nend.customevent.mopub

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial

class InterstitialActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_interstitial)

        findViewById<View>(R.id.bt_load).setOnClickListener {
            interstitial = MoPubInterstitial(this, MOPUB_AD_UNIT_ID).apply {
                interstitialAdListener = adListener
                load()
            }
        }

        findViewById<View>(R.id.bt_show).setOnClickListener {
            interstitial?.let {
                if (it.isReady) {
                    it.show()
                }
            }
        }
    }

    override fun onDestroy() {
        interstitial?.destroy()
        super.onDestroy()
    }

    private fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    companion object {
        const val MOPUB_AD_UNIT_ID = "3eff80f4d3854da188efaa7991a812aa"
    }
}

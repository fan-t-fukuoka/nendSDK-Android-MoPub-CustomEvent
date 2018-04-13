package net.nend.customevent.mopub

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView

class SelectAdTypeActivity : ListActivity() {

    private enum class AdType(val id: Int) {
        BANNER(0),
        INTERSTITIAL(1),
        REWARDED_VIDEO(2),
        INTERSTITIAL_VIDEO(3);

        companion object {
            fun getAdType(id: Int) = AdType.values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                BANNER -> activity.startActivity(Intent(activity,
                    BannerActivity::class.java))
                INTERSTITIAL -> activity.startActivity(Intent(activity,
                        InterstitialActivity::class.java))
                REWARDED_VIDEO -> activity.startActivity(Intent(activity,
                        RewardedVideoActivity::class.java))
                INTERSTITIAL_VIDEO -> activity.startActivity(Intent(activity,
                        InterstitialVideoActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_ad_type)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        AdType.getAdType(position).startActivity(this)
    }
}

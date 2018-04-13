package net.nend.customevent.mopub;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SelectAdTypeActivity extends ListActivity {

    private static final List<Class> SAMPLE_ACTIVITIES = new ArrayList<Class>() {
        {
            add(BannerActivity.class);
            add(InterstitialActivity.class);
            add(RewardedVideoActivity.class);
            add(InterstitialVideoActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ad_type);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(this, SAMPLE_ACTIVITIES.get(position)));
    }
}

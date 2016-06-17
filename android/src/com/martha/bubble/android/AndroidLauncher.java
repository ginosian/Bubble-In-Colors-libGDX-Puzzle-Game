package com.martha.bubble.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.martha.bubble.Bubble;
import com.martha.bubble.listener.IAdController;
import com.martha.bubble.listener.IDeviceMetrics;
import com.martha.bubble.listener.IRate;

public class AndroidLauncher extends AndroidApplication {

    private volatile AdView bannerAd;
    private IAdController adController;
    private IDeviceMetrics deviceMetrics;
    private IRate iRate;
    private BroadcastReceiver broadcastReceiver;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        initBannerAd();
        initAdController();
        initDeviceMetrics();
        initIRate();

        // Turn off screen sleep.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        RelativeLayout mainLayout = new RelativeLayout(this);

        View gameView = initializeForView(new Bubble(adController, deviceMetrics, iRate), config);

        // gameView params.
        RelativeLayout.LayoutParams gameViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                     ViewGroup.LayoutParams.MATCH_PARENT);
        // adView params.
        RelativeLayout.LayoutParams adViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                   ViewGroup.LayoutParams.WRAP_CONTENT);
        adViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        // Adding game view and AD view in main layout.
        mainLayout.addView(gameView, gameViewParams);
        mainLayout.addView(bannerAd, adViewParams);

        setContentView(mainLayout);

        // Initializing and registering the broadcast receiver.
        initBroadcastReceiver();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    // region Initializing methods
    private void initIRate() {
        iRate = new IRate() {
            @Override
            public void showRateDialog() {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AndroidConstants.MARKET_URI)));
            }
        };
    }
    private void initDeviceMetrics() {
        deviceMetrics = new IDeviceMetrics() {
            @Override
            public int pxToDp(int px) {
                DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
                return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            }
            @Override
            public int dpToPx(int dp) {
                DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
                return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            }
        };
    }
    private void initBannerAd() {
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setAdUnitId(AndroidConstants.AD_UNIT_ID);
        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdSize(AdSize.SMART_BANNER);
        bannerAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bannerAd.setVisibility(View.VISIBLE);
                        bannerAd.bringToFront();
                    }
                });
            }
        });
    }
    private void initAdController() {
        adController = new IAdController() {
            @Override
            public void loadAndShowAD() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AdRequest.Builder builder = new AdRequest.Builder();
                            AdRequest ad = builder.build();
                            bannerAd.loadAd(ad);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void hide() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bannerAd.setVisibility(View.INVISIBLE);
                    }
                });
            }
            @Override
            public boolean isConnectedToInternet() {
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = false;
                if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                    isConnected = true;
                }
                return isConnected;
            }

            @Override
            public int getAdHeight() {
                return bannerAd.getHeight();
            }
        };
    }
    private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    if (extras.get(ConnectivityManager.EXTRA_NO_CONNECTIVITY) == null) {
                        adController.loadAndShowAD();
                        return;
                    }
                }
                adController.hide();
            }
        };
    }
    // endregion

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        bannerAd.destroy();
    }
}

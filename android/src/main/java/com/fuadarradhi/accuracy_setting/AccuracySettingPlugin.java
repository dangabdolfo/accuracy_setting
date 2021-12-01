package com.fuadarradhi.accuracy_setting;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** AccuracySettingPlugin */
public class AccuracySettingPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;
  private Context mContext;

  public static final String channelName = "accuracy_setting";


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    this.mContext = binding.getApplicationContext();
    channel = new MethodChannel(binding.getBinaryMessenger(), AccuracySettingPlugin.channelName);
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onMethodCall(MethodCall call,Result result) {

    if(mContext == null){
      result.error("-1", "Plugin not attached", "You have to attach the plugin before call method");
    }

    LocationManager locationManager = (LocationManager) mContext
            .getSystemService(mContext.LOCATION_SERVICE);

    switch (call.method){
      case "getAccuracySetting":
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
          try {
            int mode = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE);
            result.success(mode);
          } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
          }
        } else {
          result.success(4);
        }
        break;
      case "getGPSEnabled":
        boolean gpsActive = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        result.success(gpsActive);
        break;
      case "getNetworkEnabled":
        boolean networkActive = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        result.success(networkActive);
        break;
      default:
        result.notImplemented();
        break;
    }
  }
}

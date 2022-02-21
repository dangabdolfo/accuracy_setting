import Flutter
import Foundation
import UIKit
import CoreLocation

public class SwiftAccuracySettingPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "accuracy_setting", binaryMessenger: registrar.messenger())
        let instance = SwiftAccuracySettingPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        switch call.method {
        case "getAccuracySetting":
        break;
        case "getGPSEnabled":
            let gpsEnabled: Bool = CLLocationManager.locationServicesEnabled()
            result(gpsEnabled)
        break;
        case "getNetworkEnabled":
            let wifiEnabled : Bool =  isWifiEnabled()
            result(wifiEnabled)
        break;
        case "deviceHasGps":
            let hasGps: Bool = deviceHasGps()
            result(hasGps)
        break;
        default:
          result(FlutterMethodNotImplemented)
        }
    }

    func isWifiEnabled() -> Bool {
        var hasWiFiNetwork: Bool = false
        let interfaces: NSArray = CFBridgingRetain(CNCopySupportedInterfaces()) as! NSArray

        for interface  in interfaces {
            let networkInfo: [AnyHashable: Any]? = CFBridgingRetain(CNCopyCurrentNetworkInfo(((interface) as! CFString))) as? [AnyHashable : Any]
            if (networkInfo != nil) {
                hasWiFiNetwork = true
                break
            }
        }
        return hasWiFiNetwork;
    }
    
    func deviceHasGps() -> Bool {
        var hasGps: Bool = false
        
        return hasGps;
    }
}

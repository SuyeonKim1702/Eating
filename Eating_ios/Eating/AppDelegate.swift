//
//  AppDelegate.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/02.
//

import UIKit
import KakaoSDKCommon
import KakaoSDKAuth

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        if (AuthApi.isKakaoTalkLoginUrl(url)) {

          return AuthController.handleOpenUrl(url: url)
        }
       return false
       }
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        KakaoSDKCommon.initSDK(appKey: Constant.kakaoNativeKey)
        return true
    }
}


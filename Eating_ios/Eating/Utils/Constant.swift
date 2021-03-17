//
//  Constant.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/03.
//

import Foundation

class Constant {
    static let kakaoNativeKey = "7b287bd0f2209ab8c9c1779af8b9bf93"
    static let kakaoRestApiKey = "b946a901e7f94cbeeea950f9d9bb27fa"
    static let scheme = "https"
    static let kakaoHost = "dapi.kakao.com"
    static let locationPermissionString = "위치 서비스를 사용할 수 없습니다. 기기의 '설정 > 개인정보 보호'에서 위치 서비스를 켜주세요."
    
    enum ApiId: String {
        case kakaoAddressApi = "/v2/local/search/keyword.json"
        case kakaoRegionCodeApi = "/v2/local/geo/coord2address.json"
    }
}



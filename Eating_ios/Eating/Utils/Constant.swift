//
//  Constant.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/03.
//

import Foundation

class Constant {
    static var userId = ""
    static var address = ""
    static var nickname = ""
    static var profileImageData: Data?

    static let kakaoNativeKey = "7b287bd0f2209ab8c9c1779af8b9bf93"
    static let kakaoRestApiKey = "b946a901e7f94cbeeea950f9d9bb27fa"
    static let scheme = "https"
    static let schemeHttp = "http"
    static let endpoint = "3.34.214.72"
    static let kakaoHost = "dapi.kakao.com"
    static let locationPermissionString = "위치 서비스를 사용할 수 없습니다. 기기의 '설정 > 개인정보 보호'에서 위치 서비스를 켜주세요."
    
    enum ApiId: String {
        case kakaoAddressApi = "/v2/local/search/keyword.json"
        case kakaoRegionCodeApi = "/v2/local/geo/coord2address.json"
        case user = "/member"
        case post = "/post"
        case postList = "/posts"
    }

    enum menu: Int {
        case korean = 0
        case schoolFood = 1
        case chicken = 2
        case asian = 3
        case nightSnack = 4
        case japanese = 5
        case cafe = 6
        case pizza = 7
        case chinese = 8
        case fastCood = 9
    }

    enum MeetPlace: Int {
        case host = 0
        case mid = 1
        case undefined = 2
        var text: String {
            switch self {
            case .host:
                return "호스트쪽"
            case .mid:
                return "중간"
            case .undefined:
                return "장소협의"
            }
        }
    }

    enum DeliveryFee: Int {
        case equally = 0
        case host = 1
        case guest = 2
        var text: String {
            switch self {
            case .equally:
                return "각자부담"
            case .host:
                return "호스트 부담"
            case .guest:
                return "없음"
            }
        }
    }

    static func getMenuCode(raw: Int) -> Int {
        if raw < 5 {
            return raw * 2
        } else {
            return (raw - 5) * 2 + 1
        }
    }
}



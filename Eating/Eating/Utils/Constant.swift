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
    
    enum ApiId: String {
        case kakaoAddressApi = "/v2/local/search/keyword.json"
    }
}



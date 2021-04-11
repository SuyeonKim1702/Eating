//
//  LoginResponse.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/29.
//

import Foundation

class PostResponse: Codable {
    var data: AddressResponse
}

class AddressResponse: Codable {
    var address: String
    var latitude: Double
    var longitude: Double
}

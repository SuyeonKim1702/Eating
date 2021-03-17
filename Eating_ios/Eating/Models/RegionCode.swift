//
//  RegionCode.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import Foundation

struct RegionCode: Codable {
    let documents: [RegionCodeAddress]
}

struct RegionCodeAddress: Codable {
    let address: AddressInfo
}

struct AddressInfo: Codable {
    enum CodingKeys: String, CodingKey {
        case addressName = "address_name"
    }
    let addressName: String
}

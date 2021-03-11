//
//  Address.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import Foundation

struct Address: Codable {
    let documents: [Place]
}

struct Place: Codable {
    enum CodingKeys: String, CodingKey {
       case placeName = "place_name"
       case addressName = "address_name"
       case x
       case y
     }
    
    let placeName: String
    let addressName: String
    let x: String
    let y: String
}


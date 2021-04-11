//
//  Review.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/30.
//

import Foundation

struct ReviewResponse: Codable {
    var data: [Review]
}

struct Review: Codable {
    var review: String
    var senderNickname: String
    var writeDate: String
}

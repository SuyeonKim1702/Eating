//
//  Profile.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/29.
//

import Foundation

struct ProfileResponse: Codable {
    var data: Profile
}

struct Profile: Codable {
    var fastAnswer: Int
    var foodDivide: Int
    var niceGuy: Int
    var nickname: String
    var sugarScore: Int
    var timeGood: Int
    var totalCount: Int
}

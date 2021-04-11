//
//  FeedDetail.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import Foundation

struct FeedDetailResponse: Codable {
    let data: FeedDetail
}

struct FeedDetail: Codable {
    let category: Int
    let categoryURL: String
    let chatLink: String
    let currentMemberCount: Int
    let deliveryFeeByHost: Int
    let description: String
    let finished: Bool
    let foodLink: String
    let meetPlace: Int
    let memberCountLimit: Int
    let orderTime: String
    let title: String
    let writer: String
    let sugerScore: Double
    let favorite: Bool
}

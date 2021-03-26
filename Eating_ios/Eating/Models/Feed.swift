//
//  Feed.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import Foundation


struct Response: Codable {
    let data: FeedList
}

struct FeedList: Codable {
    let posts: [Feed]
}

struct Feed: Codable {
    let title: String
    let foodLink: String
    let deliveryFeeByHost: Int
    let meetPlace: Int
    let memberCount: Int
    let categoryImage: String
    let memberCountLimit: Int
    let orderTime: String
    let distance: Int
    let isFavorite: Bool
}

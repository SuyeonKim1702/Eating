//
//  ReviewMember.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/30.
//

import Foundation

struct ReviewMemberResponse: Codable {
    var data: [ReviewMember]
}

struct ReviewMember: Codable {
    var kakaoId: String
    var nickName: String
    var postId: Int
}

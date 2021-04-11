//
//  GetFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import UIKit

class GetReviewMemberService {
    let networkManager = NetworkManager()

    func getReviewMemberList(completion: @escaping (Result<[ReviewMember], NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.reviewMember.rawValue

        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, parseMember(_:), nil).resume()
    }


    func parseMember(_ data: Data) -> (Result<[ReviewMember], NetworkError>) {
        let decoder = JSONDecoder()
        do {
            let response = try decoder.decode(ReviewMemberResponse.self, from: data)
            let member = response.data
            return .success(member)
        } catch {
            return .failure(.decodingError)
        }

    }
}

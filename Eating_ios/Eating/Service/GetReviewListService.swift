//
//  GetDetailFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import Foundation

class GetReviewListService {
    let networkManager = NetworkManager()

    func getReviewList(completion: @escaping (Result<[Review], NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.review.rawValue

        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, parseReview(_:), nil).resume()
    }

    func parseReview(_ data: Data) -> (Result<[Review], NetworkError>) {
        let decoder = JSONDecoder()
        do {
            let response = try decoder.decode(ReviewResponse.self, from: data)
            let reviewList = response.data
            return .success(reviewList)
        } catch {
            return .failure(.decodingError)
        }
    }
}


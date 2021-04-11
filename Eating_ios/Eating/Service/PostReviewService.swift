//
//  PostFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import Foundation

class PostReviewService {
    let networkManager = NetworkManagerForPost()

    func postReview(reviewScore: Int, fastAnswer: Bool, foodDivide: Bool, niceGuy: Bool, timeGood: Bool, receiverId: String, review: String, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.review.rawValue

        var json = [String:Any]()
        json["fastAnswer"] = fastAnswer
        json["foodDivide"] = foodDivide
        json["niceGuy"] = niceGuy
        json["timeGood"] = timeGood
        json["receiverId"] = receiverId
        json["review"] = review
        json["reviewScore"] = reviewScore

        do {
            let data = try JSONSerialization.data(withJSONObject: json, options: [])
            var request = URLRequest(url: URL(string: urlComponents.url!.absoluteString)!)
            request.httpMethod = "POST"
            request.httpBody = data
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")
            networkManager.request(request, nil, completion).resume()
        } catch {
            completion(.failure(.urlError))
        }
    }
}


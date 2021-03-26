//
//  PostFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import Foundation

class PostFeedService {
    let networkManager = NetworkManagerForPost()

    func postUserInfo(category: Int, chatLink: String?, deliveryFeeByHost: Int, description: String, foodLink: String?, meetPlace: Int, memberCountLimit: Int, orderTime: String, title: String, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.post.rawValue

        var json = [String:Any]()
        json["category"] = category
        json["chatLink"] = chatLink
        json["deliveryFeeByHost"] = deliveryFeeByHost
        json["description"] = description
        json["foodLink"] = foodLink
        json["meetPlace"] = meetPlace
        json["memberCountLimit"] = memberCountLimit
        json["orderTime"] = orderTime
        json["title"] = title


        do {
            let data = try JSONSerialization.data(withJSONObject: json, options: [])
            var request = URLRequest(url: URL(string: urlComponents.url!.absoluteString)!)
            request.httpMethod = "POST"
            request.httpBody = data
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")
            networkManager.request(request, completion).resume()
        } catch {
            completion(.failure(.urlError))
        }
    }
}


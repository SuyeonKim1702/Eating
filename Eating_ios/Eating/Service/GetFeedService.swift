//
//  GetFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import UIKit

class GetFeedService {
    let networkManager = NetworkManager()

    func getFeedList(apiType: Constant.ApiId, page: Int, category: String = "0-1-2-3-4-5-6-7-8-9", distance: Int = 500, mine: Int, completion: @escaping (Result<[Feed], NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = apiType.rawValue
        urlComponents.queryItems = [
            URLQueryItem(name: "size", value: "10"),
            URLQueryItem(name: "page", value: "\(page)"),
            URLQueryItem(name: "category", value: category),
            URLQueryItem(name: "distance", value: "\(distance)"),
            URLQueryItem(name: "mine", value: "\(mine)")
        ]
        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        print(urlString)
        networkManager.request(url, completion, parseFeed(_:), nil).resume()
    }


    func parseFeed(_ data: Data) -> (Result<[Feed], NetworkError>) {
        let decoder = JSONDecoder()
        do {
            let response = try decoder.decode(Response.self, from: data)
            let feeds = response.data
            return .success(feeds)
        } catch {
            return .failure(.decodingError)
        }

    }
}

//
//  GetFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import UIKit

class GetFeedService {
    let networkManager = NetworkManager()

    func getFeedList(page: Int, completion: @escaping (Result<[Feed], NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.postList.rawValue
        urlComponents.queryItems = [
            URLQueryItem(name: "size", value: "10"),
            URLQueryItem(name: "page", value: "\(page)"),
            URLQueryItem(name: "category", value: "0-1-2-3-4-5-6-7-8-9")
        ]
        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, parseFeed(_:), nil).resume()
    }


    func parseFeed(_ data: Data) -> (Result<[Feed], NetworkError>) {
        let decoder = JSONDecoder()
        do {
            print("?")
            print(String(data: data, encoding: .utf8))
            let response = try decoder.decode(Response.self, from: data)
            let feeds = response.data.posts
            return .success(feeds)
        } catch {
            return .failure(.decodingError)
        }

    }
}

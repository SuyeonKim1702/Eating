//
//  GetDetailFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import Foundation

class GetDetailFeedService {
    let networkManager = NetworkManager()

    func getFeedList(idx: Int, completion: @escaping (Result<Feed, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = "\(Constant.ApiId.post.rawValue)?\(idx)"

        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, parseDetailFeed(_:), nil).resume()
    }

    //고쳐야 함 ..
    func parseDetailFeed(_ data: Data) -> (Result<Feed, NetworkError>) {
        let decoder = JSONDecoder()
        return .failure(.clientError)
    }
}


//
//  GetDetailFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import Foundation

class GetDetailFeedService {
    let networkManager = NetworkManager()

    func getFeedDetail(index: Int, completion: @escaping (Result<FeedDetail, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = "\(Constant.ApiId.post.rawValue)/\(index)"

        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, parseDetailFeed(_:), nil).resume()
    }

    func parseDetailFeed(_ data: Data) -> (Result<FeedDetail, NetworkError>) {
        let decoder = JSONDecoder()
        do {
            let response = try decoder.decode(FeedDetailResponse.self, from: data)
            let feed = response.data
            return .success(feed)
        } catch {
            return .failure(.decodingError)
        }
    }
}


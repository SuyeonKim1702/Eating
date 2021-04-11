//
//  GetDetailFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import Foundation

class PostStateChangeService {
    let networkManager = NetworkManager()

    func changeState(idx: Int, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = "\(Constant.ApiId.state.rawValue)/\(idx)"

        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, nil, nil).resume()
    }
}


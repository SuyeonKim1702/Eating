//
//  GetFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import UIKit

class FavoriteService {
    let networkManager = NetworkManagerForFavorite()

    func changeFavoriteState(postIdx: Int, state: Bool, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        if state {
            urlComponents.path = "\(Constant.ApiId.favorite.rawValue)/\(postIdx)"
        } else {
            urlComponents.path = "\(Constant.ApiId.unfavorite.rawValue)/\(postIdx)"
        }

        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, nil, nil).resume()
    }

    func postParticipateChat(postIdx: Int, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = "\(Constant.ApiId.joinPost.rawValue)/\(postIdx)"

        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, nil, nil).resume()
    }
}

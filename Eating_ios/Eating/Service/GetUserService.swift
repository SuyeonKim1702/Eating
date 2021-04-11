//
//  GetDetailFeedService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import Foundation

class GetUserService {
    let networkManager = NetworkManager()

    func getUserInfo(completion: @escaping (Result<Profile, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.profile.rawValue

        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, parseProfile(_:), nil).resume()
    }

    func parseProfile(_ data: Data) -> (Result<Profile, NetworkError>) {
        let decoder = JSONDecoder()
        do {
            let response = try decoder.decode(ProfileResponse.self, from: data)
            let profile = response.data
            return .success(profile)
        } catch {
            return .failure(.decodingError)
        }
    }
}


//
//  PostUserService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/22.
//

import UIKit

class LogoutService {
    let networkManager = NetworkManagerForPost()

    func logout(completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.logout.rawValue

        var request = URLRequest(url: URL(string: urlComponents.url!.absoluteString)!)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        networkManager.request(request, nil, completion).resume()
    }
}

//
//  PostUserService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/22.
//

import UIKit

class DeletePostService {
    let networkManager = NetworkManagerForPost()

    func deletePost(postIdx: Int, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = "\(Constant.ApiId.post.rawValue)/\(postIdx)"

            var request = URLRequest(url: URL(string: urlComponents.url!.absoluteString)!)
            request.httpMethod = "DELETE"
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")
            networkManager.request(request, nil, completion).resume()
    }
}

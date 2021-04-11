//
//  PostUserService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/22.
//

import UIKit

class LoginService {
    let networkManager = NetworkManagerForPost()

    func postUserInfo(id: String, completion: @escaping (Result<AddressResponse, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.login.rawValue
        var json = [String:Any]()
        json["kakaoId"] = id

        do {
            let data = try JSONSerialization.data(withJSONObject: json, options: [])
            var request = URLRequest(url: URL(string: urlComponents.url!.absoluteString)!)
            request.httpMethod = "POST"
            request.httpBody = data
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")
            networkManager.request(request, parseLoginResponse(_:), completion).resume()
        } catch {

        }
    }

    func parseLoginResponse(_ data: Data) -> (Result<AddressResponse, NetworkError>) {
        let decoder = JSONDecoder()
        do {
            let response = try decoder.decode(PostResponse.self, from: data)
            let address = response.data
            return .success(address)
        } catch {
            return .failure(.decodingError)
        }
    }

    func updateUserInfo(nickname: String, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.profile.rawValue
        var json = [String:Any]()
        json["nickname"] = nickname

        do {
            let data = try JSONSerialization.data(withJSONObject: json, options: [])
            var request = URLRequest(url: URL(string: urlComponents.url!.absoluteString)!)
            request.httpMethod = "PUT"
            request.httpBody = data
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")
            networkManager.request(request, nil, completion).resume()
        } catch {

        }
    }
}

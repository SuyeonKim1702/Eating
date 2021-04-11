//
//  PostUserService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/22.
//

import UIKit

class UpdateAddressService {
    let networkManager = NetworkManagerForPost()

    func updateAddress(address: String, latitude: Double?, longitude: Double?, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.address.rawValue
        var json = [String:Any]()
        json["address"] = address
        json["latitude"] = latitude!
        json["longitude"] = longitude!

        do {
            let data = try JSONSerialization.data(withJSONObject: json, options: [])
            print(urlComponents)
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

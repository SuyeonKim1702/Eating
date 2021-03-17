//
//  AddressService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import Foundation

class AddressRegionCodeService {
    let networkManager = NetworkManager()
    
    func getAddressInfo(x: String, y: String, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.scheme
        urlComponents.host = Constant.kakaoHost
        urlComponents.path = Constant.ApiId.kakaoRegionCodeApi.rawValue
        urlComponents.queryItems = [
            URLQueryItem(name: "x", value: x),
            URLQueryItem(name: "y", value: y)
        ]
        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, parseAddress(_:), "KakaoAK \(Constant.kakaoRestApiKey)").resume()
    }

    
    func parseAddress(_ data: Data) -> (Result<String, NetworkError>) {
        let decoder = JSONDecoder()
        do {
            let response = try decoder.decode(RegionCode.self, from: data)
            guard let address = response.documents[safe: 0]?.address.addressName else { return .failure(.decodingError) }
            return .success(address)
        } catch {
            return .failure(.decodingError)
        }
        
    }

}

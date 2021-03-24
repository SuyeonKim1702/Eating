//
//  AddressService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import Foundation

class AddressService{
    let networkManager = NetworkManager()
    
    func getAddressInfo(for keyword: String, page: Int, completion: @escaping (Result<[Place], NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.scheme
        urlComponents.host = Constant.kakaoHost
        urlComponents.path = Constant.ApiId.kakaoAddressApi.rawValue
        urlComponents.queryItems = [
            URLQueryItem(name: "query", value: keyword),
            URLQueryItem(name: "page", value: "\(page)")
        ]
        guard let urlString = urlComponents.url?.absoluteString, let url = URL(string: urlString) else {
            return completion(.failure(.urlError))
        }
        networkManager.request(url, completion, parseAddress(_:), "KakaoAK \(Constant.kakaoRestApiKey)").resume()
    }

    
    func parseAddress(_ data: Data) -> (Result<[Place], NetworkError>) {
        let decoder = JSONDecoder()
        do {
            let response = try decoder.decode(Address.self, from: data)
            let places = response.documents
            return .success(places)
        } catch {
            return .failure(.decodingError)
        }
        
    }

}

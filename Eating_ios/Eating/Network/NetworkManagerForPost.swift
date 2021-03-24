//
//  NetworkManager.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import Foundation

class NetworkManagerForPost {
    func request<T: Any>(_ request: URLRequest, _ completion: @escaping (Result<T, NetworkError>) -> Void) -> URLSessionDataTask {
        let request = request

        let config = URLSessionConfiguration.default
        config.requestCachePolicy = .reloadIgnoringLocalCacheData
        config.urlCache = nil

        let session = URLSession.init(configuration: config)
        let successRange = 200..<300
        let dataTask = session.dataTask(with: request) { [weak self] data, response, error in
            print(String(data: data!, encoding: .utf8))
            print((response as? HTTPURLResponse)?.statusCode)
            guard self != nil else {
                completion(.failure(.clientError))
                return
            }
            guard error == nil,
                  let statusCode = (response as? HTTPURLResponse)?.statusCode,
                  successRange.contains(statusCode) else {
                completion(.failure(.serverError))
                return
            }
            guard let unwrappedData = data else {
                completion(.failure(.noData))
                return
            }
            guard let data = unwrappedData as? T  else { return }
            completion(.success(data))
        }
        return dataTask
    }
}

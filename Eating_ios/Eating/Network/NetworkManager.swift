//
//  NetworkManager.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import Foundation

class NetworkManager {
    func request<T: Any>(_ url: URL, _ completion: @escaping (Result<T, NetworkError>) -> Void, _ parser: ((Data) -> (Result<T, NetworkError>))?, _ key: String?) -> URLSessionTask {
        var request = URLRequest(url: url)

        if let key = key {
            request.setValue(key, forHTTPHeaderField: "Authorization")
        }
        
        let config = URLSessionConfiguration.default
        config.requestCachePolicy = .reloadIgnoringLocalCacheData
        config.urlCache = nil
        
        let session = URLSession.init(configuration: config)
        let successRange = 200..<300
        let dataTask = session.dataTask(with: request) { [weak self] data, response, error in
            if data != nil {
                print(String(data: data!, encoding: .utf8)!)
            }

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
            if let parser = parser {
                completion(parser(unwrappedData))
            } else {
                guard let unwrappedData = unwrappedData as? T  else { return }
                    completion(.success(unwrappedData))
            }
        }
        return dataTask
    }
}

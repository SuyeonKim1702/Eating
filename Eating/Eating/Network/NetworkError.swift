//
//  NetworkError.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import Foundation

enum NetworkError: Error {
    case urlError
    case noData
    case clientError
    case serverError
    case decodingError
}

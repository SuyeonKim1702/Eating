//
//  PostUserService.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/22.
//

import UIKit

class PostUserService {
    let networkManager = NetworkManagerForPost()

    func postUserInfo(id: String, nickname: String?, latitude: Double?, longitude: Double?, address: String?, completion: @escaping (Result<String, NetworkError>) -> Void) {
        var urlComponents = URLComponents()
        urlComponents.scheme = Constant.schemeHttp
        urlComponents.host = Constant.endpoint
        urlComponents.path = Constant.ApiId.user.rawValue
        var json = [String:Any]()
        json["nickname"] = nickname
        json["address"] = address!
        json["latitude"] = latitude!
        json["longitude"] = longitude!
        json["kakaoId"] = id
        /*

        let boundary = "Boundary-\(UUID().uuidString)"
        let fileName = "profileImage.png"
        guard let imageData = Constant.profileImageData else { return }
        
        let body : [String: String] = [
            "kakaoId": id,
            "nickname": nickname ?? "",
            "latitude": "\(latitude!)",
            "longitude": "\(longitude!)",
            "address": address ?? ""
        ]

        let bodyData = createBody(parameters: body,
                                  boundary: boundary,
                                  data: imageData,
                                  mimeType: "image/png",
                                  filename: fileName)
    */

        do {
            let data = try JSONSerialization.data(withJSONObject: json, options: [])
            var request = URLRequest(url: URL(string: urlComponents.url!.absoluteString)!)
            request.httpMethod = "POST"
            request.httpBody = data
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            request.addValue("application/json", forHTTPHeaderField: "Accept")
            //request.httpBody = bodyData as Data
            //request.setValue("multipart/form-data; boundary=\(boundary)", forHTTPHeaderField: "Content-Type")
            networkManager.request(request, nil, completion).resume()
        } catch {

        }

    }

    private func createBody(parameters: [String: String], boundary: String, data: Data, mimeType: String, filename: String) -> Data {
        var body = Data()
        let imgDataKey = "profile"
        let boundaryPrefix = "--\(boundary)\r\n"

        for (key, value) in parameters {
            body.append(boundaryPrefix.data(using: .utf8)!)
            body.append("Content-Disposition: form-data; name=\"\(key)\"\r\n\r\n".data(using: .utf8)!)
            body.append("\(value)\r\n".data(using: .utf8)!)
        }

        // 이미지 더하는 부분
        body.append(boundaryPrefix.data(using: .utf8)!)
        body.append("Content-Disposition: form-data; name=\"\(imgDataKey)\"; filename=\"\(filename)\"\r\n".data(using: .utf8)!)
        body.append("Content-Type: \(mimeType)\r\n\r\n".data(using: .utf8)!)
        body.append(data)
        body.append("\r\n".data(using: .utf8)!)
        body.append("--".appending(boundary.appending("--")).data(using: .utf8)!)

        return body as Data
    }

}

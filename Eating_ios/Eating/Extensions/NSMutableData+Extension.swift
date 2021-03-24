//
//  NSMutableData+Extension.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/23.
//

import Foundation


extension NSMutableData {

    func appendString(_ string: String) {
        let data = string.data(using: String.Encoding.utf8, allowLossyConversion: true)
        append(data!)
    }
}


//
//  Collection+Extension.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import Foundation

extension Collection {
    subscript (safe index: Index) -> Element? {
        indices.contains(index) ? self[index] : nil
    }
}

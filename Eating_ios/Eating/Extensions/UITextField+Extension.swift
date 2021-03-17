//
//  UITextField+Extension.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/06.
//

import UIKit

extension UITextField {
    func addLeftPadding() {
        let paddingView = UIView(frame: CGRect(x: 0, y: 0, width: 30, height: self.frame.height))
        self.leftView = paddingView
        self.leftViewMode = ViewMode.always
    }
    
    
    func addleftimage(image:UIImage) {
        let leftimage = UIImageView(frame: CGRect(x: 10, y: 0, width: image.size.width, height: image.size.height))
        leftimage.image = image
        let view = UIView(frame: CGRect(x: 0, y: 0, width: image.size.width + 20, height: image.size.height))
        view.addSubview(leftimage)
        self.leftView = view
        self.leftViewMode = .always
    }
}


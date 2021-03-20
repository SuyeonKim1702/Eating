//
//  UISegmentedControl.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/20.
//

import UIKit

extension UISegmentedControl{
    func removeBorder(){
        let backgroundImage = UIImage.getColoredRectImageWith(color: UIColor.white.cgColor, andSize: self.bounds.size)
        self.setBackgroundImage(backgroundImage, for: .normal, barMetrics: .default)
        self.setBackgroundImage(backgroundImage, for: .selected, barMetrics: .default)
        self.setBackgroundImage(backgroundImage, for: .highlighted, barMetrics: .default)

        let deviderImage = UIImage.getColoredRectImageWith(color: UIColor.white.cgColor, andSize: CGSize(width: 1.0, height: self.bounds.size.height))
        self.setDividerImage(deviderImage, forLeftSegmentState: .selected, rightSegmentState: .normal, barMetrics: .default)
    }

    func addUnderlineForSelectedSegment(){
        removeBorder()
        let greyUnderlineWidth: CGFloat = self.bounds.size.width
        let greyUnderlineHeight: CGFloat = 1.0
        let underlineWidth: CGFloat = self.bounds.size.width / CGFloat(self.numberOfSegments) - 72
        let underlineHeight: CGFloat = 4.0
        let underlineXPosition = CGFloat(selectedSegmentIndex * Int(underlineWidth)) + 36
        let underLineYPosition = self.bounds.size.height - 8.0
        let greyUnderlineYPosition = underLineYPosition - 1.0

        let greyUnderline = UIView(frame: CGRect(x: 0, y: greyUnderlineYPosition, width: greyUnderlineWidth, height: greyUnderlineHeight))

        let underlineFrame = CGRect(x: underlineXPosition, y: underLineYPosition, width: underlineWidth, height: underlineHeight)
        let underline = UIView(frame: underlineFrame)
        underline.backgroundColor = UIColor(red: 253, green: 220, blue: 33)
        underline.tag = 1
        greyUnderline.backgroundColor = UIColor(red: 238, green: 238, blue: 238)
        self.addSubview(greyUnderline)
        self.addSubview(underline)
    }

    func changeUnderlinePosition(){
        guard let underline = self.viewWithTag(1) else {return}
        let underlineFinalXPosition = (self.bounds.width / CGFloat(self.numberOfSegments)) * CGFloat(selectedSegmentIndex) + 36
        UIView.animate(withDuration: 0.1, animations: {
            underline.frame.origin.x = underlineFinalXPosition
        })
    }
}

extension UIImage{

    class func getColoredRectImageWith(color: CGColor, andSize size: CGSize) -> UIImage{
        UIGraphicsBeginImageContextWithOptions(size, false, 0.0)
        let graphicsContext = UIGraphicsGetCurrentContext()
        graphicsContext?.setFillColor(color)
        let rectangle = CGRect(x: 0.0, y: 0.0, width: size.width, height: size.height)
        graphicsContext?.fill(rectangle)
        let rectangleImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return rectangleImage!
    }
}


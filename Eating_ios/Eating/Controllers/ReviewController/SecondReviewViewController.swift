//
//  SecondReviewViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/17.
//

import UIKit

class SecondReviewViewController: UIViewController {
    @IBOutlet var goodImageView: UIImageView?
    @IBOutlet var sosoImageView: UIImageView?
    @IBOutlet var badImageView: UIImageView?
    @IBOutlet var firstButton: UIButton?
    override func viewDidLoad() {
        super.viewDidLoad()


        let tapGestureRecognizerForImage = UITapGestureRecognizer(target: self, action: #selector(tapImage))
        goodImageView?.addGestureRecognizer(tapGestureRecognizerForImage)

    }
    @objc func tapImage(_ sender: UITapGestureRecognizer) {
        let imageView = sender.view as? UIImageView
        imageView?.isHighlighted = !imageView!.isHighlighted



    }

    @IBAction func buttonClicked(_ sender: UIButton) {
        sender.isSelected = !sender.isSelected

    }

}

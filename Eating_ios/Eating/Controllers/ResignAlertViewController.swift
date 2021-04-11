//
//  ResignAlertViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/26.
//

import UIKit

class ResignAlertViewController: UIViewController {
    @IBOutlet var outerView: UIView!
    @IBOutlet var resignButton: UIButton!
    @IBOutlet var stayButton: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()

    }

    private func stylingViews() {
        outerView.layer.cornerRadius = Constant.alertButtonCornerRadius
        stayButton?.layer.cornerRadius = Constant.alertButtonCornerRadius
        resignButton?.layer.cornerRadius = Constant.alertButtonCornerRadius
        stayButton?.layer.borderWidth = 2
        stayButton?.layer.borderColor = UIColor(red: 253, green: 220, blue: 33).cgColor
    }

    @IBAction func goOutButton(_ sender: Any) {
        //탈퇴 로직 처리 -> 맨 처음 화면 띄워주기

    }

    @IBAction func goBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
}

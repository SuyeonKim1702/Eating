//
//  FillAlertViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/30.
//

import UIKit

class FillAlertViewController: UIViewController {

    @IBOutlet var outerView: UIView!
    @IBOutlet var confirmButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        confirmButton.layer.cornerRadius = 20
        outerView.layer.cornerRadius = 24

    }

    @IBAction func tapConfirmButton(_ sender: Any) {
        (presentingViewController as? WritingViewController)?.floatingButton.isHidden = false
        dismiss(animated: true, completion: nil)
    }
}

//
//  PrepareAlertViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/30.
//

import UIKit

class PrepareAlertViewController: UIViewController {

    @IBOutlet var outerView: UIView!
    @IBOutlet var completeButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()
    }

    private func stylingViews() {
        completeButton.layer.cornerRadius = 23
        outerView.layer.cornerRadius = 24
    }

     @IBAction func tapCompleteButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
     }
}

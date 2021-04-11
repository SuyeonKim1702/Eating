//
//  WritingAlertViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/26.
//

import UIKit

class WritingAlertViewController: UIViewController {
    weak var defaultReviewViewController: DefaultReviewViewController?
    weak var writingViewController: WritingViewController?
    @IBOutlet var goOutButton: UIButton!
    @IBOutlet var stayButton: UIButton!
    @IBOutlet var outerView: UIView!
    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()
    }

    private func stylingViews() {
        outerView.layer.cornerRadius = Constant.alertButtonCornerRadius
        stayButton?.layer.cornerRadius = Constant.alertButtonCornerRadius
        goOutButton?.layer.cornerRadius = Constant.alertButtonCornerRadius
        stayButton?.layer.borderWidth = 2
        stayButton?.layer.borderColor = UIColor(red: 253, green: 220, blue: 33).cgColor
    }

    @IBAction func stayButton(_ sender: Any) {
        (presentingViewController as? WritingViewController)?.floatingButton.isHidden = false
        dismiss(animated: true, completion: nil)

    }

    @IBAction func goOutButton(_ sender: Any) {
        dismiss(animated: true) {
            if self.defaultReviewViewController != nil {
                self.defaultReviewViewController?.dismiss(animated: true) {

                    let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
                    guard let tabBarController = storyboard.instantiateViewController(withIdentifier: "UITabBarController") as? UITabBarController else { return }

                    tabBarController.modalTransitionStyle = .coverVertical
                    tabBarController.modalPresentationStyle = .fullScreen
                    tabBarController.tabBar.tintColor = .black

                    self.defaultReviewViewController?.present(tabBarController, animated: true, completion: nil)
                }
            } else {
                self.writingViewController?.dismiss(animated: true, completion: nil)
            }
        }
    }

}

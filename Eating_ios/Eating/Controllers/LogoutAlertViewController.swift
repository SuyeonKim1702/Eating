//
//  LogoutAlertViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/26.
//

import UIKit

class LogoutAlertViewController: UIViewController {
    let logoutService = LogoutService()
    @IBOutlet var outerView: UIView!
    var settingViewController: SettingViewController?
    @IBOutlet var logoutButton: UIButton!
    @IBOutlet var stayButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()
    }

    private func stylingViews() {
        outerView.layer.cornerRadius = Constant.alertButtonCornerRadius
        stayButton?.layer.cornerRadius = Constant.alertButtonCornerRadius
        logoutButton?.layer.cornerRadius = Constant.alertButtonCornerRadius
        stayButton?.layer.borderWidth = 2
        stayButton?.layer.borderColor = UIColor(red: 253, green: 220, blue: 33).cgColor
    }

    @IBAction func goBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

    @IBAction func logoutButton(_ sender: Any) {
        logoutService.logout { [weak self] response in
            switch response {
            case .success(_ ):
                DispatchQueue.main.async {
                    Constant.check = false
                    UserDefaults.standard.removeObject(forKey: "kakaoId")
                    UserDefaults.standard.removeObject(forKey: "address")
                    self?.dismiss(animated: true) {

                            let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
                            guard let loginViewController = storyboard.instantiateViewController(withIdentifier: "LoginViewController") as? LoginViewController else { return }

                            loginViewController.modalTransitionStyle = .coverVertical
                            loginViewController.modalPresentationStyle = .fullScreen
                        self?.settingViewController?.present(loginViewController, animated: true, completion: nil)

                    }
                }

            case .failure(let error):
                print(error)
            }
        }
        
    }
}

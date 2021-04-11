//
//  StartActivity.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/28.
//

import Foundation

class StartViewController : UIViewController {
    let loginService = LoginService()

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if UserDefaults.standard.string(forKey: "kakaoId") != nil {
            callLoginApi(id: UserDefaults.standard.string(forKey: "kakaoId")!)
        } else {
            goToLoginPage()
        }
    }

    private func goToLoginPage() {
        guard let loginViewController = storyboard?.instantiateViewController(withIdentifier: "LoginViewController") as? LoginViewController else { return }
        loginViewController.modalTransitionStyle = .coverVertical
        loginViewController.modalPresentationStyle = .fullScreen
        present(loginViewController, animated: true, completion: nil)
    }

    private func goToMainPage() {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let tabBarController = storyboard.instantiateViewController(withIdentifier: "UITabBarController") as? UITabBarController else { return }

        tabBarController.modalTransitionStyle = .coverVertical
        tabBarController.modalPresentationStyle = .fullScreen
        tabBarController.tabBar.tintColor = .black

        present(tabBarController, animated: true, completion: nil)
    }

    private func callLoginApi(id: String) {
        loginService.postUserInfo(id: id) { [weak self] result in
            switch result {
            case .success(let data):
                DispatchQueue.main.async {
                    UserDefaults.standard.set(data.address, forKey: "address")
                    Constant.address = data.address

                    UserDefaults.standard.set(data.longitude, forKey: "longitude")
                    UserDefaults.standard.set(data.latitude, forKey: "latitude")
                    Constant.latitude = data.latitude
                    Constant.longitude = data.longitude
                    self?.goToMainPage()
                }

            case .failure(let error):
                print(error.localizedDescription)
                DispatchQueue.main.async {
                    self?.goToLoginPage()
                }
            }
        }
    }
}

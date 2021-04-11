//
//  ViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/02.
//

import UIKit
import KakaoSDKAuth
import KakaoSDKUser
import AuthenticationServices

class LoginViewController: UIViewController, ASAuthorizationControllerDelegate, ASAuthorizationControllerPresentationContextProviding {
    var kakaoUserId: String?
    @IBOutlet var kakaoStackView: UIStackView!
    @IBOutlet weak var kakaoLoginButton: UIButton?
    @IBOutlet weak var appleButtonPlaceHolder: UIView?
    let loginService = LoginService()
    override func viewDidLoad() {
        super.viewDidLoad()
        stylingButton()
        addTapGesture()
    }

    private func addTapGesture() {
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(tapKakaoLogInButton(_:)))
        kakaoStackView.addGestureRecognizer(tapGesture)
    }
        
    private func stylingButton() {
        kakaoLoginButton?.layer.cornerRadius = Constant.buttonConnerRadius
        if #available(iOS 13.0, *) {
            let appleLoginBtn = ASAuthorizationAppleIDButton(authorizationButtonType: .signIn, authorizationButtonStyle: .black)
            appleLoginBtn.isUserInteractionEnabled = true
            appleLoginBtn.translatesAutoresizingMaskIntoConstraints = false
            appleButtonPlaceHolder?.addSubview(appleLoginBtn)
            appleLoginBtn.frame = appleButtonPlaceHolder!.bounds
            appleLoginBtn.cornerRadius = Constant.buttonConnerRadius
            
            appleLoginBtn.addTarget(self, action: #selector(tapAppleLogInButton), for: .touchUpInside)
            appleLoginBtn.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 20).isActive = true
            appleLoginBtn.rightAnchor.constraint(equalTo: view.rightAnchor, constant: -20).isActive = true
            appleLoginBtn.heightAnchor.constraint(equalToConstant: 49).isActive = true

        } else {
            // Fallback on earlier versions
            //TODO: 알림창 띄워주기 버전 업데이트 관련해서
        }
    }
    
    @available(iOS 13.0, *)
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        if let credential = authorization.credential as? ASAuthorizationAppleIDCredential {
            let userIdentifier = credential.user
            Constant.userId = userIdentifier
            callLoginApi(id: Constant.userId)
        }
    }
    
    @available(iOS 13.0, *)
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        return self.view.window!
        
    }
    
    @objc func tapAppleLogInButton() {
        if #available(iOS 13.0, *) {
            let appleIDProvider = ASAuthorizationAppleIDProvider()
            let request = appleIDProvider.createRequest()
            let authorizationController = ASAuthorizationController(authorizationRequests: [request])
            authorizationController.delegate = self
            authorizationController.presentationContextProvider = self
            authorizationController.performRequests()
            
        } else {
            //TODO: alert 띄워주기 -> 버전 업그레이드 하도록
            // Fallback on earlier versions
        }
    }
    
    @IBAction func tapKakaoLogInButton(_ sender: Any) {
        UserApi.shared.loginWithKakaoAccount { [weak self] (oauthToken, error) in
            if let error = error {
                print(error)
            }
            else {
                sleep(UInt32(1))
                self?.fetchUserId()
            }
        }
    }
    
    private func fetchUserId() {
        UserApi.shared.me() { [weak self] (user, error) in
            if let error = error {
                print(error)
            }
            else {
                if let user = user {
                    self?.kakaoUserId = String(user.id)
                    Constant.userId = self?.kakaoUserId ?? ""
                    self?.callLoginApi(id: Constant.userId)
                }
            }
        }
    }

    private func callLoginApi(id: String) {
        loginService.postUserInfo(id: id) { [weak self] result in
            switch result {
            case .success(let data):
                DispatchQueue.main.async {
                    UserDefaults.standard.set(Constant.userId, forKey: "kakaoId")
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
                    self?.moveToNext()
                }
            }
        }
    }
    
    private func moveToNext() {
        guard let signUpViewController = storyboard?.instantiateViewController(withIdentifier: "SignUpViewController") else { return }
        signUpViewController.modalTransitionStyle = .coverVertical
        signUpViewController.modalPresentationStyle = .fullScreen
        present(signUpViewController, animated: true, completion: nil)
    }

    private func goToMainPage() {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let tabBarController = storyboard.instantiateViewController(withIdentifier: "UITabBarController") as? UITabBarController else { return }

        tabBarController.modalTransitionStyle = .coverVertical
        tabBarController.modalPresentationStyle = .fullScreen
        tabBarController.tabBar.tintColor = .black

        present(tabBarController, animated: true, completion: nil)
    }
}


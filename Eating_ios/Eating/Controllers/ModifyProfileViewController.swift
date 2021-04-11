//
//  ModifyProfileViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/26.
//

import UIKit

class ModifyProfileViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet var profileImageHolder: UIImageView!
    @IBOutlet var underlineLabel: UILabel!
    @IBOutlet var nicknameTextField: UITextField!
    var nickname = String()
    let updateProfileService = LoginService()


    override func viewDidLoad() {
        super.viewDidLoad()
        nicknameTextField?.delegate = self
        stylingViews()
        setupImageHolder()
        setupToolBar()
        setupGestureRecognizer()
        nicknameTextField.text = nickname
    }

    private func setupGestureRecognizer() {
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(showAlert))
        profileImageHolder.addGestureRecognizer(tapGesture)
    }

    @objc func tapImageHolder() {
        guard let prepareAlertViewController = storyboard?.instantiateViewController(withIdentifier: "PrepareAlertViewController") as? PrepareAlertViewController else { return }
        prepareAlertViewController.modalPresentationStyle = .overCurrentContext
        present(prepareAlertViewController, animated: true, completion: nil)
    }

    @IBAction func goBackButton(_ sender: Any) {
        if validateNickname(nicknameTextField.text ?? "") {
            updateProfileService.updateUserInfo(nickname: nicknameTextField.text!) { [weak self] result in
                switch result {
                case .success(_):
                    DispatchQueue.main.async {
                        print("success")
                        self?.dismiss(animated: true, completion: nil)
                    }
                case .failure(let error):
                    DispatchQueue.main.async {
                        print(error.localizedDescription)
                        self?.dismiss(animated: true, completion: nil)
                    }
                }
            }
        }
    }

    private func setupToolBar() {
        let toolBarKeyboard = UIToolbar(frame: CGRect(x: 0, y: 0, width: view.frame.width, height: 60))

        let flexibleSpace = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: self, action: nil)

        let btnDoneBar = UIBarButtonItem(image: UIImage(named: "checkButton"), style: .done, target: self, action: #selector(tapCheckButton(_:)))
        toolBarKeyboard.items = [flexibleSpace, btnDoneBar]
        toolBarKeyboard.barTintColor = .white
        toolBarKeyboard.clipsToBounds = true
        nicknameTextField?.inputAccessoryView = toolBarKeyboard
    }

    @objc private func tapCheckButton(_ sender: Any) {
        nicknameTextField.endEditing(true)
    }

    private func setupImageHolder() {
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(showAlert))
        profileImageHolder?.isUserInteractionEnabled = true
        profileImageHolder?.addGestureRecognizer(tapGestureRecognizer)
        profileImageHolder?.contentMode = .scaleAspectFill
        profileImageHolder?.layer.cornerRadius = 0.5 * (profileImageHolder?.bounds.size.width)!
    }

    @objc private func showAlert() {
        guard let alertViewController = storyboard?.instantiateViewController(withIdentifier: "AlertViewController") else { return }
        alertViewController.modalPresentationStyle = .overCurrentContext
        present(alertViewController, animated: true, completion: nil)
    }


    private func stylingViews() {
        nicknameTextField?.layer.cornerRadius = 32
        nicknameTextField?.addLeftPadding()
    }

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if let nickname = textField.text {
            if validateNickname(nickname) {
                goToNextPage()
            }
        }
        return true
    }

    func textFieldDidBeginEditing(_ textField: UITextField) {
        changeToOriginal()
    }

    private func validateNickname(_ text: String) -> Bool {
        if text.count < 3 || text.count > 8 {
            alertWrongNickname()
            return false
        } else {
            return true
        }
    }

    private func changeToOriginal() {
        underlineLabel?.textColor = UIColor(red: 193, green: 193, blue: 192)
        nicknameTextField?.layer.borderWidth = 0

    }

    private func alertWrongNickname() {
        underlineLabel?.textColor = .red
        nicknameTextField?.layer.borderWidth = 1
        nicknameTextField?.layer.borderColor = UIColor.red.cgColor
    }

    private func goToNextPage() {
        guard let addressViewController = storyboard?.instantiateViewController(withIdentifier: "AddressViewController") else { return }
        addressViewController.modalTransitionStyle = .coverVertical
        addressViewController.modalPresentationStyle = .fullScreen
        present(addressViewController, animated: true, completion: nil)
    }
}


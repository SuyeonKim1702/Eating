//
//  SignUpViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/06.
//

import UIKit

class SignUpViewController: UIViewController, UITextFieldDelegate {
    @IBOutlet weak var completeButton: UIButton?
    @IBOutlet weak var nicknameTextField: UITextField?
    @IBOutlet var profileImageHolder: UIImageView?

    
    override func viewDidLoad() {
        super.viewDidLoad()
        nicknameTextField?.delegate = self
        stylingViews()
        setupImageHolder()
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
        completeButton?.layer.cornerRadius = 32
        nicknameTextField?.layer.cornerRadius = 32
        nicknameTextField?.addLeftPadding()
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.endEditing(true)
        return true
    }
    
    @IBAction func tapGoBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func tapCompleteButton(_ sender: Any) {
        guard let addressViewController = storyboard?.instantiateViewController(withIdentifier: "AddressViewController") else { return }
        addressViewController.modalTransitionStyle = .coverVertical
        addressViewController.modalPresentationStyle = .fullScreen
        present(addressViewController, animated: true, completion: nil)
        
    }

}

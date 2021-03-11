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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        nicknameTextField?.delegate = self
        stylingViews()

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

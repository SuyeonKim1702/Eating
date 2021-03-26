//
//  AlertViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/21.
//

import UIKit

class AlertViewController: UIViewController {

    let imagePickerController = UIImagePickerController()
    weak var signUpViewController: SignUpViewController?

    override func viewDidLoad() {
        super.viewDidLoad()
        imagePickerController.delegate = self
        signUpViewController = parent as? SignUpViewController
    }

    @IBAction func tapTakePictureButton(_ sender: Any) {
        imagePickerController.sourceType = .camera
        present(imagePickerController, animated: true, completion: nil)
    }


    @IBAction func goToGallery(_ sender: Any) {
        imagePickerController.sourceType = .photoLibrary
        present(imagePickerController, animated: true, completion: nil)
    }
    @IBAction func closeAlert(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
}

extension AlertViewController : UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if let image = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            if let presenter = presentingViewController as? SignUpViewController {
                presenter.profileImageHolder?.image = image
                Constant.profileImageData = image.pngData()
            }
            dismiss(animated: true, completion: nil)
        }
        dismiss(animated: true, completion: nil)
    }

}

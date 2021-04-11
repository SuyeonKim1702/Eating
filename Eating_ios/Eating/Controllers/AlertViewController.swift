//
//  AlertViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/21.
//

import UIKit

class AlertViewController: UIViewController {

    @IBOutlet var outerView: UIView!
    @IBOutlet var selectFromGalleryButton: UIButton!
    @IBOutlet var takePictureButton: UIButton!
    let imagePickerController = UIImagePickerController()
    weak var signUpViewController: SignUpViewController?

    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()
        imagePickerController.delegate = self
        signUpViewController = parent as? SignUpViewController
    }

    private func stylingViews() {
        outerView.layer.cornerRadius = Constant.buttonConnerRadius
        takePictureButton?.layer.cornerRadius = Constant.buttonConnerRadius
        selectFromGalleryButton?.layer.cornerRadius = Constant.buttonConnerRadius
        takePictureButton?.layer.borderWidth = 2
        takePictureButton?.layer.borderColor = UIColor(red: 253, green: 220, blue: 33).cgColor

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
                UserDefaults.standard.set(Constant.profileImageData, forKey: "profileImage")
            } else if let presenter = presentingViewController as? ModifyProfileViewController {
                presenter.profileImageHolder?.image = image
                Constant.profileImageData = image.pngData()
                UserDefaults.standard.set(Constant.profileImageData, forKey: "profileImage")
            } else if let presenter = presentingViewController as? DefaultReviewViewController {
                (presenter.children[safe: 2] as? ThirdReviewViewController)?.photoImageView.image = image
            }
            dismiss(animated: true, completion: nil)
        }
        dismiss(animated: true, completion: nil)
    }

}

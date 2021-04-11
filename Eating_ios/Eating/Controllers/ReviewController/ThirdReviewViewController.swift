//
//  ThirdReviewViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/26.
//

import UIKit

class ThirdReviewViewController: UIViewController {

    @IBOutlet var nicknameLabel: UILabel!
    @IBOutlet var checkButton: UIButton!
    @IBOutlet var contentsTextView: UITextView!
    @IBOutlet var photoImageView: UIImageView!
    var time = Bool()
    var kindness = Bool()
    var division = Bool()
    var reply = Bool()
    var selectedButton = Int()
    let reviewService = PostReviewService()
    var receiverId: String?

    override func viewDidLoad() {
        super.viewDidLoad()
        setupGestureRecognizer()
        setupImageView()
        setupToolBar()
        stylingViews()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        nicknameLabel.text = "\((parent as! DefaultReviewViewController).nickname!)님에게 후기 메세지를 보내세요~"
        receiverId = (parent as? DefaultReviewViewController)?.userId

    }

    private func stylingViews() {
        checkButton.layer.shadowRadius = 5
        checkButton.layer.shadowColor = UIColor(rgb: 0x000000, alpha: 29).cgColor
        checkButton.layer.shadowOffset = CGSize(width: 0, height: 3)
        checkButton.layer.shadowOpacity = 0.29
    }

    private func dismissPage() {
        dismiss(animated: true) {
            let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
            guard let tabBarController = storyboard.instantiateViewController(withIdentifier: "UITabBarController") as? UITabBarController else { return }

            tabBarController.modalTransitionStyle = .coverVertical
            tabBarController.modalPresentationStyle = .fullScreen
            tabBarController.tabBar.tintColor = .black

            self.present(tabBarController, animated: true, completion: nil)
        }
    }

    @IBAction func completeButton(_ sender: Any) {

        let image = photoImageView.image
        let contents = contentsTextView.text
        if let userId = receiverId {
            reviewService.postReview(reviewScore: selectedButton, fastAnswer: reply, foodDivide: division, niceGuy: kindness, timeGood: time, receiverId: userId, review: contents ?? "") { [weak self] data in
                switch data {
                case .success(let result):
                    print(result)

                case .failure(let error):
                    print(error.localizedDescription)
                }
                DispatchQueue.main.async {
                    self?.dismissPage()
                }
            }
        }
    }

    private func setupToolBar() {
        let toolBarKeyboard = UIToolbar(frame: CGRect(x: 0, y: 0, width: view.frame.width, height: 60))

        let flexibleSpace = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: self, action: nil)

        let btnDoneBar = UIBarButtonItem(image: UIImage(named: "checkButton"), style: .done, target: self, action: #selector(hideKeyboard(_:)))
        toolBarKeyboard.items = [flexibleSpace, btnDoneBar]
        toolBarKeyboard.barTintColor = .white
        toolBarKeyboard.clipsToBounds = true
        contentsTextView?.inputAccessoryView = toolBarKeyboard
    }

    @objc private func hideKeyboard(_ sender: Any) {
        contentsTextView?.endEditing(true)
    }

    private func setupImageView() {
        photoImageView?.contentMode = .scaleAspectFill
        photoImageView?.layer.cornerRadius = 0.5 * (photoImageView?.bounds.size.width)!
    }

    private func setupGestureRecognizer() {
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(tapImageView(_:)))
        photoImageView.addGestureRecognizer(tapGesture)
    }

    @objc private func tapImageView(_ sender: Any) {
        guard let alertViewController = storyboard?.instantiateViewController(withIdentifier: "AlertViewController") else { return }
        alertViewController.modalPresentationStyle = .overCurrentContext
        present(alertViewController, animated: true, completion: nil)
    }
}

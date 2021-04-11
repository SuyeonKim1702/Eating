//
//  FirstReviewViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/16.
//

import UIKit

class FirstReviewViewController: UIViewController {

    @IBOutlet var meetPlaceLabel: PaddingLabel!
    @IBOutlet var deliveryLabel: PaddingLabel!
    @IBOutlet var titlLabel: UILabel!
    @IBOutlet var distanceLabel: UILabel!
    @IBOutlet var thumbnailImageView: UIImageView!
    @IBOutlet var nicknameLabel: UILabel!
    @IBOutlet var secondTagButton: PaddingLabel!
    @IBOutlet var firstTagButton: PaddingLabel!
    @IBOutlet var yesButton: UIButton!
    @IBOutlet var noButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()

    }

    private func stylingViews() {
        firstTagButton.clipsToBounds = true
        secondTagButton.clipsToBounds = true
        firstTagButton.layer.cornerRadius = 12
        secondTagButton.layer.cornerRadius = 12
        yesButton.layer.cornerRadius = 30
        noButton.layer.cornerRadius = 30
        noButton?.layer.borderWidth = 2
        noButton?.layer.borderColor = UIColor(red: 253, green: 220, blue: 33).cgColor

    }

    func updateUI(data: FeedDetail) {
        titlLabel.text = data.title
        thumbnailImageView.image = Constant.categoryImage[data.category]
        deliveryLabel.text = Constant.DeliveryFee(rawValue: data.deliveryFeeByHost)?.text
        meetPlaceLabel.text = Constant.MeetPlace(rawValue: data.meetPlace)?.text
        nicknameLabel.text = "\((parent as! DefaultReviewViewController).nickname!)님과 잇팅하셨나요?"
    }
    
     @IBAction func goToNextButton(_ sender: Any) {
        weak var parentViewController = parent as? DefaultReviewViewController
        parentViewController?.animateViewController(withDuration: 1, velocity: 2, metrics: ViewFrame(originY: UIScreen.main.bounds.height, height: 0), view: view)
        parentViewController?.animateViewController(withDuration: 1, velocity: 2, metrics: ViewFrame(originY: UIScreen.main.bounds.height*0.3, height: UIScreen.main.bounds.height*0.7), view: parentViewController?.secondView ?? UIView())
        (parent as? DefaultReviewViewController)?.floatingView.isHidden = false
        
     }
    
   
    @IBAction func goBackButton(_ sender: Any) {
        dismiss(animated: true, completion: {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let tabBarController = storyboard.instantiateViewController(withIdentifier: "UITabBarController") as? UITabBarController else { return }

        tabBarController.modalTransitionStyle = .coverVertical
        tabBarController.modalPresentationStyle = .fullScreen
        tabBarController.tabBar.tintColor = .black
            self.present(tabBarController, animated: true, completion: nil)
        })
    }
}

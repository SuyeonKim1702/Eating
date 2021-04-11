//
//  ProfileTableViewFourthHeader.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

class ProfileTableViewFourthHeader: UITableViewHeaderFooterView {
    var viewController: MyPageViewController?
    @IBOutlet var numOfReviewLabel: UILabel!
    @IBOutlet var outerView: UIView!


    override func awakeFromNib() {
        super.awakeFromNib()
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(goToNextPage(_:)))
        outerView.addGestureRecognizer(tapGesture)
    }


    @IBAction func goToNextPage(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let reviewListViewController = storyboard.instantiateViewController(withIdentifier: "ReviewListViewController") as? ReviewListViewController else { return }

        reviewListViewController.modalTransitionStyle = .coverVertical
        reviewListViewController.modalPresentationStyle = .fullScreen
        viewController?.present(reviewListViewController, animated: true, completion: nil)
    }

    func updateUI(num: Int) {
        numOfReviewLabel.text = "\(num)"
    }
}

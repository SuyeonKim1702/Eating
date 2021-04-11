//
//  ProfileTableViewSecondHeader.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

class ProfileTableViewSecondHeader: UITableViewHeaderFooterView {
    weak var viewController: MyPageViewController?
    @IBOutlet var outerView: UIView!

    override func awakeFromNib() {
        super.awakeFromNib()
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(goToActivityListButton(_:)))
        outerView.addGestureRecognizer(tapGesture)
    }

    @IBAction func goToActivityListButton(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let activityListViewController = storyboard.instantiateViewController(withIdentifier: "ActivityListViewController") as? ActivityListViewController else { return }
        activityListViewController.modalTransitionStyle = .coverVertical
        activityListViewController.modalPresentationStyle = .fullScreen

        viewController?.present(activityListViewController, animated: true, completion: nil)
    }
}

//
//  SettingViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import UIKit
import MessageUI

class SettingViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func goToBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

    @IBAction func logOutButton(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let logoutAlertViewController = storyboard.instantiateViewController(withIdentifier: "LogoutAlertViewController") as? LogoutAlertViewController else { return }

        logoutAlertViewController.modalTransitionStyle = .coverVertical
        logoutAlertViewController.settingViewController = self

        self.present(logoutAlertViewController, animated: true, completion: nil)
    }

    @IBAction func goToLicensePage(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let openSourceViewController = storyboard.instantiateViewController(withIdentifier: "OpenSourceLicenseViewController") as? OpenSourceLicenseViewController else { return }

        openSourceViewController.modalTransitionStyle = .coverVertical
        openSourceViewController.modalPresentationStyle = .fullScreen

        self.present(openSourceViewController, animated: true, completion: nil)

    }

    @IBAction func sendEmailButton(_ sender: Any) {
        let composeVC = MFMailComposeViewController()
        composeVC.mailComposeDelegate = self
        composeVC.setToRecipients(["2021eating@gmail.com"])
        composeVC.setSubject("잇팅 문의 사항")
        composeVC.setMessageBody("문의 사항을 입력해주세요 :)", isHTML: false)
    composeVC.modalTransitionStyle = .coverVertical
        present(composeVC, animated: true, completion: nil)
    }
}

extension SettingViewController: MFMailComposeViewControllerDelegate {
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) { dismiss(animated: true, completion: nil) }

}

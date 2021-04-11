//
//  ProfileCell.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

class ProfileCell: UITableViewCell {
    var viewController: MyPageViewController?

    @IBOutlet var profileImageView: UIImageView!
    @IBOutlet var profileOuterView: UIView!
    @IBOutlet var infoButton: UIButton!
    @IBOutlet var toolTipImageView: UIImageView!
    @IBOutlet var tradeCountLabel: UILabel!
    @IBOutlet var nicknameLabel: UILabel!
    @IBOutlet var toolTipButton: UIButton!
    @IBOutlet var slider: UISlider?
    var timer = Timer()
    var nickname = String()
    override func awakeFromNib() {
        super.awakeFromNib()

        profileImageView?.layer.cornerRadius = 0.5 * (profileImageView?.bounds.size.width)!

    
        slider?.setThumbImage(UIImage(named: "sugarThumb"), for: .normal)
        slider?.maximumValue = 100
        slider?.minimumValue = 0
        slider?.setMaximumTrackImage(UIImage(named: "sugarPercentGreyBar"), for: .normal)
        slider?.setMinimumTrackImage(UIImage(named: "sugarPercentYellowBar")?.resizableImage(withCapInsets: UIEdgeInsets(top: 13,left: 15,bottom: 15,right: 14)), for: .normal)

        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(goToChangeProfileButton(_:)))
        profileOuterView.addGestureRecognizer(tapGesture)
    }

    override func layoutSubviews() {
            super.layoutSubviews()
        toolTipButton.center.x = CGFloat(slider!.value)
        }

    func updateUI(nickname: String, tradeCount: Int, sugarPercent: Int) {
        nicknameLabel.text = nickname
        tradeCountLabel.text = "\(tradeCount)"
        animateSlider(value: sugarPercent)
        self.nickname = nickname
        if (UserDefaults.standard.data(forKey: "profileImage") != nil) {
            profileImageView.image = UIImage(data: UserDefaults.standard.data(forKey: "profileImage")!)
        } else {
            profileImageView.image = UIImage(named: "myProfile")
        }
    }

    @IBAction func goToChangeProfileButton(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let modifyProfileViewController = storyboard.instantiateViewController(withIdentifier: "ModifyProfileViewController") as? ModifyProfileViewController else { return }
        modifyProfileViewController.nickname = nickname
        modifyProfileViewController.modalTransitionStyle = .coverVertical
        modifyProfileViewController.modalPresentationStyle = .fullScreen

        viewController?.present(modifyProfileViewController, animated: true, completion: nil)

    }
    private func animateSlider(value: Int) {
        self.setToolTipSize(value)
        self.slider?.isUserInteractionEnabled = false
    }

    private func setToolTipSize(_ value: Int) {
        let barWidth = (slider?.frame.width)!
        let toolTipFrame = (barWidth * CGFloat(value)) / 100 + 10
        toolTipButton.setTitle("\(value)%", for: .normal)
        UIView.animate(withDuration: 0.3, animations: {
            self.slider?.setValue(Float(value), animated: true)
        }) {_ in
            self.toolTipButton.center.x = toolTipFrame
        }
    }
    
    @IBAction func tapInfoButton(_ sender: Any) {
        toolTipImageView.isHidden = false
        runTimer()
    }

    private func runTimer() {
        timer.invalidate()
        timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: (#selector(updateTimer)), userInfo: nil, repeats: true)
    }

    @objc private func updateTimer() {
        timer.invalidate()
        }
}

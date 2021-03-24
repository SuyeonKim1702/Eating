//
//  FeedViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import UIKit
import SafariServices

class MyFeedViewController: UIViewController {
    @IBOutlet var deliveryButton: UIButton!
    @IBOutlet var foodImageView: UIImageView!
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var locationButton: UIButton!
    @IBOutlet var underBar: UIView?
    @IBOutlet var oneButton: UIButton!
    @IBOutlet var linkButton: UIButton!
    @IBOutlet var twoButton: UIButton!
    @IBOutlet var threeButton: UIButton!
    @IBOutlet var nicknameLabel: UILabel!
    @IBOutlet var fourButton: UIButton!
    @IBOutlet var remainingTimeLabel: UILabel!
    @IBOutlet var toggleButton: UIButton!
    @IBOutlet var distanceLabel: UILabel!
    @IBOutlet var profileImageView: UIImageView!
    @IBOutlet var contentsLabel: UILabel?
    let detailFeedService = GetDetailFeedService()
    var peopleButtons = [UIButton]()
    var timer = Timer()
    var timeInterval = Int()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupUnderBar()
        setupViews()
        setupTimer()
        peopleButtons = [oneButton, twoButton, threeButton, fourButton]

        //아마 타이머는 , handler안에서 처리를 해줘야 합니다. 왜냐면 정보를 받고난 이후에 타이머가 동작하기 떄문에
        detailFeedService.getFeedList(idx: 1) {_ in
            // 상세 정보 받아오고 처리해주는 부분
        }
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        timer.invalidate()
    }

    private func setupTimer() {
        runTimer()
    }

    private func runTimer() {
        calculateTimeInterval()
         timer = Timer.scheduledTimer(timeInterval: 1, target: self,   selector: (#selector(updateTimer)), userInfo: nil, repeats: true)
    }

    @objc private func updateTimer() {
        timeInterval -= 1
        remainingTimeLabel?.text = timeString(time: TimeInterval(timeInterval))
    }

    func calculateTimeInterval() {
        let format = DateFormatter()
        format.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        guard let endTime = format.date(from: "2021-03-25T20:17:46.384Z") else { return }

        timeInterval = Int(endTime.timeIntervalSince(Date()))
    }

    func timeString(time:TimeInterval) -> String {
        let hours = Int(time) / 3600
        let minutes = Int(time) / 60 % 60
        let seconds = Int(time) % 60
        return String(format: "%02i:%02i:%02i", hours, minutes, seconds)
    }

    private func setupViews() {
        contentsLabel?.setLinespace(spacing: 10)
    }

    private func setupUnderBar() {
        underBar?.backgroundColor = .white
        underBar?.layer.shadowRadius = 5
        underBar?.layer.shadowColor = UIColor(rgb: 0x000000, alpha: 29).cgColor
        underBar?.layer.shadowOffset = CGSize(width: 0, height: 3)
        underBar?.layer.shadowOpacity = 0.29
        underBar?.layer.shadowPath = UIBezierPath(rect: CGRect(x: 0,
                                                         y: underBar!.bounds.maxY - underBar!.layer.shadowRadius,
                                                         width: underBar!.bounds.width,
                                                         height: underBar!.layer.shadowRadius)).cgPath
        }
    
    @IBAction func tapGoBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

    @IBAction func goToChatButton(_ sender: Any) {
        openSafariViewController(url: "https://open.kakao.com/o/stiF5o4c")
    }

    private func openSafariViewController(url: String) {
        if let url = URL(string: url) {
               let config = SFSafariViewController.Configuration()
               config.entersReaderIfAvailable = true

               let vc = SFSafariViewController(url: url, configuration: config)
               present(vc, animated: true)
           }

    }

    @IBAction func tapPeopleButton(_ sender: UIButton) {
        if sender.tag == 0 && !peopleButtons[sender.tag + 1].isSelected {
            sender.isSelected = !sender.isSelected
        } else if sender.tag == 3 && peopleButtons[sender.tag - 1].isSelected {
            sender.isSelected = !sender.isSelected

        } else if (sender.tag == 1 || sender.tag == 2) && peopleButtons[sender.tag - 1].isSelected  && !peopleButtons[sender.tag + 1].isSelected {
            sender.isSelected = !sender.isSelected
        }



    }
}

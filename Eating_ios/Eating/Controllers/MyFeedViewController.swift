//
//  FeedViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/24.
//

import UIKit
import SafariServices

class MyFeedViewController: UIViewController {
    @IBOutlet var outerView: UIView!
    @IBOutlet var coverImageView: UIImageView!
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
    let changeFeedStateService = PostStateChangeService()
    let changeMemberService = PostFeedService()
    var peopleButtons = [UIButton]()
    var timer = Timer()
    var timeInterval = Int()
    var index: Int?
    var distance = Int()
    var chatLink = String()
    var feedDetail: FeedDetail?

    override func viewDidLoad() {
        super.viewDidLoad()
        peopleButtons = [oneButton, twoButton, threeButton, fourButton]
        setupViews()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        getFeedDetailApi()
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        timer.invalidate()
    }

    private func getFeedDetailApi() {
        detailFeedService.getFeedDetail(index: index!) { [weak self] result in
            switch result {
            case .success(let data):
                DispatchQueue.main.async {
                    self?.feedDetail = data
                    self?.updateUI(for: data)
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    private func updateUI(for data: FeedDetail) {
        if (UserDefaults.standard.data(forKey: "profileImage") != nil) {
            profileImageView.image = UIImage(data: UserDefaults.standard.data(forKey: "profileImage")!)
        } else {
            profileImageView.image = UIImage(named: "myProfile")
        }
        if isTimePassed(data.orderTime) {
            remainingTimeLabel.text = "00:00:00"
            remainingTimeLabel.textColor = UIColor(red: 117, green: 117, blue: 117)
        } else {
            setupTimer(for: data.orderTime)
            remainingTimeLabel.textColor = UIColor(red: 252, green: 73, blue: 20)

        }

        if distance > 1000 {
            distanceLabel?.text = "\(distance/1000)km"
        } else {
            distanceLabel?.text = "\(distance)m"
        }
        titleLabel.text = data.title
        contentsLabel?.text = data.description
        nicknameLabel?.text = data.writer
        toggleButton.isSelected = !data.finished
        deliveryButton.setTitle(Constant.DeliveryFee(rawValue: data.deliveryFeeByHost)?.text , for: .normal)
        locationButton.setTitle(Constant.MeetPlace(rawValue: data.meetPlace)?.text, for: .normal)
        chatLink = data.chatLink
        setupMemberButtons(totalMember: data.memberCountLimit, currentMember: data.currentMemberCount)
        foodImageView.kf.setImage(with: URL(string: data.categoryURL))
        coverImageView.image = UIImage(named: "coverImage")
    }

    func isTimePassed(_ time: String) -> Bool {
        let format = DateFormatter()
        format.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        guard let endTime = format.date(from: time) else { return true }
        let timeInterval = Int(endTime.timeIntervalSince(Date()))
        if timeInterval < 0 {
            return true
        } else {
            return false
        }
    }

    private func setupTimer(for time: String) {
        runTimer(for: time)
    }

    private func runTimer(for time: String) {
        calculateTimeInterval(for: time)
        timer = Timer.scheduledTimer(timeInterval: 1, target: self,   selector: (#selector(updateTimer)), userInfo: nil, repeats: true)
    }

    @objc private func updateTimer() {
        if timeInterval == 0 {
            timer.invalidate()
            remainingTimeLabel?.text = "00:00:00"
            remainingTimeLabel?.textColor = UIColor(red: 117, green: 117, blue: 117)
        } else {
            timeInterval -= 1
            remainingTimeLabel?.text = timeString(time: TimeInterval(timeInterval))
        }
    }

    func calculateTimeInterval(for time: String) {
        let format = DateFormatter()
        format.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        guard let endTime = format.date(from: time) else { return }

        timeInterval = Int(endTime.timeIntervalSince(Date()))
    }

    func timeString(time:TimeInterval) -> String {
        let hours = Int(time) / 3600
        let minutes = Int(time) / 60 % 60
        let seconds = Int(time) % 60
        if hours >= 24 {
            return "\(hours/24)day"
        }
        return String(format: "%02i:%02i:%02i", hours, minutes, seconds)
    }

    @IBAction func tapToggleButton(_ sender: Any) {
        toggleButton.isSelected = !toggleButton.isSelected
    }

    private func setupViews() {
        contentsLabel?.setLinespace(spacing: 10)
        outerView.layer.cornerRadius = Constant.completeButtonCornerRadius
        underBar?.layer.cornerRadius = Constant.completeButtonCornerRadius
        linkButton?.layer.cornerRadius = 22
        contentsLabel?.setLinespace(spacing: 12)
        profileImageView?.layer.cornerRadius = 0.5 * (profileImageView?.bounds.size.width)!
    }

    private func setupMemberButtons(totalMember: Int, currentMember: Int) {
        for i in 0..<(4-totalMember) {
            peopleButtons[3-i].setImage(UIImage(named: "dot"), for: .normal)
            peopleButtons[3-i].isUserInteractionEnabled = false
        }

        for i in 0..<currentMember {
            peopleButtons[i].isSelected = true
        }
    }
    
    @IBAction func tapGoBackButton(_ sender: Any) {
        var num = 0
        for i in 0...3 {
            var j = 3 - i
            if peopleButtons[j].isSelected {
                num = j + 1
                break
            }
        }
        changeMemberService.updateMember(postIdx: index!, num: num) { [weak self] data in
            switch data {
            case .success(let result):
                print(result)
            case .failure(let error):
                print(error.localizedDescription)
            }
            DispatchQueue.main.async {
                self?.dismiss(animated: true, completion: nil)
            }
        }
    }

    @IBAction func goToChatButton(_ sender: Any) {
        openSafariViewController(url: chatLink)
    }

    private func openSafariViewController(url: String) {
        if let url = URL(string: url) {
               let config = SFSafariViewController.Configuration()
               config.entersReaderIfAvailable = true

               let vc = SFSafariViewController(url: url, configuration: config)
               present(vc, animated: true)
           }

    }
    @IBAction func showAlertButton(_ sender: Any) {
        guard let writingAlertViewController = storyboard?.instantiateViewController(withIdentifier: "WritingUpdateViewController") as? WritingUpdateViewController else { return }
        writingAlertViewController.myFeedViewController = self
        writingAlertViewController.index = index!
        writingAlertViewController.modalTransitionStyle = .coverVertical
        writingAlertViewController.modalPresentationStyle = .overCurrentContext
        present(writingAlertViewController, animated: true, completion: nil)
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

    func dismissSelf() {
    dismiss(animated: true, completion: nil)
    }

    @IBAction func changeFinishedState(_ sender: Any) {
        changeFeedStateService.changeState(idx: index!) { result in
            switch result {
            case .success(let data):
                print(data)
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
        
    }
}

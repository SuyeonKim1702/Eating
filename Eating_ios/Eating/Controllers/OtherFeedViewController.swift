//
//  OtherFeedViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import UIKit
import SafariServices

class OtherFeedViewController: UIViewController {

    @IBOutlet var coverImageView: UIImageView!
    @IBOutlet var favoriteButton: UIButton!
    @IBOutlet var totalMemberLabel: UILabel!
    @IBOutlet var currentMemberLabel: UILabel!
    @IBOutlet var outerView: UIView!
    @IBOutlet var deliveryButton: UIButton!
    @IBOutlet var foodImageView: UIImageView!
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var locationButton: UIButton!
    @IBOutlet var underBar: UIView?
    @IBOutlet var linkButton: UIButton!
    @IBOutlet var nicknameLabel: UILabel!
    @IBOutlet var remainingTimeLabel: UILabel!
    @IBOutlet var distanceLabel: UILabel!
    @IBOutlet var profileImageView: UIImageView!
    @IBOutlet var contentsLabel: UILabel?
    @IBOutlet var sweetPercentLabel: UILabel!
    @IBOutlet var chatButton: UIButton!
    @IBOutlet var slider: UISlider!
    let detailFeedService = GetDetailFeedService()
    let favoriteService = FavoriteService()
    var timer = Timer()
    var timeInterval = Int()
    var index: Int?
    var distance = Int()
    var chatLink = String()
    var storeLink = String()
    var post = Int()
    var favorite = Bool()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupSlider()
        setupViews()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        getFeedDetailApi()
    }

    private func updateUI(for data: FeedDetail) {
        if data.finished || isTimePassed(data.orderTime) {
            remainingTimeLabel.text = "00:00:00"
            remainingTimeLabel.textColor = UIColor(red: 117, green: 117, blue: 117)
            chatButton.backgroundColor = UIColor(red: 238, green: 238, blue: 238)
        } else {
            setupTimer(for: data.orderTime)
        }
        if distance > 1000 {
            distanceLabel?.text = "\(distance/1000)km"
        } else {
            distanceLabel?.text = "\(distance)m"
        }
        titleLabel.text = data.title
        contentsLabel?.text = data.description
        nicknameLabel?.text = data.writer
        deliveryButton.setTitle(Constant.DeliveryFee(rawValue: data.deliveryFeeByHost)?.text , for: .normal)
        locationButton.setTitle(Constant.MeetPlace(rawValue: data.meetPlace)?.text, for: .normal)
        chatLink = data.chatLink
        storeLink = data.foodLink
        currentMemberLabel.text = "\(data.currentMemberCount)"
        totalMemberLabel.text = "\(data.memberCountLimit)"
        foodImageView.kf.setImage(with: URL(string: data.categoryURL))
        coverImageView.image = UIImage(named: "coverImage")
        sweetPercentLabel.text = "\(data.sugerScore)%"
        slider.value = Float(data.sugerScore)
        favoriteButton.isSelected = data.favorite
        favorite = data.favorite
    }

    private func getFeedDetailApi() {
        guard let index = index else { return }
        detailFeedService.getFeedDetail(index: index) { [weak self] result in
            switch result {
            case .success(let data):
                DispatchQueue.main.async {
                    self?.updateUI(for: data)
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
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

    @IBAction func tapFavoriteButton(_ sender: Any) {
        favoriteService.changeFavoriteState(postIdx: index!, state: !favorite) { [weak self] data in
            switch data {
            case .success(_):
                DispatchQueue.main.async {
                    self?.favorite = !self!.favorite
                    self?.favoriteButton.isSelected = !self!.favoriteButton.isSelected
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
    private func setupViews() {
        outerView.layer.cornerRadius = Constant.completeButtonCornerRadius
        chatButton?.layer.cornerRadius = 26
        linkButton?.layer.cornerRadius = 22
        contentsLabel?.setLinespace(spacing: 10)

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
            chatButton.backgroundColor = UIColor(red: 238, green: 238, blue: 238)
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
        } else {
            return String(format: "%02i:%02i:%02i", hours, minutes, seconds)
        }
    }

    private func setupSlider() {
        slider.setMaximumTrackImage(UIImage(named: "smallSliderGrey"), for: .normal)
        slider.setMinimumTrackImage(UIImage(named: "smallSliderYellow"), for: .normal)

       // slider.setMinimumTrackImage(UIImage(named: "smallSliderYellow")?.resizableImage(withCapInsets: UIEdgeInsets(top: 13,left: 13,bottom: 13,right: 16)), for: .normal)
        slider.setThumbImage(UIImage(named: "smallSliderThumb"), for: .normal)
        slider.isUserInteractionEnabled = false
    }


    @IBAction func goBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

    @IBAction func goToChatButton(_ sender: Any) {
        openSafariViewController(url: chatLink)
        favoriteService.postParticipateChat(postIdx: index!) { data in
            switch data {
            case .success(_):
                print("success")
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    @IBAction func goToStore(_ sender: Any) {
        openSafariViewController(url: storeLink)
    }

    private func openSafariViewController(url: String) {
        if let url = URL(string: url) {
               let config = SFSafariViewController.Configuration()
               config.entersReaderIfAvailable = true

               let vc = SFSafariViewController(url: url, configuration: config)
               present(vc, animated: true)
           }

    }
}

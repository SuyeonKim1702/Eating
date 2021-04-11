//
//  FeedTableViewCell.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/14.
//

import UIKit
import Kingfisher

class FeedCell: UITableViewCell {
    @IBOutlet var thumbnailImageView: UIImageView?
    @IBOutlet var titleLabel: UILabel?
    @IBOutlet var heartButton: UIButton?
    @IBOutlet var totalMemberLabel: UILabel?
    @IBOutlet var currentMemberLabel: UILabel?
    @IBOutlet var remainingTimeLabel: UILabel?
    @IBOutlet var deliveryTagLabel: UILabel?
    @IBOutlet var locationTagLabel: UILabel?
    @IBOutlet var distanceLabel: UILabel?
    @IBOutlet var coverImageView: UIImageView!
    var timer = Timer()
    var timeInterval = Int()
    var tapHeartButton: (() -> Void)? = nil

    override func awakeFromNib() {
        super.awakeFromNib()
        setupCell()
    }
    
    private func setupCell() {
        thumbnailImageView?.layer.cornerRadius = 10
        deliveryTagLabel?.clipsToBounds = true
        locationTagLabel?.clipsToBounds = true
        deliveryTagLabel?.layer.cornerRadius = 10
        locationTagLabel?.layer.cornerRadius = 10
    }

    func updateUI(_ feed: Feed) {
        if feed.mine {
            heartButton?.isHidden = true
        } else {
            heartButton?.isHidden = false
        }
        titleLabel?.text = feed.title
        totalMemberLabel?.text = "\(feed.memberCountLimit)"
        currentMemberLabel?.text = "\(feed.memberCount)"
        if feed.distance > 1000 {
            distanceLabel?.text = "\(feed.distance/1000)km"
        } else {
            distanceLabel?.text = "\(feed.distance)m"
        }
        thumbnailImageView?.image = Constant.categoryImage[feed.categoryIdx]
        deliveryTagLabel?.text = Constant.DeliveryFee(rawValue: feed.deliveryFeeByHost)?.text
        locationTagLabel?.text = Constant.MeetPlace(rawValue: feed.meetPlace)?.text


        if feed.favorite {
            heartButton?.isSelected = true
        } else {
            heartButton?.isSelected = false
        }


        if feed.finished || isTimePassed(feed.orderTime) { //완료된 게시물
            remainingTimeLabel?.text = "00:00"
            remainingTimeLabel?.textColor = UIColor(red: 117, green: 117, blue: 117)
            coverImageView.isHidden = false

        } else { //진행 중인 게시물
            setupTimer(feed.orderTime)
            coverImageView.isHidden = true


        }
    }

    private func setupTimer(_ time: String) {
        runTimer(time)
    }

    private func runTimer(_ time: String) {
        calculateTimeInterval(time)
        timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: (#selector(updateTimer)), userInfo: nil, repeats: true)
    }

    @objc private func updateTimer() {
        if timeInterval == 0 {
            timer.invalidate()
            remainingTimeLabel?.text = "00:00"
            remainingTimeLabel?.textColor = UIColor(red: 117, green: 117, blue: 117)
            coverImageView.isHidden = false
        } else {
            remainingTimeLabel?.textColor = UIColor(red: 252, green: 73, blue: 20)
            timeInterval -= 1
            remainingTimeLabel?.text = timeString(time: TimeInterval(timeInterval))
        }

    }

    func isTimePassed(_ time: String) -> Bool {
        let format = DateFormatter()
        format.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.sss"
        guard let endTime = format.date(from: time) else { return true }
        let timeInterval = Int(endTime.timeIntervalSince(Date()))
        if timeInterval < 0 {
            return true
        } else {
            return false
        }
    }

    func calculateTimeInterval(_ time: String) {
        let format = DateFormatter()
        format.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.sss"
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

    override func prepareForReuse() {
        super.prepareForReuse()
        timer.invalidate()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    @IBAction func tapHeartButton(_ sender: Any) {
        tapHeartButton?()
    }

}

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
    var timer = Timer()
    var timeInterval = Int()

    override func awakeFromNib() {
        super.awakeFromNib()
        setupCell()
    }
    
    private func setupCell() {
        thumbnailImageView?.layer.cornerRadius = 10
    }

    func updateUI(_ feed: Feed) {
        setupTimer()

        titleLabel?.text = feed.title
        totalMemberLabel?.text = "\(feed.memberCountLimit)명"
        currentMemberLabel?.text = "\(feed.memberCount)명"
        distanceLabel?.text = "\(feed.distance)m"

        if feed.isFavorite {
            heartButton?.isSelected = true
        } else {
            heartButton?.isSelected = false
        }

        deliveryTagLabel?.text = Constant.DeliveryFee(rawValue: feed.deliveryFeeByHost)?.text
        locationTagLabel?.text = Constant.MeetPlace(rawValue: feed.meetPlace)?.text

        thumbnailImageView?.kf.setImage(with: URL(string: feed.categoryImage)!)

    }

    private func setupTimer() {
        runTimer()
    }

    private func runTimer() {
        calculateTimeInterval()
         timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: (#selector(updateTimer)), userInfo: nil, repeats: true)
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

    override func prepareForReuse() {
        super.prepareForReuse()
        timer.invalidate()
    }



    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
}

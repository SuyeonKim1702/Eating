//
//  FeedTableViewCell.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/14.
//

import UIKit

class FeedCell: UITableViewCell {
    @IBOutlet var thumbnailImageView: UIImageView?
    @IBOutlet var titleLabel: UILabel?
    @IBOutlet var heartImageView: UIImageView?
    @IBOutlet var totalMemberLabel: UILabel?
    @IBOutlet var currentMemberLabel: UILabel?
    @IBOutlet var remainingTimeLabel: UILabel?
    @IBOutlet var deleveryTagLabel: UILabel?
    @IBOutlet var locationTagLabel: UILabel?
    @IBOutlet var distanceLabel: UILabel?
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCell()
    }
    
    private func setupCell() {
        thumbnailImageView?.layer.cornerRadius = 10
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}

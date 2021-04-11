//
//  ReviewCell.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

class ReviewCell: UITableViewCell {

    @IBOutlet var reviewContentsLabel: UILabel!
    @IBOutlet var dateLabel: UILabel!
    @IBOutlet var nicknameLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        reviewContentsLabel?.setLinespace(spacing: 10)
    }

    func updateUI(review: Review) {
        reviewContentsLabel.text = review.review
        nicknameLabel.text = review.senderNickname
        dateLabel.text = review.writeDate
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}

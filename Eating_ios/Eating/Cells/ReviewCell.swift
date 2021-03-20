//
//  ReviewCell.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

class ReviewCell: UITableViewCell {

    @IBOutlet var contentLabel: UILabel?
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        contentLabel?.setLinespace(spacing: 10)
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}

//
//  CardCell.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

class CardCell: UITableViewCell {
    @IBOutlet var commentButton: UIButton!
    @IBOutlet var numOfCards: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    func updateUI(num: Int, comment: String) {
        numOfCards.text = "\(num)"
        commentButton.setTitle(comment, for: .normal)
    }
    
}

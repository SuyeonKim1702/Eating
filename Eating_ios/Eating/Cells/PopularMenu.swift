//
//  PopularMenu.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/21.
//

import UIKit

class PopularMenu: UITableViewCell {

    @IBOutlet var menuLabel: UILabel!
    @IBOutlet var rankingLabel: UILabel!
    @IBOutlet var starLabel: UIImageView!
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    func updateUI(rank: Int, menu: String) {
        if rank > 3 {
            starLabel.isHidden = true
        } else {
            starLabel.isHidden = false
        }
        menuLabel.text = menu
        rankingLabel.text = "\(rank)"
    }
    
}

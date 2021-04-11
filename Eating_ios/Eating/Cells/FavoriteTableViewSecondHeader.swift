//
//  FavoriteTableViewSecondHeader.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

protocol FavoriteToolTipCellDelegate: AnyObject {
    func favoriteToolTipCellToggleSection()
}

class FavoriteTableViewSecondHeader: UITableViewHeaderFooterView {
    weak var delegate: FavoriteToolTipCellDelegate?

    override func awakeFromNib() {
        super.awakeFromNib()
        backgroundColor = .green
    }

    @IBAction func expandToolTip(_ sender: Any) {
        delegate?.favoriteToolTipCellToggleSection()
    }
}

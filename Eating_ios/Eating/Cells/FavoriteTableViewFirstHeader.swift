//
//  FavoriteTableViewFirstHeader.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

protocol ChattingToolTipCellDelegate: AnyObject {
    func chattingToolTipCellToggleSection()
}

class FavoriteTableViewFirstHeader: UITableViewHeaderFooterView {
    weak var delegate: ChattingToolTipCellDelegate?

    override func awakeFromNib() {
        super.awakeFromNib()
    }

    @IBAction func expandToolTip(_ sender: Any) {
        delegate?.chattingToolTipCellToggleSection()
    }
}

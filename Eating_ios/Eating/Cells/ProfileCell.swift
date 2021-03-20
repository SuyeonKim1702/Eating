//
//  ProfileCell.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/19.
//

import UIKit

class ProfileCell: UITableViewCell {

    @IBOutlet var slider: UISlider?
    override func awakeFromNib() {
        super.awakeFromNib()
        slider?.setThumbImage(UIImage(named: "sugarThumb"), for: .normal)
        slider?.maximumValue = 100
        slider?.minimumValue = 0
        slider?.setMaximumTrackImage(UIImage(named: "sugarPercentGreyBar"), for: .normal)
        slider?.setMinimumTrackImage(UIImage(named: "sugarPercentYellowBar"), for: .normal)

        UIView.animate(withDuration: 2, animations: {
            self.slider?.setValue(40, animated: true)
            self.slider?.isUserInteractionEnabled = false
        })
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
    }
    
}

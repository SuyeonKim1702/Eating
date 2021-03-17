//
//  AddressCell.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import UIKit

class AddressCell: UITableViewCell {
    
    @IBOutlet var roadLabel: UILabel?
    @IBOutlet var addressNameLabel: UILabel?
    @IBOutlet var placeNameLabel: UILabel?
    @IBOutlet var roadAddressNameLabel: UILabel?
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func updateUI(for addressInfo: Place) {
        addressNameLabel?.text = addressInfo.addressName
        placeNameLabel?.text = addressInfo.placeName
   
        if addressInfo.roadAddressName == "" {
            roadLabel?.text = ""
        } else {
            roadLabel?.text = "(도로명)"
            roadAddressNameLabel?.text = addressInfo.roadAddressName
        }
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        roadLabel?.text = ""
        addressNameLabel?.text = ""
        placeNameLabel?.text = ""
        roadAddressNameLabel?.text = ""
    }
}

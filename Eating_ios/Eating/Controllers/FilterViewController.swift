//
//  FilterViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/18.
//

import UIKit

class FilterViewController: UIViewController {

    @IBOutlet var slider: UISlider?
    var sliderToolTip: UIImageView?
    override func viewDidLoad() {
        super.viewDidLoad()
        setupSlider()
        setupGestureRecognizer()
        sliderToolTip = UIImageView(image: UIImage(named:"sliderToolTip"))
        view.addSubview(sliderToolTip!)
        //sliderToolTip?.bottomAnchor.constraint(equalTo: slider!.topAnchor, constant: 30).isActive = true



    }

    private func setupGestureRecognizer() {
        slider?.addTarget(self, action: #selector(sliderValueChanged(_:)), for: UIControl.Event.valueChanged)
    }

    @objc private func sliderValueChanged(_ sender: UISlider) {
        print(sender.value*1000)
    }

    private func setupSlider() {
        slider?.setThumbImage(UIImage(named: "sliderThumbImage"), for: .normal)
        slider?.setMaximumTrackImage(UIImage(named: "minTrackImage"), for: .normal)
        slider?.setMinimumTrackImage(UIImage(named: "maxTrackImage"), for: .normal)

    }


}

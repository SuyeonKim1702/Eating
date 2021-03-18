//
//  FilterViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/18.
//

import UIKit

class FilterViewController: UIViewController {

    @IBOutlet var toolTip: UIButton?
    private var observer: NSKeyValueObservation?
    @IBOutlet var slider: UISlider?
    var distance = UILabel()
    var sliderToolTip: UIImageView?
    var count = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        setupSlider()
        setupGestureRecognizer()
        sliderToolTip = UIImageView()
        sliderToolTip?.center.x = slider?.center.x ?? 0
        toolTip?.center.x = slider?.center.x ?? 0
        view.addSubview(sliderToolTip!)

    }



    private func setupGestureRecognizer() {
        slider?.addTarget(self, action: #selector(sliderValueChanged(_:)), for: UIControl.Event.valueChanged)
    }



    @objc private func sliderValueChanged(_ sender: UISlider) {

        self.toolTip?.center.x = CGFloat(sender.value*0.9 + 25)
        if count%1 == 0 {
            let value = Int((sender.value/319)*1000)
            if value > 1000 {
                toolTip?.setTitle("1km", for: .normal)
            } else if value < 90 {
                toolTip?.setTitle("0m", for: .normal)
            } else {
                toolTip?.setTitle("\(value)m", for: .normal)
            }
        }
        count+=1

    }

    private func setupSlider() {
        slider?.setThumbImage(UIImage(named: "sliderThumbImage"), for: .normal)
        slider?.setMaximumTrackImage(UIImage(named: "minTrackImage"), for: .normal)
        slider?.setMinimumTrackImage(UIImage(named: "maxTrackImage"), for: .normal)

        //slider?.minimumValue = 28
        //slider?.maximumValue = 347
        slider?.value = 200
        distance.text = "400m"
        view?.addSubview(distance)
    }
}

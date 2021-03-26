//
//  FilterViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/18.
//

import UIKit

class FilterViewController: UIViewController {

    @IBOutlet var secondButtonStackView: UIStackView?
    @IBOutlet var firstButtonStackView: UIStackView?
    @IBOutlet var toolTip: UIButton?
    private var observer: NSKeyValueObservation?
    @IBOutlet var slider: UISlider?
    var distance = UILabel()
    var sliderToolTip: UIImageView?
    var count = 0
    var buttonsArray = [UIButton]()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupSlider()
        setupGestureRecognizer()
        setupButtons()
        sliderToolTip = UIImageView()
        sliderToolTip?.center.x = slider?.center.x ?? 0
        toolTip?.center.x = slider?.center.x ?? 0
        view.addSubview(sliderToolTip!)
    }

    private func setupButtons() {
        let firstButtonArray = firstButtonStackView?.subviews.map{ $0 as! UIButton }
        let secondButtonArray = secondButtonStackView?.subviews.map{ $0 as! UIButton }

        buttonsArray = firstButtonArray! + secondButtonArray!
        for button in buttonsArray {
            button.addTarget(self, action: #selector(tapButton(_:)), for: .touchUpInside)
        }
    }

    @objc private func tapButton(_ sender: UIButton) {
        sender.isSelected = !sender.isSelected
    }

    private func setupGestureRecognizer() {
        slider?.addTarget(self, action: #selector(sliderValueChanged(_:)), for: UIControl.Event.valueChanged)
    }

    @IBAction func goBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
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
        count += 1
    }

    private func setupSlider() {
        slider?.setThumbImage(UIImage(named: "sliderThumbImage"), for: .normal)
        slider?.setMaximumTrackImage(UIImage(named: "minTrackImage"), for: .normal)
        slider?.setMinimumTrackImage(UIImage(named: "maxTrackImage"), for: .normal)
        slider?.value = 200
        distance.text = "\(Int((slider!.value/319)*1000))"
        view?.addSubview(distance)
    }

    @IBAction func tapCompleteButton(_ sender: Any) {
        var index = 0
        var menuString = ""
        let distanceString = toolTip?.titleLabel?.text
        for button in buttonsArray {
            if button.isSelected {
                menuString += "\(Constant.getMenuCode(raw: index))-"
            }
            index += 1
        }
        //api에 같이 보내야 할 값
        menuString = String(menuString.dropLast())
        print(menuString)
        print(distanceString)
        dismiss(animated: true, completion: nil)
    }

}

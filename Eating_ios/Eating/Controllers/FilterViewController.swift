//
//  FilterViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/18.
//

import UIKit

class FilterViewController: UIViewController {

    @IBOutlet var completeButton: UIButton!
    @IBOutlet var secondButtonStackView: UIStackView?
    @IBOutlet var firstButtonStackView: UIStackView?
    @IBOutlet var toolTip: UIButton?
    private var observer: NSKeyValueObservation?
    @IBOutlet var slider: UISlider?
    var distance = UILabel()
    var sliderToolTip: UIImageView?
    var count = 0
    var toolTipText: Int = 500
    var sliderValue: Float = 200.0
    @IBOutlet var outerView: UIView!
    var buttonsArray = [UIButton]()
    weak var homeViewController: HomeViewController?
    var check = false
    var selectedButton = [Int]()

    override func viewDidLoad() {
        super.viewDidLoad()
        completeButton?.layer.cornerRadius = Constant.completeButtonCornerRadius
        outerView?.layer.cornerRadius = Constant.buttonConnerRadius
        setupSlider()
        setupGestureRecognizer()
        setupButtons()
        sliderToolTip = UIImageView()
        sliderToolTip?.center.x = slider?.center.x ?? 0
        toolTip?.center.x = CGFloat(sliderValue*0.9 + 25)

        if toolTipText == 1000 {
            toolTip?.setTitle("1km", for: .normal)
        } else {
            toolTip?.setTitle("\(toolTipText)M", for: .normal)
        }
        view.addSubview(sliderToolTip!)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        UIView.animate(withDuration: 0, animations: {
        }) {_ in
            self.toolTip?.center.x = CGFloat(self.sliderValue*0.9 + 25)
        }

        for button in selectedButton {
            buttonsArray[button].isSelected = true
        }
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
        slider?.value = sliderValue
        distance.text = "\(Int((slider!.value/319)*1000))"
        view?.addSubview(distance)
    }

    @IBAction func tapCompleteButton(_ sender: Any) {
        var index = 0
        var menuString = ""
        let distanceString = toolTip!.titleLabel!.text
        selectedButton = []
        for button in buttonsArray {
            if button.isSelected {
                menuString += "\(Constant.getMenuCode(raw: index))-"
                selectedButton.append(index)
            }
            index += 1
        }
        //api에 같이 보내야 할 값
        menuString = String(menuString.dropLast())
        homeViewController?.category = menuString
        if distanceString == "1km" {
            homeViewController?.distance = 1000
        } else {
            homeViewController?.distance = Int((distanceString?.dropLast())!)!
        }
        homeViewController?.sliderValue = slider!.value
        homeViewController?.selectedButtonMenu = selectedButton

        dismiss(animated: true, completion: nil)
    }

}

//
//  FoodCategoryViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/20.
//

import UIKit

class FoodCategoryViewController: UIViewController {
    @IBOutlet var firstButtonStackView: UIStackView?
    @IBOutlet var secondButtonStackView: UIStackView?
    var buttonsArray = [UIButton]()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupButtons()
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
        for button in buttonsArray {
            button.isSelected = false
        }
        sender.isSelected = !sender.isSelected
    }

    private func getSelectedButton() -> String {
        var index = 0
        var menuString = ""

        for button in buttonsArray {
            if button.isSelected {
                menuString += "\(Constant.getMenuCode(raw: index))-"
            }
            index += 1
        }
        menuString = String(menuString.dropLast())
        return menuString
    }

    @IBAction func tapCompleteButton(_ sender: Any) {
        (parent as? WritingViewController)?.menu = getSelectedButton()

        UIView.animate(withDuration: 1,
                       delay: 0,
                       usingSpringWithDamping: 2.0,
                       initialSpringVelocity: 3,
                       options: .curveEaseInOut,
                       animations: {
                        self.setTabViewFrame(metrics:ViewFrame(originY: UIScreen.main.bounds.height,
                                                               height: 0),
                                             view: self.view)
                       }, completion: { [weak self]  _ in
                        self?.view.removeFromSuperview()
                        self?.removeFromParent()
                       })
        (parent as? WritingViewController)?.visualEffect.isHidden = true
    }

    private func setTabViewFrame (metrics: ViewFrame?, view: UIView) {
        if let unwrappedMetrics = metrics {
            view.frame = CGRect(x: 0,
                                y: unwrappedMetrics.originY,
                                width: view.frame.width,
                                height: unwrappedMetrics.height)
        }
    }

}

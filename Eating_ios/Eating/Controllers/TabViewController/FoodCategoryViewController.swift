//
//  FoodCategoryViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/20.
//

import UIKit

class FoodCategoryViewController: UIViewController {


    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func tapCompleteButton(_ sender: Any) {
        let parentViewController = parent as? WritingViewController

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
        parentViewController?.visualEffect.isHidden = true
        // TODO 선택한 값 넘겨주기 -> 버튼 뭐 선택했는지 넘겨줄 것
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

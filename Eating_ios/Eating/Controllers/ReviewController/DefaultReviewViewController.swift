//
//  DefaultReviewViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/16.
//

import UIKit

class DefaultReviewViewController: UIViewController {
    private var firstViewControllerFrame = ViewFrame(originY: UIScreen.main.bounds.height * 0.15, height: UIScreen.main.bounds.height * 0.85 )
    private var zeroFrame = ViewFrame(originY: UIScreen.main.bounds.height, height: 0)
    var secondView: UIView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let firstReviewViewController = storyboard.instantiateViewController(withIdentifier: "FirstReviewViewController") as? FirstReviewViewController else { return }
        guard let secondReviewViewController = storyboard.instantiateViewController(withIdentifier: "SecondReviewViewController") as? SecondReviewViewController else { return }
        let firstView = firstReviewViewController.view
        secondView = secondReviewViewController.view
        
        addChild(firstReviewViewController)
        addChild(secondReviewViewController)
        view.addSubview(firstView ?? UIView())
        view.addSubview(secondView ?? UIView())
        setTabViewFrame(metrics: firstViewControllerFrame, view: firstView ?? UIView())
        setTabViewFrame(metrics: zeroFrame, view: secondView ?? UIView())
    }
    
     func animateViewController(withDuration: Double, velocity: CGFloat, metrics: ViewFrame?, view: UIView) {
        UIView.animate(withDuration: withDuration,
                       delay: 0,
                       usingSpringWithDamping: 2.0,
                       initialSpringVelocity: velocity,
                       options: .curveEaseInOut,
                       animations: {
                        self.setTabViewFrame(metrics: metrics, view: view)
                       }, completion: nil)
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

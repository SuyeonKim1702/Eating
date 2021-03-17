//
//  FirstReviewViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/16.
//

import UIKit

class FirstReviewViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
     @IBAction func goToNextButton(_ sender: Any) {
        weak var parentViewController = parent as? DefaultReviewViewController
        parentViewController?.animateViewController(withDuration: 1, velocity: 2, metrics: ViewFrame(originY: UIScreen.main.bounds.height, height: 0), view: view)
        parentViewController?.animateViewController(withDuration: 1, velocity: 2, metrics: ViewFrame(originY: UIScreen.main.bounds.height*0.3, height: UIScreen.main.bounds.height*0.7), view: parentViewController?.secondView ?? UIView())
        
        
     }
    
   
}

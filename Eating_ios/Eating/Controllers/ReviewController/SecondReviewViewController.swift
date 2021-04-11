//
//  SecondReviewViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/17.
//

import UIKit

class SecondReviewViewController: UIViewController {
    @IBOutlet var nicknameLabel: UILabel!
    @IBOutlet var goodButton: UIButton?
    @IBOutlet var sosoButton: UIButton?
    @IBOutlet var badButton: UIButton?
    @IBOutlet var timeButton: UIButton?
    @IBOutlet var replyButton: UIButton!
    @IBOutlet var divideButton: UIButton!
    @IBOutlet var kindButton: UIButton!
    @IBOutlet var completeButton: UIButton!
    @IBOutlet var badLabel: UILabel!
    @IBOutlet var sosoLabel: UILabel!
    @IBOutlet var bestLabel: UILabel!
    var buttons = [UIButton?]()
    var labels = [UILabel?]()

    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()
        buttons = [badButton, sosoButton, goodButton]
        labels = [badLabel, sosoLabel, bestLabel]


    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        nicknameLabel.text = "\((parent as! DefaultReviewViewController).nickname!)님과의 잇팅은 어떠셨나요?"

    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
    }

    private func stylingViews() {
        completeButton.layer.cornerRadius = Constant.completeButtonCornerRadius
    }

    @IBAction func tapButton(_ sender: UIButton){
        for button in buttons {
            button?.isSelected = false
            labels[button!.tag]?.isHighlighted = false
        }

        sender.isSelected = !sender.isSelected
        labels[sender.tag]?.isHighlighted = !labels[sender.tag]!.isHighlighted
    }

    @IBAction func buttonClicked(_ sender: UIButton) {
        sender.isSelected = !sender.isSelected

    }

    @IBAction func tapDetailButton(_ sender: UIButton) {
        sender.isSelected = !sender.isSelected
    }


    @IBAction func goToNextPage(_ sender: Any) {

        var tag: Int?
        for button in buttons {
            if button?.isSelected == true {
                tag = button!.tag
                break
            }
        }
        if let tag = tag {
            weak var parentViewController = parent as? DefaultReviewViewController
            parentViewController?.animateViewController(withDuration: 1, velocity: 2, metrics: ViewFrame(originY: UIScreen.main.bounds.height, height: 0), view: view)
            parentViewController?.animateViewController(withDuration: 1, velocity: 2, metrics: ViewFrame(originY: UIScreen.main.bounds.height*0.15, height: UIScreen.main.bounds.height*0.85), view: parentViewController?.thirdView ?? UIView())
            (parent as? DefaultReviewViewController)?.floatingView.isHidden = true
            weak var thirdViewController = (parent as? DefaultReviewViewController)?.children[safe: 2] as? ThirdReviewViewController

            thirdViewController?.selectedButton = tag
            thirdViewController?.time = timeButton!.isSelected
            thirdViewController?.kindness = kindButton!.isSelected
            thirdViewController?.division = divideButton!.isSelected
            thirdViewController?.reply = replyButton!.isSelected
            thirdViewController?.checkButton.isHidden = false
        } else {
            guard let alertViewController = storyboard?.instantiateViewController(withIdentifier: "FillAlertViewController") as? FillAlertViewController else { return }
            alertViewController.modalPresentationStyle = .overCurrentContext
            present(alertViewController, animated: true, completion: nil)
        }


    }
}

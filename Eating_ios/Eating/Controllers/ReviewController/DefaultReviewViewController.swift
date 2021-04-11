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
    let getFeedService = GetDetailFeedService()
    var secondView: UIView?
    var thirdView: UIView?
    var nickname: String?
    var userId: String?
    var postIdx: Int?
    @IBOutlet var distanceLabel: UILabel!
    @IBOutlet var feedTitleLabel: UILabel!
    @IBOutlet var thumbnailImageView: UIImageView!
    @IBOutlet var floatingView: UIView!
    @IBOutlet var test: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        setupChildren()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        callFeedApi()
        // 1. 가져온 postid를 토대로, 포스트 정보가져오기
        // 2. view Update 하고
        // 3.  first에 정보 보내고 first도 view 업데이트
    }

    private func callFeedApi() {
        if let postIdx = postIdx {
            getFeedService.getFeedDetail(index: postIdx) { [weak self] result in
                switch result {
                case .success(let data):
                    DispatchQueue.main.async {
                        self?.updateView(data: data)
                        (self?.children[0] as? FirstReviewViewController)?.updateUI(data: data)
                    }

                case .failure(let error):
                    print(error.localizedDescription)
                }
            }
        }
    }

    private func updateView(data: FeedDetail) {
        feedTitleLabel.text = data.title
        thumbnailImageView.image = Constant.categoryImage[data.category]
    }

    private func setupChildren() {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let firstReviewViewController = storyboard.instantiateViewController(withIdentifier: "FirstReviewViewController") as? FirstReviewViewController else { return }
        guard let secondReviewViewController = storyboard.instantiateViewController(withIdentifier: "SecondReviewViewController") as? SecondReviewViewController else { return }
        guard let thirdReviewViewController = storyboard.instantiateViewController(withIdentifier: "ThirdReviewViewController") as? ThirdReviewViewController else { return }
        let firstView = firstReviewViewController.view
        secondView = secondReviewViewController.view
        thirdView = thirdReviewViewController.view
        firstView?.layer.cornerRadius = 24
        secondView?.layer.cornerRadius = 24
        thirdView?.layer.cornerRadius = 24

        addChild(firstReviewViewController)
        addChild(secondReviewViewController)
        addChild(thirdReviewViewController)
        view.addSubview(firstView ?? UIView())
        view.addSubview(secondView ?? UIView())
        view.addSubview(thirdView ?? UIView())
        setTabViewFrame(metrics: firstViewControllerFrame, view: firstView ?? UIView())
        setTabViewFrame(metrics: zeroFrame, view: secondView ?? UIView())
        setTabViewFrame(metrics: zeroFrame, view: thirdView ?? UIView())

    }

    @IBAction func goBackButton(_ sender: Any) {
        guard let alertViewController = storyboard?.instantiateViewController(withIdentifier: "WritingAlertViewController") as? WritingAlertViewController else { return }
        alertViewController.defaultReviewViewController = self
        alertViewController.modalPresentationStyle = .overCurrentContext
        present(alertViewController, animated: true, completion: nil)
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

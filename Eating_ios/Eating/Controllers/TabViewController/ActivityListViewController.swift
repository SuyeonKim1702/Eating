//
//  ActivityListViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/20.
//

import UIKit

class ActivityListViewController: UIViewController {
    @IBOutlet var segmentedControl: UISegmentedControl?
    @IBOutlet var activityTableView: UITableView?
    override func viewDidLoad() {
        super.viewDidLoad()
        setupSegmentedControl()
        setupTableView()
    }

    func setupTableView() {
        let feedNib = UINib(nibName: "FeedCell", bundle: nil)
        activityTableView?.register(feedNib, forCellReuseIdentifier: "FeedCell")
        activityTableView?.dataSource = self
    }

    func setupSegmentedControl() {
        if #available(iOS 13.0, *) {
          let image = UIImage()
            let size = CGSize(width: 1, height: segmentedControl?.intrinsicContentSize.height ?? 0)
          UIGraphicsBeginImageContextWithOptions(size, false, 0.0)
          image.draw(in: CGRect(origin: .zero, size: size))
          let scaledImage = UIGraphicsGetImageFromCurrentImageContext()
          UIGraphicsEndImageContext()
            segmentedControl?.setBackgroundImage(scaledImage, for: .normal, barMetrics: .default)
          segmentedControl?.setDividerImage(scaledImage, forLeftSegmentState: .normal, rightSegmentState: .normal, barMetrics: .default)
        }
        segmentedControl?.setTitleTextAttributes([NSAttributedString.Key.font : UIFont(name: "NanumSquareRoundOTFEB", size: 16)!, NSAttributedString.Key.foregroundColor: UIColor(red: 66, green: 66, blue: 66)], for: .selected)

        segmentedControl?.setTitleTextAttributes([NSAttributedString.Key.font : UIFont(name: "NanumSquareRoundOTFEB", size: 16)!, NSAttributedString.Key.foregroundColor: UIColor(red: 117, green: 117, blue: 117)], for: .normal)

        segmentedControl?.addUnderlineForSelectedSegment()
        segmentedControl?.addTarget(self, action: #selector(segmentValueChanged(_:)), for: .valueChanged)
    }

    @objc func segmentValueChanged(_ sender: UISegmentedControl) {
        sender.changeUnderlinePosition()

    }
}

extension ActivityListViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        10
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let feedCell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as? FeedCell else { return UITableViewCell() }

        return feedCell
        
    }




}


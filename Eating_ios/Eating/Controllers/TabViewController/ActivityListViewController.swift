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
    let feedListService = GetFeedService()
    var category = "0-1-2-3-4-5-6-7-8-9"
    var distance = 500
    var currentPage = 1
    var check = false
    var feed = [Feed]()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupSegmentedControl()
        setupTableView()
        callMyEatingListApi(page: 0)
    }

    func setupTableView() {
        let feedNib = UINib(nibName: "FeedCell", bundle: nil)
        activityTableView?.register(feedNib, forCellReuseIdentifier: "FeedCell")
        activityTableView?.dataSource = self
        activityTableView?.delegate = self
    }
    @IBAction func tapGoBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
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

        segmentedControl?.addUnderlineForSelectedSegment()
        segmentedControl?.addTarget(self, action: #selector(segmentValueChanged(_:)), for: .valueChanged)
    }

    @objc func segmentValueChanged(_ sender: UISegmentedControl) {
        sender.changeUnderlinePosition()

        switch sender.selectedSegmentIndex {
        case 0:
            sender.setImage(UIImage(named: "openListSelected"), forSegmentAt: 0)
            sender.setImage(UIImage(named: "participatedListUnselected"), forSegmentAt: 1)
            callMyEatingListApi(page: 0)
        case 1:
            sender.setImage(UIImage(named: "openListUnselected"), forSegmentAt: 0)
            sender.setImage(UIImage(named: "participatedListSelected"), forSegmentAt: 1)
            callParticipatedFeedListApi(page: 0)

        default:
            break
        }

    }

    private func callParticipatedFeedListApi(page: Int) {
        feedListService.getFeedList(apiType: .participated, page: page, category: category, distance: distance, mine: 0) { [weak self] data in
            switch data {
            case .success(let data):
                DispatchQueue.main.async {
                    self?.feed = data
                    self?.activityTableView?.reloadData()
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    private func callMyEatingListApi(page: Int) {
        feedListService.getFeedList(apiType: .postList, page: page, mine: 1) { [weak self] data in
            switch data {
            case .success(let data):
                DispatchQueue.main.async {
                    self?.feed = data
                    self?.activityTableView?.reloadData()
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
}

extension ActivityListViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if feed.count == 0 {
            tableView.setEmptyView()
        } else {
            tableView.restore()
        }
        return feed.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let feedCell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as? FeedCell else { return UITableViewCell() }

        guard let feed = feed[safe: indexPath.row] else { return feedCell }
        feedCell.updateUI(feed)

        return feedCell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let myFeedViewController = storyboard.instantiateViewController(withIdentifier: "MyFeedViewController") as? MyFeedViewController else { return }
        myFeedViewController.index = feed[safe: indexPath.row]?.postId
        myFeedViewController.distance = feed[safe: indexPath.row]!.distance

        guard let otherFeedViewController = storyboard.instantiateViewController(withIdentifier: "OtherFeedViewController") as? OtherFeedViewController else { return }
        otherFeedViewController.index = feed[safe: indexPath.row]?.postId
        otherFeedViewController.distance = feed[safe: indexPath.row]!.distance

        let selectedViewController : UIViewController
        if feed[safe: indexPath.row]?.mine == true {
            selectedViewController = myFeedViewController
        } else {
            selectedViewController = otherFeedViewController
        }
        selectedViewController.modalTransitionStyle = .coverVertical
        selectedViewController.modalPresentationStyle = .fullScreen
        present(selectedViewController, animated: true, completion: nil)
    }
}


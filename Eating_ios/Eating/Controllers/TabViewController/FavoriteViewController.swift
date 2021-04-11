//
//  FavoriteViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import UIKit

class FavoriteViewController: UIViewController {

    @IBOutlet var favoriteTableView: UITableView?
    let feedListService = GetFeedService()
    var category = "0-1-2-3-4-5-6-7-8-9"
    var distance = 500
    var currentPage = 1
    var check = false
    var feed = [Feed]()
    var participatingFeed = [Feed]()
    var chatClicked = false
    var favoriteClicked = false
    var firstTimer = Timer()
    var secondTimer = Timer()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        callFavoriteFeedListApi()
        favoriteTableView?.backgroundView = UIImageView(image: UIImage(named: "taylor-swift"))
        favoriteTableView?.contentInset = UIEdgeInsets(top: -35, left: 0, bottom: 0, right: 0)
    }

    private func callFavoriteFeedListApi() {
        feedListService.getFeedList(apiType: .favoriteList, page: 0, category: category, distance: distance, mine: 0) { [weak self] data in
            switch data {
            case .success(let data):
                self?.feed = data
                self?.callParticipatingFeedListApi()
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    private func callParticipatingFeedListApi() {
        feedListService.getFeedList(apiType: .participating, page: 0, category: category, distance: distance, mine: 0) { [weak self] data in
            switch data {
            case .success(let data):
                DispatchQueue.main.async {
                    self?.participatingFeed = data
                    self?.favoriteTableView?.reloadData()
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    private func setupTableView(){
        let feedNib = UINib(nibName: "FeedCell", bundle: nil)
        favoriteTableView?.register(feedNib, forCellReuseIdentifier: "FeedCell")
        favoriteTableView?.dataSource = self
        initializeNibs()
        favoriteTableView?.dataSource = self
        favoriteTableView?.delegate = self
    }

    private func initializeNibs() {
        let firstSectionHeaderNib = UINib(nibName: "FavoriteTableViewFirstHeader", bundle: nil)
        favoriteTableView?.register(firstSectionHeaderNib, forHeaderFooterViewReuseIdentifier: "FavoriteTableViewFirstHeader")

        let secondSectionHeaderNib = UINib(nibName: "FavoriteTableViewSecondHeader", bundle: nil)
        favoriteTableView?.register(secondSectionHeaderNib, forHeaderFooterViewReuseIdentifier: "FavoriteTableViewSecondHeader")

        let chatTooltipNib = UINib(nibName: "ChattingToolTipCell", bundle: nil)
        let favoriteTooltipNib = UINib(nibName: "FavoriteToolTipCell", bundle: nil)
        favoriteTableView?.register(chatTooltipNib, forCellReuseIdentifier: "ChattingToolTipCell")
        favoriteTableView?.register(favoriteTooltipNib, forCellReuseIdentifier: "FavoriteToolTipCell")
    }

    private func runFirstTimer() {
        firstTimer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: (#selector(updateFirstTimer)), userInfo: nil, repeats: true)
    }

    @objc private func updateFirstTimer() {
        firstTimer.invalidate()
        chatClicked = false
        UITableViewCell.setAnimationsEnabled(true)
        favoriteTableView?.reloadSections([0], with: .fade)
        }

    private func runSecondTimer() {
        secondTimer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: (#selector(updateSecondTimer)), userInfo: nil, repeats: true)
    }

    @objc private func updateSecondTimer() {
        secondTimer.invalidate()
        favoriteClicked = false
        UITableViewCell.setAnimationsEnabled(true)
        favoriteTableView?.reloadSections([1], with: .fade)
        }
}

extension FavoriteViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if feed.count == 0 && participatingFeed.count == 0 {
            tableView.setEmptyView()
            return 0
        } else {
            tableView.restore()
            if section == 0 {
                if chatClicked {
                    return participatingFeed.count + 1
                } else {
                    return participatingFeed.count
                }
            } else {
                if favoriteClicked {
                    return feed.count + 1
                } else {
                    return feed.count
                }
            }
        }

    }

    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard let firstHeaderView =
                tableView.dequeueReusableHeaderFooterView(withIdentifier: "FavoriteTableViewFirstHeader") as? FavoriteTableViewFirstHeader
        else { return UITableViewHeaderFooterView() }
        firstHeaderView.delegate = self

        guard let secondHeaderView =
                tableView.dequeueReusableHeaderFooterView(withIdentifier: "FavoriteTableViewSecondHeader") as? FavoriteTableViewSecondHeader
        else {return UITableViewHeaderFooterView() }
        secondHeaderView.delegate = self

        if section == 0 {
            return firstHeaderView
        } else if section == 1 {
            return secondHeaderView
        } else {
            return nil
        }
    }

    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if section == 0 {
            if participatingFeed.count == 0 {
                return 0
            } else {
                return 64
            }
        } else if section == 1 {
            return 64
        } else {
            return 0
        }
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        2
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let feedCell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as? FeedCell else { return UITableViewCell() }
        guard let currentToolTipCell = tableView.dequeueReusableCell(withIdentifier: "ChattingToolTipCell", for: indexPath) as? ChattingToolTipCell else { return UITableViewCell() }
        guard let favoriteToolTipCell = tableView.dequeueReusableCell(withIdentifier: "FavoriteToolTipCell", for: indexPath) as? FavoriteToolTipCell else { return UITableViewCell() }

        if indexPath.section == 0 {
            if chatClicked {
                if indexPath.row == 0 {
                    return currentToolTipCell
                } else {
                    guard let feed = participatingFeed[safe: indexPath.row - 1] else { return feedCell }
                    feedCell.updateUI(feed)
                    return feedCell
                }

            } else {
                guard let feed = participatingFeed[safe: indexPath.row] else { return feedCell }
                feedCell.updateUI(feed)
                return feedCell
            }

        } else {
            if favoriteClicked {
                if indexPath.row == 0 {
                    return favoriteToolTipCell
                } else {
                    guard let feed = feed[safe: indexPath.row - 1] else { return feedCell }
                    feedCell.updateUI(feed)
                    return feedCell
                }
            } else {
                guard let feed = feed[safe: indexPath.row] else { return feedCell }
                feedCell.updateUI(feed)
                return feedCell
            }
        }
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let myFeedViewController = storyboard.instantiateViewController(withIdentifier: "MyFeedViewController") as? MyFeedViewController else { return }
        guard let otherFeedViewController = storyboard.instantiateViewController(withIdentifier: "OtherFeedViewController") as? OtherFeedViewController else { return }
        var selectedFeed: Feed?
        if indexPath.section == 0 {
            if chatClicked {
                if indexPath.row == 0 {
                    return
                }
                selectedFeed = participatingFeed[safe: indexPath.row - 1]
            } else {
                selectedFeed = participatingFeed[safe: indexPath.row]
            }
        } else {
            if favoriteClicked {
                if indexPath.row == 0 {
                    return
                } else {
                    selectedFeed = feed[safe: indexPath.row - 1]
                }
            } else {
                selectedFeed = feed[safe: indexPath.row]
            }
            selectedFeed = feed[safe: indexPath.row]
        }

        if let selectedFeed = selectedFeed {
            myFeedViewController.distance = selectedFeed.distance
            otherFeedViewController.distance = selectedFeed.distance
            myFeedViewController.index = selectedFeed.postId
            otherFeedViewController.index = selectedFeed.postId
        }

        let selectedViewController : UIViewController
        if selectedFeed?.mine == true {
            selectedViewController = myFeedViewController
        } else {
            selectedViewController = otherFeedViewController
        }
        selectedViewController.modalTransitionStyle = .coverVertical
        selectedViewController.modalPresentationStyle = .fullScreen
        present(selectedViewController, animated: true, completion: nil)
    }
}


extension FavoriteViewController: FavoriteToolTipCellDelegate, ChattingToolTipCellDelegate {
    func chattingToolTipCellToggleSection() {
        chatClicked = true
        UITableViewCell.setAnimationsEnabled(false)
        favoriteTableView?.reloadSections([0], with: .automatic)
        UITableViewCell.setAnimationsEnabled(true)
        runFirstTimer()
    }

    func favoriteToolTipCellToggleSection() {
        favoriteClicked = true
        UITableViewCell.setAnimationsEnabled(false)
        favoriteTableView?.reloadSections([1], with: .automatic)
        UITableViewCell.setAnimationsEnabled(true)
        runSecondTimer()
    }
}

//
//  FavoriteViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import UIKit

class FavoriteViewController: UIViewController {

    @IBOutlet var favoriteTableView: UITableView?
    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        favoriteTableView?.dataSource = self
        favoriteTableView?.delegate = self
    }

    private func setupTableView(){
        let feedNib = UINib(nibName: "FeedCell", bundle: nil)
        favoriteTableView?.register(feedNib, forCellReuseIdentifier: "FeedCell")
        favoriteTableView?.dataSource = self
        initializeNibs()
    }

    private func initializeNibs() {
        let firstSectionHeaderNib = UINib(nibName: "FavoriteTableViewFirstHeader", bundle: nil)
        favoriteTableView?.register(firstSectionHeaderNib, forHeaderFooterViewReuseIdentifier: "FavoriteTableViewFirstHeader")

        let secondSectionHeaderNib = UINib(nibName: "FavoriteTableViewSecondHeader", bundle: nil)
        favoriteTableView?.register(secondSectionHeaderNib, forHeaderFooterViewReuseIdentifier: "FavoriteTableViewSecondHeader")
    }
}

extension FavoriteViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return 1
        } else {
            return 10
        }
    }

    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard let firstHeaderView =
                tableView.dequeueReusableHeaderFooterView(withIdentifier: "FavoriteTableViewFirstHeader") as? FavoriteTableViewFirstHeader
        else { return UITableViewHeaderFooterView() }

        guard let secondHeaderView =
                tableView.dequeueReusableHeaderFooterView(withIdentifier: "FavoriteTableViewSecondHeader") as? FavoriteTableViewSecondHeader
        else {return UITableViewHeaderFooterView() }

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
            return 64
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

        return feedCell
    }
}

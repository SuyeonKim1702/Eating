//
//  MyPageViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import UIKit

class MyPageViewController: UIViewController {

    @IBOutlet var myPageTableView: UITableView?
    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
    }

    private func setupTableView() {
        myPageTableView?.dataSource = self
        myPageTableView?.delegate = self
        setupTableViewCell()
        setupTableViewHeader()
    }

    private func setupTableViewCell(){
        let profileNib = UINib(nibName: "ProfileCell", bundle: nil)
        let reviewNib = UINib(nibName: "ReviewCell", bundle: nil)
        let cardNib = UINib(nibName: "CardCell", bundle: nil)

        myPageTableView?.register(profileNib, forCellReuseIdentifier: "ProfileCell")
        myPageTableView?.register(reviewNib, forCellReuseIdentifier: "ReviewCell")
        myPageTableView?.register(cardNib, forCellReuseIdentifier: "CardCell")
    }

    private func setupTableViewHeader() {
        let secondSectionHeaderNib = UINib(nibName: "ProfileTableViewSecondHeader", bundle: nil)
        myPageTableView?.register(secondSectionHeaderNib, forHeaderFooterViewReuseIdentifier: "ProfileTableViewSecondHeader")

        let thirdSectionHeaderNib = UINib(nibName: "ProfileTableViewThirdHeader", bundle: nil)
        myPageTableView?.register(thirdSectionHeaderNib, forHeaderFooterViewReuseIdentifier: "ProfileTableViewThirdHeader")

        let fourthSectionHeaderNib = UINib(nibName: "ProfileTableViewFourthHeader", bundle: nil)
        myPageTableView?.register(fourthSectionHeaderNib, forHeaderFooterViewReuseIdentifier: "ProfileTableViewFourthHeader")

    }
}

extension MyPageViewController: UITableViewDataSource, UITableViewDelegate {

    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        switch section {
        case 0:
            return 0
        default:
            return 64
        }

    }

    func numberOfSections(in tableView: UITableView) -> Int {
        4
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch section {
        case 0:
            return 1
        case 1:
            return 0
        case 2:
            return 4
        case 3:
            return 4
        default:
            return 0
        }
    }

    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard let secondHeaderView =
                tableView.dequeueReusableHeaderFooterView(withIdentifier: "ProfileTableViewSecondHeader") as? ProfileTableViewSecondHeader
        else { return UITableViewHeaderFooterView() }

        guard let thirdHeaderView =
                tableView.dequeueReusableHeaderFooterView(withIdentifier: "ProfileTableViewThirdHeader") as? ProfileTableViewThirdHeader
        else {return UITableViewHeaderFooterView() }

        guard let fourthHeaderView =
                tableView.dequeueReusableHeaderFooterView(withIdentifier: "ProfileTableViewFourthHeader") as? ProfileTableViewFourthHeader
        else {return UITableViewHeaderFooterView() }

        switch section {
        case 0:
            return UITableViewHeaderFooterView()
        case 1:
             return secondHeaderView
        case 2:
            return thirdHeaderView
        case 3:
            return fourthHeaderView
        default:
            return UITableViewHeaderFooterView()
        }
}

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let profileCell = tableView.dequeueReusableCell(withIdentifier: "ProfileCell", for: indexPath) as? ProfileCell else { return UITableViewCell() }
        guard let reviewCell = tableView.dequeueReusableCell(withIdentifier: "ReviewCell", for: indexPath) as? ReviewCell else { return UITableViewCell() }
        guard let cardCell = tableView.dequeueReusableCell(withIdentifier: "CardCell", for: indexPath) as? CardCell else { return UITableViewCell() }

        switch indexPath.section {
        case 0:
            return profileCell
        case 1:
            return UITableViewCell()
        case 2:
            return cardCell
        case 3:
            return reviewCell
        default:
            return UITableViewCell()
        }
    }
}

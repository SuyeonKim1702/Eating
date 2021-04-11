//
//  MyPageViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import UIKit

class MyPageViewController: UIViewController {

    @IBOutlet var myPageTableView: UITableView?
    var nickname = ""
    var sugarPercent = Int()
    var totalEatingCount = Int()
    var timeGood = Int()
    var niceGuy = Int()
    var foodDivide = Int()
    var fastAnswer = Int()
    var cardNumArray = [Int]()
    var commentArray = ["음식을 정확하게 반으로 나눴어요!", "시간을 잘 지켜요.", "친절해요.", "답변이 빨라요"]
    let profileService = GetUserService()
    let reviewService = GetReviewListService()
    var num = Int()
    var reviews = [Review]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        myPageTableView?.contentInset = UIEdgeInsets(top: -35, left: 0, bottom: 0, right: 0)
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        callProfileAPI()
    }

    private func callProfileAPI() {
        profileService.getUserInfo { [weak self] result in
            switch result {
            case .success(let data):
                self?.nickname = data.nickname
                self?.sugarPercent = data.sugarScore
                self?.timeGood = data.timeGood
                self?.niceGuy = data.niceGuy
                self?.foodDivide = data.foodDivide
                self?.fastAnswer = data.fastAnswer
                self?.totalEatingCount = data.totalCount
                self?.cardNumArray = [data.foodDivide, data.timeGood, data.niceGuy, data.fastAnswer]

                DispatchQueue.main.async {
                    self?.callReviewApi()
                }

            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    private func callReviewApi() {
        reviewService.getReviewList { [weak self] result in
                switch result {
                case .success(let data):
                    self?.num = data.count
                    self?.reviews = data
                    DispatchQueue.main.async {
                        self?.myPageTableView?.reloadData()
                    }

                case .failure(let error):
                    print(error.localizedDescription)
                }
        }
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
    @IBAction func goToSettingPage(_ sender: Any) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let settingViewController = storyboard.instantiateViewController(withIdentifier: "SettingViewController") as? SettingViewController else { return }
        settingViewController.modalTransitionStyle = .coverVertical
        settingViewController.modalPresentationStyle = .fullScreen

        present(settingViewController, animated: true, completion: nil)
        
    }
}

extension MyPageViewController: UITableViewDataSource, UITableViewDelegate {

    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        switch section {
        case 0:
            return 0.0
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
            if reviews.count < 4 {
                return reviews.count
            } else {
                return 4
            }
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
        fourthHeaderView.updateUI(num: num)

        secondHeaderView.viewController = self
        fourthHeaderView.viewController = self

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
        print(indexPath.section)
        guard let profileCell = tableView.dequeueReusableCell(withIdentifier: "ProfileCell", for: indexPath) as? ProfileCell else { return UITableViewCell() }
        profileCell.viewController = self
        if indexPath.row == 0 {
        profileCell.updateUI(nickname: nickname, tradeCount: totalEatingCount, sugarPercent: sugarPercent)
        }

        guard let reviewCell = tableView.dequeueReusableCell(withIdentifier: "ReviewCell", for: indexPath) as? ReviewCell else { return UITableViewCell() }
        if indexPath.section == 3 {
            reviewCell.updateUI(review: reviews[indexPath.row])
        }
        guard let cardCell = tableView.dequeueReusableCell(withIdentifier: "CardCell", for: indexPath) as? CardCell else { return UITableViewCell() }
        if indexPath.section == 2 {
            if cardNumArray.count == 4 {
                cardCell.updateUI(num: cardNumArray[indexPath.row], comment: commentArray[indexPath.row])
            }
        }

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


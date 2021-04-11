//
//  HomeViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import UIKit

class HomeViewController: UIViewController {
    @IBOutlet var feedTableView: UITableView?
    @IBOutlet var distanceLabel: UILabel!
    @IBOutlet var addressLabel: UILabel?
    @IBOutlet var addressImageView: UIImageView!
    private let floatingImageView = UIImageView()
    let favoriteService = FavoriteService()
    let feedService = GetFeedService()
    var feed = [Feed]()
    var category = "0-1-2-3-4-5-6-7-8-9"
    var distance: Int = 500
    var currentPage = 1
    var check = false
    var count = 0
    var reviewMembers = [ReviewMember]()
    var addressService = AddressService()
    var getReviewMemberService = GetReviewMemberService()
    var sliderValue: Float = 200
    var selectedButtonMenu = [Int]()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        Constant.check = true
        if UserDefaults.standard.string(forKey: "address") != nil {
            addressLabel?.text = UserDefaults.standard.string(forKey: "address")!
            Constant.address = UserDefaults.standard.string(forKey: "address")!
        } else {
            addressLabel?.text = Constant.address
        }

        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(tapUpperView))
        addressImageView.addGestureRecognizer(tapGesture)
        addressLabel?.addGestureRecognizer(tapGesture)
        let tabBar = tabBarController?.tabBar
        tabBar?.layer.masksToBounds = true
        tabBar?.layer.cornerRadius = 27
        tabBar?.layer.borderWidth = 1
        tabBar?.layer.borderColor = UIColor(red: 232, green: 232, blue: 232).cgColor
        tabBar?.layer.maskedCorners = [.layerMinXMinYCorner, .layerMaxXMinYCorner]
        // 리뷰 대상자 api 호출!!!!!!!
        callReviewMemberList()
    }

    private func callReviewMemberList() {
        getReviewMemberService.getReviewMemberList { [weak self] result in
            switch result {
            case .success(let members):
                self?.reviewMembers = members
                DispatchQueue.main.async {
                    self?.popupReviewPage()
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    private func popupReviewPage() {
        if count < reviewMembers.count {
            guard let reviewViewController = storyboard?.instantiateViewController(withIdentifier: "DefaultReviewViewController") as? DefaultReviewViewController else { return }
            reviewViewController.modalTransitionStyle = .coverVertical
            reviewViewController.modalPresentationStyle = .fullScreen
            reviewViewController.nickname = reviewMembers[count].nickName
            reviewViewController.userId = reviewMembers[count].kakaoId
            reviewViewController.postIdx = reviewMembers[count].postId
            present(reviewViewController, animated: true, completion: nil)
            count += 1
        }
    }

    @objc private func tapUpperView() {
        goToMapPage()
    }

    private func goToMapPage() {
        guard let viewController = storyboard?.instantiateViewController(withIdentifier: "MapViewController"), let mapViewController = viewController as? MapViewController else { return }

        mapViewController.addressTextField.text = Constant.address
        mapViewController.x = Constant.latitude
        mapViewController.y = Constant.longitude

        mapViewController.modalTransitionStyle = .coverVertical
        mapViewController.modalPresentationStyle = .fullScreen
        present(mapViewController, animated: true, completion: nil)
    }

    private func callFeedApi(_ page: Int) {
            feedService.getFeedList(apiType: .postList, page: page, category: category, distance: distance, mine: 0) {  [weak self] result in
                switch result {
                case .success(let feeds):
                    DispatchQueue.main.async { [self] in
                        if feeds.count != 0 {
                            print(feeds)
                            self?.feed.append(contentsOf: feeds)
                            self?.feedTableView?.reloadData()
                        }
                    }
                case .failure(let error):
                    print(error.localizedDescription)
                }
            }
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        currentPage = 0
        distanceLabel.text = "\(distance)M"
        attachFloatingImageView()
        addGestureRecognizer()
        popupReviewPage()
        callFeedApi(0)
        feed = [Feed]()
    }

    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        detachFloatingImageView()
    }

    private func detachFloatingImageView() {
        floatingImageView.removeFromSuperview()
    }
    
    private func addGestureRecognizer() {
        let tapGestureRecognizerForFloatingImage = UITapGestureRecognizer(target: self, action: #selector(tapFloatingImage))
        floatingImageView.addGestureRecognizer(tapGestureRecognizerForFloatingImage)
    }
    
    @IBAction func moveToFilterPage(_ sender: Any) {
        guard let filterViewController = storyboard?.instantiateViewController(withIdentifier: "FilterViewController") as? FilterViewController else { return }
        filterViewController.modalTransitionStyle = .coverVertical
        filterViewController.modalPresentationStyle = .fullScreen
        filterViewController.homeViewController = self
        filterViewController.sliderValue = sliderValue
        filterViewController.toolTipText = distance
        filterViewController.selectedButton = selectedButtonMenu
        present(filterViewController, animated: true, completion: nil)
    }

    @objc private func tapFloatingImage() {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let writingViewController = storyboard.instantiateViewController(withIdentifier: "WritingViewController") as? WritingViewController else { return }
        
        writingViewController.modalTransitionStyle = .coverVertical
        writingViewController.modalPresentationStyle = .fullScreen
        present(writingViewController, animated: true, completion: nil)
        
    }

    func tapFavoriteButton(_ sender: Any) {

    }
    
    private func attachFloatingImageView() {

        guard let keyWindow = UIApplication.shared.keyWindow else { return }
        keyWindow.addSubview(floatingImageView)
       
        floatingImageView.isUserInteractionEnabled = true
        floatingImageView.translatesAutoresizingMaskIntoConstraints = false
        floatingImageView.image = UIImage(named: "plusIcon")
        floatingImageView.rightAnchor.constraint(equalTo: keyWindow.rightAnchor, constant: -15).isActive = true
        floatingImageView.bottomAnchor.constraint(equalTo: keyWindow.bottomAnchor, constant: -100).isActive = true
    }
    
    private func setupTableView(){
        let feedNib = UINib(nibName: "FeedCell", bundle: nil)
        feedTableView?.register(feedNib, forCellReuseIdentifier: "FeedCell")
        feedTableView?.dataSource = self
        feedTableView?.delegate = self
    }
}

extension HomeViewController: UITableViewDataSource, UITableViewDelegate {
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
        print(feed.title, feed.favorite)

        feedCell.tapHeartButton = {
            self.favoriteService.changeFavoriteState(postIdx: feed.postId, state: !feed.favorite) { [weak self] data in
                switch data {
                case .success(_):
                    DispatchQueue.main.async {
                        feedCell.heartButton?.isSelected = !(feedCell.heartButton!.isSelected)
                    }
                case .failure(let error):
                    print(error.localizedDescription)
                }
            }

        }


        return feedCell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let myFeedViewController = storyboard.instantiateViewController(withIdentifier: "MyFeedViewController") as? MyFeedViewController else { return }
        myFeedViewController.index = feed[safe: indexPath.row]?.postId
        myFeedViewController.distance = feed[safe: indexPath.row]?.distance ?? 0

        guard let otherFeedViewController = storyboard.instantiateViewController(withIdentifier: "OtherFeedViewController") as? OtherFeedViewController else { return }
        otherFeedViewController.index = feed[safe: indexPath.row]?.postId
        otherFeedViewController.distance = feed[safe: indexPath.row]?.distance ?? 0

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

    func scrollViewDidScroll(_ scrollView: UIScrollView) {

        let offsetY = scrollView.contentOffset.y
        let contentHeight = scrollView.contentSize.height

        if offsetY > contentHeight - scrollView.frame.height {
            currentPage += 1
            callFeedApi(currentPage)
    }
}
}

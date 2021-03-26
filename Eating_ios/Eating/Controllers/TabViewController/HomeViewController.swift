//
//  HomeViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import UIKit

class HomeViewController: UIViewController {
    @IBOutlet var feedTableView: UITableView?
    @IBOutlet var addressLabel: UILabel?
    private let floatingImageView = UIImageView()
    let feedService = GetFeedService()
    var feed = [Feed]()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        addressLabel?.text = Constant.address

        feedService.getFeedList(page: 0) {  [weak self] result in
            switch result {
            case .success(let feeds):
                print("갯수",feeds.count)
                DispatchQueue.main.async {
                    for i in feeds{
                        print(i.title)
                    }
                    self?.feed = feeds
                    self?.feedTableView?.reloadData()

                }
            case .failure(let error):
                print("여기")
                print(error.localizedDescription)
            }

        }
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        attachFloatingImageView()
        addGestureRecognizer()
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
        present(filterViewController, animated: true, completion: nil)
    }

    @IBAction func moveToSearchPage(_ sender: Any) {
        guard let searchViewController = storyboard?.instantiateViewController(withIdentifier: "SearchViewController") as? SearchViewController else { return }
        searchViewController.modalTransitionStyle = .coverVertical
        searchViewController.modalPresentationStyle = .fullScreen
        present(searchViewController, animated: true, completion: nil)

    }
    @objc private func tapFloatingImage() {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let writingViewController = storyboard.instantiateViewController(withIdentifier: "WritingViewController") as? WritingViewController else { return }
        
        writingViewController.modalTransitionStyle = .coverVertical
        writingViewController.modalPresentationStyle = .fullScreen
        present(writingViewController, animated: true, completion: nil)
        
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
        10
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
        guard let otherFeedViewController = storyboard.instantiateViewController(withIdentifier: "OtherFeedViewController") as? OtherFeedViewController else { return }

        // 피드가 내 피드인지 아닌지 확인하고 -> 맞는 vc 고르기
        let selectedViewController = otherFeedViewController

        selectedViewController.modalTransitionStyle = .coverVertical
        selectedViewController.modalPresentationStyle = .fullScreen
        present(selectedViewController, animated: true, completion: nil)
    }
}

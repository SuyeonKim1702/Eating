//
//  HomeViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/13.
//

import UIKit

class HomeViewController: UIViewController {
    @IBOutlet var feedTableView: UITableView?
    private let floatingImageView = UIImageView()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        stylingFloatingImageView()
        addGestureRecognizer()      
    }
    
    private func addGestureRecognizer() {
        let tapGestureRecognizerForFloatingImage = UITapGestureRecognizer(target: self, action: #selector(tapFloatingImage))
        floatingImageView.addGestureRecognizer(tapGestureRecognizerForFloatingImage)
    }
    
    @objc private func tapFloatingImage() {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let writingViewController = storyboard.instantiateViewController(withIdentifier: "WritingViewController") as? WritingViewController else { return }
        
        writingViewController.modalTransitionStyle = .coverVertical
        writingViewController.modalPresentationStyle = .fullScreen
        present(writingViewController, animated: true, completion: nil)
        
    }
    
    private func stylingFloatingImageView() {

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
    }
}

extension HomeViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        10
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let feedCell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as? FeedCell else { return UITableViewCell() }
        
        return feedCell
    }  
}

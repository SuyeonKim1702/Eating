//
//  ReviewListViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/25.
//

import UIKit

class ReviewListViewController: UIViewController {
    @IBOutlet var reviewListTableView: UITableView!
    let reviewService = GetReviewListService()
    @IBOutlet var reviewCountLabel: UILabel!
    var reviews = [Review]()

    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        callReviewApi()
    }

    private func callReviewApi() {
        reviewService.getReviewList { [weak self] result in
            switch result {
            case .success(let data):
                DispatchQueue.main.async {
                    self?.reviews = data
                    self?.reviewListTableView.reloadData()
                    self?.reviewCountLabel.text = "\(data.count)"
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    func setupTableView() {
        let reviewNib = UINib(nibName: "ReviewCell", bundle: nil)
        reviewListTableView?.register(reviewNib, forCellReuseIdentifier: "ReviewCell")
        reviewListTableView?.dataSource = self
    }

    @IBAction func goBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
}

extension ReviewListViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        reviews.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let reviewCell = tableView.dequeueReusableCell(withIdentifier: "ReviewCell", for: indexPath) as? ReviewCell else { return UITableViewCell() }
        reviewCell.updateUI(review: reviews[indexPath.row])

        return reviewCell
    }


}

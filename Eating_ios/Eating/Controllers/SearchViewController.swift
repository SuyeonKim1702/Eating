//
//  SearchViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/21.
//

import UIKit

class SearchViewController: UIViewController {

    @IBOutlet var searchTextField: UITextField?
    @IBOutlet var popularTableView: UITableView?
    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        setupTextField()
    }

    private func setupTextField() {
        searchTextField?.addLeftPadding()
        searchTextField?.addleftimage(image: UIImage(named: "searchIcon")!)
    }

    private func setupTableView(){
        let menuNib = UINib(nibName: "PopularMenu", bundle: nil)
        popularTableView?.register(menuNib, forCellReuseIdentifier: "PopularMenu")
        popularTableView?.dataSource = self
        popularTableView?.isScrollEnabled = false
    }

    @IBAction func tapGoBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
}

extension SearchViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        10
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        guard let menuCell = tableView.dequeueReusableCell(withIdentifier: "PopularMenu", for: indexPath) as? PopularMenu else { return UITableViewCell() }

        return menuCell
    }


}

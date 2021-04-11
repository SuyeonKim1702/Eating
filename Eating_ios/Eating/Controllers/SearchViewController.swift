//
//  SearchViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/21.
//

import UIKit

class SearchViewController: UIViewController {

    @IBOutlet var searchTextField: UITextField!
    @IBOutlet var popularTableView: UITableView?
    let menuArray = ["햄버거", "떡볶이", "버블티", "치킨", "피자", "삼겹살", "파스타", "쌀국수", "자장면", "곱창", "냉면", "마라탕", "돈까스"]
    var category = "0-1-2-3-4-5-6-7-8-9"
    var numberArray = [Int]()
    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        setupTextField()

        while numberArray.count < 10 {
            let number = Int.random(in: 0...12)
            if !numberArray.contains(number){
                numberArray.append(number)
            }
        }
    }

    private func setupTextField() {
        searchTextField?.addLeftPadding()
        searchTextField?.addleftimage(image: UIImage(named: "searchIcon")!)
        searchTextField?.layer.cornerRadius = 30
        searchTextField?.isEnabled = true
        searchTextField?.delegate = self
    }

    private func setupTableView(){
        let menuNib = UINib(nibName: "PopularMenu", bundle: nil)
        popularTableView?.register(menuNib, forCellReuseIdentifier: "PopularMenu")
        popularTableView?.dataSource = self
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
        menuCell.updateUI(rank: indexPath.row + 1, menu: menuArray[numberArray[indexPath.row]])

        return menuCell
    }
}

extension SearchViewController: UITextFieldDelegate {

    func callCategoryApi(_ keyword: String) {
        switch keyword {
        case let str where str.contains("한식") || str.contains("삼겹살") || str.contains("국밥") || str.contains("찌개") || str.contains("돈까스"):
            category = "0"
        case let str where str.contains("일식") || str.contains("연어") || str.contains("초밥") || str.contains("회") || str.contains("돈부리"):
            category = "1"
        case let str where str.contains("떡볶이") || str.contains("김밥") || str.contains("분식") || str.contains("분식") || str.contains("순대") || str.contains("튀김"):
            category = "2"
        case let str where str.contains("카페") || str.contains("디저트") || str.contains("커피") || str.contains("버블티") || str.contains("케이크") || str.contains("마카롱"):
            category = "3"
        case let str where str.contains("치킨") || str.contains("닭강정"):
            category = "4"
        case let str where str.contains("피자"):
            category = "5"
        case let str where str.contains("쌀국수") || str.contains("분짜") || str.contains("딤섬") || str.contains("똠양꿍") || str.contains("반미") || str.contains("팟타이"):
            category = "6"
        case let str where str.contains("자장면") || str.contains("짜장면") || str.contains("탕수육") || str.contains("짬뽕") || str.contains("볶음밥") || str.contains("마라탕") || str.contains("마라샹궈") :
            category = "7"
        case let str where str.contains("보쌈") || str.contains("족발") || str.contains("곱창") || str.contains("닭발"):
            category = "8"
        case let str where str.contains("햄버거") || str.contains("타코"):
            category = "9"
        default:
            category = "0-1-2-3-4-5-6-7-8-9"
        }

        ((presentingViewController as? UITabBarController)?.children[0] as? HomeViewController)?.category = category

        dismiss(animated: true, completion: nil)
    }

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.endEditing(true)
        let keyword = textField.text
        callCategoryApi(keyword ?? "")


        return true
    }
}

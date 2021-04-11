//
//  WritingUpdateViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/29.
//

import UIKit

class WritingUpdateViewController: UIViewController {

    @IBOutlet var modifyButton: UIButton!
    @IBOutlet var deleteButton: UIButton!
    var index = 0
    let deletePostService = DeletePostService()
    weak var myFeedViewController: MyFeedViewController?

    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()
    }

    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?){
        dismiss(animated: true, completion: nil)
   }

    private func stylingViews() {
        modifyButton.layer.cornerRadius = 25
        deleteButton.layer.cornerRadius = 25
    }

    @IBAction func deletePost(_ sender: Any) {
        deletePostService.deletePost(postIdx: index) { [weak self] data in
            switch data {
            case .success(let data):
                DispatchQueue.main.sync {
                    print(self?.presentingViewController)
                    self?.dismiss(animated: true) {
                        self?.myFeedViewController?.dismissSelf()
                    }
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }

    @IBAction func modifyPost(_ sender: Any) {
        guard let writingViewController = storyboard?.instantiateViewController(withIdentifier: "WritingViewController") as? WritingViewController else { return }
        writingViewController.modalTransitionStyle = .coverVertical
        writingViewController.modalPresentationStyle = .overCurrentContext
        present(writingViewController, animated: true) {
            writingViewController.index = (self.myFeedViewController?.index)!
            writingViewController.setupModifyValue(for: self.myFeedViewController!.feedDetail!)
        }
    }
}

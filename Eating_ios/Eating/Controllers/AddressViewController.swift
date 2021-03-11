//
//  AddressViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import UIKit

class AddressViewController: UIViewController {
    @IBOutlet weak var addressTextField: UITextField?
    let addressService = AddressService()
    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()
    }
    
    private func stylingViews() {
        addressTextField?.addLeftPadding()
        addressTextField?.layer.cornerRadius = 32
    }
    
    private func callAddressApi(for query: String) {
        addressService.getAddressInfo{ places in
            switch(places){
            case .success(let places):
                print(places)
                break
            case .failure(let failure):
                print(failure.localizedDescription)
                break
            }
        }
    }

    @IBAction func tapCompleteButton(_ sender: Any) {
        guard let mapViewController = storyboard?.instantiateViewController(withIdentifier: "MapViewController") else { return }
        mapViewController.modalTransitionStyle = .coverVertical
        mapViewController.modalPresentationStyle = .fullScreen
        present(mapViewController, animated: true, completion: nil)
    }
}

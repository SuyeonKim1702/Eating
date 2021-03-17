//
//  AddressViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import UIKit

class AddressViewController: UIViewController {
    @IBOutlet weak var addressTextField: UITextField?
    @IBOutlet var addressTableView: UITableView?
    let addressService = AddressService()
    var places: [Place]?

    override func viewDidLoad() {
        super.viewDidLoad()
        stylingViews()
        addressTextField?.delegate = self
        addressTableView?.dataSource = self
        addressTableView?.delegate = self
    }
    
    private func stylingViews() {
        addressTextField?.addLeftPadding()
        addressTextField?.layer.cornerRadius = 32
        addressTextField?.addleftimage(image: UIImage(named:"searchIcon")!)
    }
    
    private func callAddressApi(for query: String) {
        addressService.getAddressInfo(for: query){ [weak self] places in
            switch(places){
            case .success(let places):
                self?.places = places
                DispatchQueue.main.async {
                    self?.addressTableView?.reloadData()
                }
                break
            case .failure(let failure):
                print(failure.localizedDescription)
                break
            }
        }
    }
    
    @IBAction func tapGoBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
}

extension AddressViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.endEditing(true)
        if let text = textField.text {
            callAddressApi(for: text)
        }
        return true
    }
}

extension AddressViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        places?.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "AddressCell", for: indexPath) as? AddressCell else { return UITableViewCell() }
        
        if let place = places?[safe: indexPath.row] {
            cell.updateUI(for: place)
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard let place = places?[safe: indexPath.row] else { return }
        guard let viewController = storyboard?.instantiateViewController(withIdentifier: "MapViewController"), let mapViewController = viewController as? MapViewController else { return }
    
        mapViewController.addressTextField.text = place.addressName
        mapViewController.x = Double(place.x)
        mapViewController.y = Double(place.y)
        
        mapViewController.modalTransitionStyle = .coverVertical
        mapViewController.modalPresentationStyle = .fullScreen
        present(mapViewController, animated: true, completion: nil)
    }
    
}

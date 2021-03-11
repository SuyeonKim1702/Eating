//
//  MapViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import UIKit

class MapViewController: UIViewController, MTMapViewDelegate {
    
    var mapView: MTMapView?
    let completeButton = UIButton()
    let addressButton = UIButton()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setForMapView()
        stylingCompleteButton()
        stylingAddressButton()
    }
    
    private func setForMapView() {
        mapView = MTMapView(frame: view.bounds)
        if let mapView = mapView {
            mapView.delegate = self
            mapView.baseMapType = .standard
            view.addSubview(mapView)

        }
    }
    
    private func stylingCompleteButton() {
        completeButton.backgroundColor = UIColor(rgb: 0xfddc21)
        mapView?.addSubview(completeButton)
        completeButton.translatesAutoresizingMaskIntoConstraints = false
        
        completeButton.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 20).isActive = true
        completeButton.rightAnchor.constraint(equalTo: view.rightAnchor, constant: -20).isActive = true
        completeButton.heightAnchor.constraint(equalToConstant: 63).isActive = true
        completeButton.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -50).isActive = true
        completeButton.layer.cornerRadius = 32
        completeButton.setTitle("선택한 위치로 설정", for: .normal)
        completeButton.setTitleColor(.black, for: .normal)
        completeButton.titleLabel?.font = UIFont(name: "JalnanOTF", size: 20)
        completeButton.layer.shadowRadius = 5
        completeButton.layer.shadowColor = UIColor(rgb: 0x000000, alpha: 29).cgColor
        completeButton.layer.shadowOffset = CGSize(width: 0, height: 3)
        completeButton.layer.shadowOpacity = 0.29
    }
    
    private func stylingAddressButton() {
        addressButton.backgroundColor = .white
        mapView?.addSubview(addressButton)
        addressButton.translatesAutoresizingMaskIntoConstraints = false
        addressButton.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 20).isActive = true
        addressButton.rightAnchor.constraint(equalTo: view.rightAnchor, constant: -20).isActive = true
        addressButton.bottomAnchor.constraint(equalTo: completeButton.topAnchor, constant: -25).isActive = true
        addressButton.heightAnchor.constraint(equalToConstant: 100).isActive = true
        addressButton.layer.cornerRadius = 10
        addressButton.layer.shadowRadius = 5
        addressButton.layer.shadowColor = UIColor(rgb: 0x000000, alpha: 29).cgColor
        addressButton.layer.shadowOffset = CGSize(width: 0, height: 3)
        addressButton.layer.shadowOpacity = 0.29
        addressButton.setTitle("서울 영등포구 선유로 9길 30 106동 싹 영등포 캠퍼스", for: .normal)
        addressButton.setTitleColor(.black, for: .normal)
        addressButton.titleLabel?.font = UIFont(name: "NanumSquareRoundOTFEB", size: 17)
        addressButton.titleLabel?.lineBreakMode = .byWordWrapping
        addressButton.titleLabel?.textAlignment = .center
        addressButton.titleLabel?.numberOfLines = 2
    }


}

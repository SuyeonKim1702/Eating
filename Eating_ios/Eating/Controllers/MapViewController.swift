//
//  MapViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/07.
//

import UIKit
import CoreLocation

class MapViewController: UIViewController, CLLocationManagerDelegate {
    
    var mapView: MTMapView?
    let completeButton = UIButton()
    let addressTextField = UITextField()
    let currentLocationImageView = UIImageView()
    let regionCodeService = AddressRegionCodeService()
    let postUserService = PostUserService()
    var x: Double?
    var y: Double?
    private let locationManager = CLLocationManager()

    override func viewDidLoad() {
        super.viewDidLoad()
        setForMapView()
        stylingCompleteButton()
        stylingTextField()
        stylingCurrentLocationButton()
        addGestureRecognizer()
        addCenterMarker()
        locationManager.delegate = self
    }
    
    @objc private func goBack() {
        dismiss(animated: true, completion: nil)
    }
    
    @objc private func moveToCurrentLocation() {
        locationManager.requestWhenInUseAuthorization()
        if CLLocationManager.authorizationStatus() == .denied {
           locationPermissionAlert()
        }
        if let coor = locationManager.location?.coordinate {
            y = coor.latitude
            x = coor.longitude
            setMapCenter(x!, y!)
        }
    }

    private func goToNextPage() {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        guard let tabBarController = storyboard.instantiateViewController(withIdentifier: "UITabBarController") as? UITabBarController else { return }

        tabBarController.modalTransitionStyle = .coverVertical
        tabBarController.modalPresentationStyle = .fullScreen
        tabBarController.tabBar.tintColor = .black

        present(tabBarController, animated: true, completion: nil)
    }
    
    @objc private func tapCompeteButton() {
        Constant.address = addressTextField.text ?? ""
        goToNextPage()

        postUserService.postUserInfo(id: "2f321343f", nickname: Constant.nickname, latitude: x, longitude: y, address: Constant.address) { [weak self] result in
            switch result {
            case .success(let data):
                print(data)
                DispatchQueue.main.async {
                    self?.goToNextPage()
                }
            case .failure(let error):
                print(error.localizedDescription)
                //로그인에 실패했습니다. 메세지 보여주기
            }
        }

    }
    
    private func locationPermissionAlert() {
        let locationAlertController = UIAlertController(title: nil, message: Constant.locationPermissionString, preferredStyle: .alert)
        let settingAction = UIAlertAction(title: "설정으로 이동", style: .default, handler: {_ in
            guard let settingString = URL(string: UIApplication.openSettingsURLString) else { return }
            UIApplication.shared.open(settingString, options: [:], completionHandler: nil)
        })
        let cancelAction = UIAlertAction(title: "취소", style: .cancel, handler: nil)
        
        locationAlertController.addAction(settingAction)
        locationAlertController.addAction(cancelAction)
        present(locationAlertController, animated: true, completion: nil)
    }
    
    private func addGestureRecognizer() {
        let tapGestureRecognizerForGoBack = UITapGestureRecognizer(target: self, action: #selector(goBack))
        let tapGestureRecognizerForCurrentLocation = UITapGestureRecognizer(target: self, action: #selector(moveToCurrentLocation))
        let tapGestureRecognizerForCompleteButton = UITapGestureRecognizer(target: self, action: #selector(tapCompeteButton))
        completeButton.addGestureRecognizer(tapGestureRecognizerForCompleteButton)
        addressTextField.addGestureRecognizer(tapGestureRecognizerForGoBack)
        currentLocationImageView.addGestureRecognizer(tapGestureRecognizerForCurrentLocation)
    }
    
    private func addCenterMarker() {
        let newView = UIView(frame: view.bounds)
        view.addSubview(newView)
        newView.backgroundColor = .none
        newView.isUserInteractionEnabled = false
        
        let imageView = UIImageView()
        imageView.image = UIImage(named: "markerIcon")
        imageView.translatesAutoresizingMaskIntoConstraints = false
        newView.addSubview(imageView)
        imageView.centerXAnchor.constraint(equalTo: newView.centerXAnchor).isActive = true
        imageView.centerYAnchor.constraint(equalTo: newView.centerYAnchor).isActive = true
    }
    
    private func setMapCenter(_ x: Double, _ y: Double) {
        mapView?.setMapCenter(MTMapPoint(geoCoord: MTMapPointGeo(latitude: y, longitude: x)), zoomLevel: 1, animated: true)
    }
    
    private func setForMapView() {
        mapView = MTMapView(frame: view.bounds)
        if let mapView = mapView {
            mapView.delegate = self
            mapView.baseMapType = .standard
            view.addSubview(mapView)
         
            if let x = x, let y = y {
                setMapCenter(x, y)
            }
        }
    }
    
    private func stylingCompleteButton() {
        completeButton.isUserInteractionEnabled = true
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
    
    private func stylingTextField() {
        mapView?.addSubview(addressTextField)
        addressTextField.backgroundColor = .white
        addressTextField.translatesAutoresizingMaskIntoConstraints = false

        addressTextField.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 20).isActive = true
        addressTextField.rightAnchor.constraint(equalTo: view.rightAnchor, constant: -20).isActive = true
        addressTextField.topAnchor.constraint(equalTo: view.topAnchor, constant: 40).isActive = true
        addressTextField.font = UIFont(name: "NanumSquareRoundOTFEB", size: 15)

        addressTextField.heightAnchor.constraint(equalToConstant: 53).isActive = true
        addressTextField.layer.cornerRadius = 28
        addressTextField.addLeftPadding()
        addressTextField.addleftimage(image: UIImage(named:"searchIcon")!)
    }
    
    private func stylingCurrentLocationButton() {
        currentLocationImageView.isUserInteractionEnabled = true
        mapView?.addSubview(currentLocationImageView)
        currentLocationImageView.translatesAutoresizingMaskIntoConstraints = false
        currentLocationImageView.image = UIImage(named: "currentLocationIcon")
        currentLocationImageView.bottomAnchor.constraint(equalTo: completeButton.topAnchor, constant: -20).isActive = true
        currentLocationImageView.rightAnchor.constraint(equalTo: completeButton.rightAnchor).isActive = true
    }
}

extension MapViewController: MTMapViewDelegate {
    func mapView(_ mapView: MTMapView!, finishedMapMoveAnimation mapCenterPoint: MTMapPoint!) {
        y = mapCenterPoint.mapPointGeo().latitude
        x = mapCenterPoint.mapPointGeo().longitude
        print(x, y)
        
        regionCodeService.getAddressInfo(x: "\(String(describing: x!))", y: "\(String(describing: y!))") { [weak self] result in
            switch result {
            case .success(let address):
                DispatchQueue.main.async {
                    self?.addressTextField.text = address
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
        
    }
}

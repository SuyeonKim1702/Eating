//
//  WritingViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/16.
//

import UIKit

class WritingViewController: UIViewController {

    @IBOutlet var linkTextField: UITextField?
    @IBOutlet var notDefiendLocationButton: UIButton?
    @IBOutlet var middleLocationButton: UIButton?
    @IBOutlet var hostLocationButton: UIButton?
    @IBOutlet var noFeeButton: UIButton?
    @IBOutlet var equalFeeButton: UIButton?
    @IBOutlet var allFeeButton: UIButton?
    @IBOutlet var scrollView: UIScrollView?
    @IBOutlet var contentTextView: UITextView?
    @IBOutlet var orderTimeButton: UIButton?
    @IBOutlet var openChatTextField: UITextField?
    @IBOutlet var menuTextField: UITextField?
    @IBOutlet var titleTextField: UITextField?
    @IBOutlet var numOfPeopleButton: UIButton?
    let pickerView = UIPickerView()
    let datePickerView = UIDatePicker()
    let visualEffect = UIVisualEffectView()
    let pickerTextField = UITextField(frame: .zero)
    let datePickerTextField = UITextField(frame: .zero)
    weak var foodCategoryViewController: FoodCategoryViewController?
    let numOfPeople = ["2명", "3명", "4명"]
    var deliveryButtons =  [UIButton]()
    var locationButtons = [UIButton]()
    var menu: String = "0"
    var orderTimeForApi: String = ""
    let postFeedService = PostFeedService()

    @IBOutlet var content: UITextView?
    override func viewDidLoad() {
        super.viewDidLoad()
        setupDelegate()
        setupObserver()
        setupButtons()
        setupToolBar()
        setupVisualEffectView()
        createPickerView()
        createDatePicker()
        dismissPickerView()
        dismissDatePicker()
        setupTextFieldReturnKeyType()

        pickerTextField.isHidden = true
        datePickerTextField.isHidden = true
        view.addSubview(pickerTextField)
        view.addSubview(datePickerTextField)
    }

    private func setupToolBar() {
        let toolBarKeyboard = UIToolbar(frame: CGRect(x: 0, y: 0, width: view.frame.width, height: 60))

        let flexibleSpace = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: self, action: nil)

        let btnDoneBar = UIBarButtonItem(image: UIImage(named: "checkButton"), style: .done, target: self, action: #selector(hideKeyboard(_:)))
        toolBarKeyboard.items = [flexibleSpace, btnDoneBar]
        toolBarKeyboard.barTintColor = .white
        toolBarKeyboard.clipsToBounds = true
        contentTextView?.inputAccessoryView = toolBarKeyboard
    }

    private func setupButtons() {
        deliveryButtons = [allFeeButton!, equalFeeButton!, noFeeButton!]
        locationButtons = [hostLocationButton!, middleLocationButton!,notDefiendLocationButton!]
    }

    private func setupTextFieldReturnKeyType() {
        titleTextField?.returnKeyType = .next
        menuTextField?.returnKeyType = .next
        openChatTextField?.returnKeyType = .next
    }

    @objc private func hideKeyboard(_ sender: Any) {
        contentTextView?.endEditing(true)
    }

    @IBAction func goBackButton(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

    @IBAction func tapCategoryButton(_ sender: Any) {
        setupVisualEffectView()
        popUpFoodCategory()
    }

    private func attachChild() {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        foodCategoryViewController = storyboard.instantiateViewController(withIdentifier: "FoodCategoryViewController") as? FoodCategoryViewController
        if let foodCategoryViewController = foodCategoryViewController {
            addChild(foodCategoryViewController)
            let foodCategoryView = foodCategoryViewController.view
            view.addSubview(foodCategoryView!)
            setTabViewFrame(metrics:ViewFrame(originY: UIScreen.main.bounds.height, height: 0), view: foodCategoryView!)
        }
    }

    private func popUpFoodCategory() {
        visualEffect.isHidden = false

        attachChild()

        UIView.animate(withDuration: 1,
                       delay: 0,
                       usingSpringWithDamping: 2.0,
                       initialSpringVelocity: 3,
                       options: .curveEaseInOut,
                       animations: {
                        self.setTabViewFrame(metrics:ViewFrame(originY: UIScreen.main.bounds.height*0.5,
                                                               height: UIScreen.main.bounds.height*0.5),
                                             view: self.foodCategoryViewController?.view ?? UIView())
                       }, completion: nil)


    }

    private func setTabViewFrame (metrics: ViewFrame?, view: UIView) {
        if let unwrappedMetrics = metrics {
            view.frame = CGRect(x: 0,
                                y: unwrappedMetrics.originY,
                                width: view.frame.width,
                                height: unwrappedMetrics.height)
        }
    }

    private func setupVisualEffectView() {
        let blurEffect = UIBlurEffect(style: .regular)
        visualEffect.effect = blurEffect
        visualEffect.frame = view.frame
        view.addSubview(visualEffect)
        visualEffect.isHidden = true
    }

    func setupObserver() {
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillShow(_:)), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillHide(_:)), name: UIResponder.keyboardWillHideNotification, object: nil)
    }

    func setupDelegate() {
        openChatTextField?.delegate = self
        menuTextField?.delegate = self
        titleTextField?.delegate = self
        pickerTextField.delegate = self
        datePickerTextField.delegate = self
    }

    func createPickerView() {
        pickerView.delegate = self
        pickerView.dataSource = self
        pickerTextField.inputView = pickerView
    }

    func createDatePicker() {
        datePickerView.datePickerMode = .dateAndTime
        datePickerView.locale = Locale(identifier: "ko-Kore_KR")
        datePickerView.minimumDate = Date()
        if #available(iOS 13.4, *) {
            datePickerView.preferredDatePickerStyle = .wheels
        }
    }

    func dismissDatePicker() {
        let toolBar = UIToolbar()
        toolBar.sizeToFit()
        let button = UIBarButtonItem(barButtonSystemItem: .done, target: nil, action: #selector(endDatePicker))
        toolBar.setItems([button], animated: false)
        datePickerTextField.inputAccessoryView = toolBar
        datePickerTextField.inputView = datePickerView
    }

    func dismissPickerView() {
        let toolBar = UIToolbar()
        toolBar.sizeToFit()
        let button = UIBarButtonItem(barButtonSystemItem: .done, target: nil, action: #selector(endPicker))
        toolBar.setItems([button], animated: true)
        toolBar.isUserInteractionEnabled = true
        pickerTextField.inputAccessoryView = toolBar
    }

    @objc func endPicker() {
        let selectedItem = numOfPeople[pickerView.selectedRow(inComponent: 0)]
        numOfPeopleButton?.setTitle(selectedItem, for: .selected)
        numOfPeopleButton?.titleLabel?.text = "\(selectedItem)"
        numOfPeopleButton?.isSelected = true
        pickerTextField.resignFirstResponder()
    }

    @objc func endDatePicker() {
        let dateFormatter = DateFormatter()
        let dateFormatterForApi = DateFormatter()
        dateFormatter.dateFormat = "YYYY/MM/dd a hh:mm"
        dateFormatterForApi.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        let formattedDateString = dateFormatter.string(from: datePickerView.date)
        orderTimeForApi = dateFormatterForApi.string(from: datePickerView.date)
        orderTimeButton?.setTitle(formattedDateString, for: .selected)

        datePickerTextField.resignFirstResponder()
        orderTimeButton?.isSelected = true
    }

    @IBAction func feeButtonClicked(_ sender: UIButton) {
        for button in deliveryButtons {
            button.isSelected = false
        }
        sender.isSelected = !sender.isSelected
    }

    @IBAction func locationButtonTapped(_ sender: UIButton) {
        for button in locationButtons {
            button.isSelected = false
        }
        sender.isSelected = !sender.isSelected
    }
    @IBAction func orderTimeButtonTapped(_ sender: Any) {
        datePickerTextField.becomeFirstResponder()
    }

    @IBAction func numOfButtonTapped(_ sender: Any) {
        pickerTextField.becomeFirstResponder()

    }

    @objc func keyboardWillShow(_ sender: UITextView) {
        if contentTextView!.isFirstResponder {
            let bottomOffset = CGPoint(x: 0, y: scrollView!.contentSize.height - scrollView!.bounds.height + scrollView!.contentInset.bottom)
            scrollView!.setContentOffset(bottomOffset, animated: true)
        }
    }

    @objc func keyboardWillHide(_ sender: Any) {
        if contentTextView!.isFirstResponder {
            scrollView!.setContentOffset(CGPoint(x: 0, y: 0), animated: true)
        }
    }
    @IBAction func tappedCompleteButton(_ sender: Any) {
        let title = titleTextField?.text
        let link = linkTextField?.text
        let openChatLink = openChatTextField?.text
        let numOfPeople = numOfPeopleButton?.titleLabel?.text?.dropLast()
        let contents = contentTextView?.text
        let deliveryFee = selectedDeliveryFeeButton()
        let location = selectedLocationButton()

        postFeedService.postUserInfo(category: Int(menu)!, chatLink: openChatLink, deliveryFeeByHost: deliveryFee, description: contents!, foodLink: link, meetPlace: location, memberCountLimit: Int(numOfPeople!)!, orderTime: orderTimeForApi, title: title!) { [weak self] result in
            switch result {
            case .success(let data):
                DispatchQueue.main.async {
                    self?.dismiss(animated: true, completion: nil)
                    print(data)
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }

    }

    private func selectedDeliveryFeeButton() -> Int {
        for button in deliveryButtons {
            if button.isSelected {
                return button.tag
            }
        }
        return 0
    }

    private func selectedLocationButton() -> Int {
        for button in locationButtons {
            if button.isSelected {
                return button.tag
            }
        }
        return 0
    }

}

extension WritingViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.endEditing(true)
        return true
    }
}

extension WritingViewController: UIPickerViewDelegate, UIPickerViewDataSource {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        1
    }

    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        numOfPeople.count
    }

    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        numOfPeople[safe: row]
    }

    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
    }
}



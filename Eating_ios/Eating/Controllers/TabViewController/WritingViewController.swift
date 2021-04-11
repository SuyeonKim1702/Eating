//
//  WritingViewController.swift
//  Eating
//
//  Created by 코드잉 on 2021/03/16.
//

import UIKit

class WritingViewController: UIViewController {

    @IBOutlet var outerView: UIView!
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
    let floatingButton = UIButton()
    let pickerView = UIPickerView()
    let datePickerView = UIDatePicker()
    let visualEffect = UIVisualEffectView()
    let pickerTextField = UITextField(frame: .zero)
    let datePickerTextField = UITextField(frame: .zero)
    weak var foodCategoryViewController: FoodCategoryViewController?
    let numOfPeople = ["2명", "3명", "4명"]
    var deliveryButtons =  [UIButton]()
    var locationButtons = [UIButton]()
    var menu: String?
    var orderTimeForApi: String?
    let postFeedService = PostFeedService()
    var feedDetail: FeedDetail?
    var index = Int()

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
        outerView?.layer.cornerRadius = Constant.buttonConnerRadius
        contentTextView?.textColor = .lightGray
        contentTextView?.text = "잇팅하면서 원하는 세부사항을 적어주세요~! Ex) 제가 있는 곳으로 와주셨으면 좋겠어요"
        pickerTextField.isHidden = true
        datePickerTextField.isHidden = true
        view.addSubview(pickerTextField)
        view.addSubview(datePickerTextField)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        attachFloatingButton()
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        floatingButton.removeFromSuperview()
    }

    func setupModifyValue(for feed: FeedDetail) {
        titleTextField?.text = feed.title
        linkTextField?.text = feed.foodLink
        openChatTextField?.text = feed.chatLink
        deliveryButtons[safe: feed.deliveryFeeByHost]?.isSelected = true
        locationButtons[safe: feed.meetPlace]?.isSelected = true
        contentTextView?.text = feed.description
        orderTimeForApi = feed.orderTime
        setDatePicker(date: feed.orderTime)
        numOfPeopleButton?.setTitle("\(feed.memberCountLimit)명", for: .normal)
        numOfPeopleButton?.isSelected = true
    }

    private func attachFloatingButton() {

        guard let keyWindow = UIApplication.shared.keyWindow else { return }
        keyWindow.addSubview(floatingButton)

        floatingButton.isUserInteractionEnabled = true
        floatingButton.translatesAutoresizingMaskIntoConstraints = false
        floatingButton.setImage(UIImage(named: "checkButton"), for: .normal)
        floatingButton.rightAnchor.constraint(equalTo: keyWindow.rightAnchor, constant: -15).isActive = true
        floatingButton.bottomAnchor.constraint(equalTo: keyWindow.bottomAnchor, constant: -60).isActive = true
        floatingButton.layer.shadowRadius = 5
        floatingButton.layer.shadowColor = UIColor(rgb: 0x000000, alpha: 29).cgColor
        floatingButton.layer.shadowOffset = CGSize(width: 0, height: 3)
        floatingButton.layer.shadowOpacity = 0.23

        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(tappedCompleteButton))
        floatingButton.addGestureRecognizer(tapGesture)
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
        deliveryButtons = [equalFeeButton!, allFeeButton!, noFeeButton!]
        locationButtons = [hostLocationButton!, middleLocationButton!,notDefiendLocationButton!]
    }

    private func setupTextFieldReturnKeyType() {
        titleTextField?.returnKeyType = .next
        menuTextField?.returnKeyType = .next
        openChatTextField?.returnKeyType = .next
        linkTextField?.returnKeyType = .next
    }

    @objc private func hideKeyboard(_ sender: Any) {
        contentTextView?.endEditing(true)
    }

    @IBAction func goBackButton(_ sender: Any) {
        floatingButton.isHidden = true
        guard let alertViewController = storyboard?.instantiateViewController(withIdentifier: "WritingAlertViewController") as? WritingAlertViewController else { return }
        alertViewController.writingViewController = self
        alertViewController.modalPresentationStyle = .overCurrentContext
        present(alertViewController, animated: true, completion: nil)
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
        floatingButton.isHidden = true
        openChatTextField?.resignFirstResponder()
        titleTextField?.resignFirstResponder()
        linkTextField?.resignFirstResponder()
        contentTextView?.resignFirstResponder()
        visualEffect.isHidden = false
        attachChild()

        UIView.animate(withDuration: 1,
                       delay: 0,
                       usingSpringWithDamping: 2.0,
                       initialSpringVelocity: 3,
                       options: .curveEaseInOut,
                       animations: {
                        self.setTabViewFrame(metrics:ViewFrame(originY: UIScreen.main.bounds.height*0.4,
                                                               height: UIScreen.main.bounds.height*0.6),
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
        contentTextView?.delegate = self
    }

    func createPickerView() {
        pickerView.delegate = self
        pickerView.dataSource = self
        pickerTextField.inputView = pickerView
    }

    func createDatePicker() {
        datePickerView.datePickerMode = .dateAndTime
        datePickerView.locale = Locale(identifier: "ko-Kore_KR")
        datePickerView.minimumDate = Calendar.current.date(byAdding: .minute, value: 5, to: Date())
        if #available(iOS 13.4, *) {
            datePickerView.preferredDatePickerStyle = .wheels
        } else {
            
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
        dateFormatterForApi.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"
        let formattedDateString = dateFormatter.string(from: datePickerView.date)
        orderTimeForApi = dateFormatterForApi.string(from: datePickerView.date)
        orderTimeButton?.setTitle(formattedDateString, for: .selected)

        datePickerTextField.resignFirstResponder()
        orderTimeButton?.isSelected = true
    }

    func setDatePicker(date: String) {
        let dateFormatter = DateFormatter()
        let dateFormatterForApi = DateFormatter()
        dateFormatter.dateFormat = "YYYY/MM/dd a hh:mm"
        dateFormatterForApi.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        let convertedDate = dateFormatterForApi.date(from: date)
        let formattedDateString = dateFormatter.string(from: convertedDate!)
        orderTimeButton?.setTitle(formattedDateString, for: .selected)
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

        if let title = titleTextField?.text, let link = linkTextField?.text, let openChatLink = openChatTextField?.text,
           title.count > 0, openChatLink.count > 0,
           let numOfPeople = Int(numOfPeopleButton?.titleLabel?.text?.dropLast() ?? ""), let contents = contentTextView?.text, let menuString = menu, let menu = Int(menuString), let orderTimeForApi = orderTimeForApi,
           let deliveryFee = selectedDeliveryFeeButton(), let location = selectedLocationButton() {
            print(numOfPeople)

            if presentingViewController as? WritingUpdateViewController != nil {
                postFeedService.updatePost(index: index, category: menu, chatLink: openChatLink, deliveryFeeByHost: deliveryFee, description: contents, foodLink: link, meetPlace: location, memberCountLimit: numOfPeople, orderTime: orderTimeForApi, title: title) { [weak self] result in
                    switch result {
                    case .success( _):
                        DispatchQueue.main.async {
                            self?.dismiss(animated: true, completion: nil)
                        }
                    case .failure(let error):
                        print(error.localizedDescription)
                    }
                }
            } else {
                postFeedService.postUserInfo(category: menu, chatLink: openChatLink, deliveryFeeByHost: deliveryFee, description: contents, foodLink: link, meetPlace: location, memberCountLimit: numOfPeople, orderTime: orderTimeForApi, title: title) { [weak self] result in
                    switch result {
                    case .success( _):
                        DispatchQueue.main.async {
                            self?.dismiss(animated: true, completion: nil)
                        }
                    case .failure(let error):
                        print(error.localizedDescription)
                    }
                }
            }

        } else {
            floatingButton.isHidden = true
            guard let alertViewController = storyboard?.instantiateViewController(withIdentifier: "FillAlertViewController") as? FillAlertViewController else { return }
            alertViewController.modalPresentationStyle = .overCurrentContext
            present(alertViewController, animated: true, completion: nil)
        }
    }

    private func selectedDeliveryFeeButton() -> Int? {
        for button in deliveryButtons {
            if button.isSelected {
                return button.tag
            }
        }
        return nil
    }

    private func selectedLocationButton() -> Int? {
        for button in locationButtons {
            if button.isSelected {
                return button.tag
            }
        }
        return nil
    }

}

extension WritingViewController: UITextFieldDelegate, UITextViewDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.endEditing(true)
        return true
    }

    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.textColor == UIColor.lightGray {
            textView.text = nil
            textView.textColor = UIColor.black
        }
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



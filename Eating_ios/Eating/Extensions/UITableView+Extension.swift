
import UIKit

extension UITableView {

    func setEmptyView() {
        let emptyView = UIView(frame: CGRect(x: self.center.x, y: self.center.y, width: self.bounds.size.width, height: self.bounds.size.height))

        let imageView = UIImageView(image:UIImage(named: "noFeed"))
        imageView.contentMode = .center

        imageView.translatesAutoresizingMaskIntoConstraints = false
        
        emptyView.addSubview(imageView)
        imageView.center = emptyView.center

        emptyView.centerYAnchor.constraint(equalTo: emptyView.centerYAnchor).isActive = true
        emptyView.centerXAnchor.constraint(equalTo: emptyView.centerXAnchor).isActive = true

        imageView.centerXAnchor.constraint(equalTo: emptyView.centerXAnchor).isActive = true
        imageView.centerYAnchor.constraint(equalTo: emptyView.centerYAnchor).isActive = true


        // The only tricky part is here:
        self.backgroundView = emptyView
        self.separatorStyle = .none
    }
    func restore() {
        self.backgroundView = nil
        self.separatorStyle = .none
    }
}

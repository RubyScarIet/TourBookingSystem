/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Main.Main;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomerForm extends JFrame {
    public CustomerForm() {
        setTitle("Nhập thông tin khách hàng");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblName = new JLabel("Họ và tên:");
        JTextField txtName = new JTextField();

        JLabel lblBirthday = new JLabel("Ngày sinh:");
        JTextField txtBirthday = new JTextField();

        JLabel lblPhone = new JLabel("Số điện thoại:");
        JTextField txtPhone = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();
        
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        
//      Tạo Customer với các chuỗi rỗng hoặc số là 0
//      Vd: Customer cus = new Customer("", "", 0.0, ...)
        JButton btnSubmit = new JButton("Nhập");
        btnSubmit.addActionListener(e -> {
            String name = txtName.getText();
            String birthday = txtBirthday.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
//      Set các giá trị cho Customer
            if (name.isEmpty() || birthday.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                statusLabel.setText("<html>⚠️ Vui lòng nhập đầy đủ <br>thông tin!</html>");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!Main.truePhoneNumber(phone)) {
                statusLabel.setText("❌ Số điện thoại không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!email.contains("@gmail.com") && !email.contains("@yahoo.com")) {
                statusLabel.setText("❌ Email không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            }
//      Truyền Object vào
//      new TourWindow(cus);
            new TourWindow(name, birthday, phone, email);
        });

        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblBirthday);
        panel.add(txtBirthday);
        panel.add(lblPhone);
        panel.add(txtPhone);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(statusLabel);
        panel.add(btnSubmit);
        
        setContentPane(panel);
        setVisible(true);
    }
}

class TourWindow extends JFrame {
    public TourWindow(String name, String birthday, String phone, String email) {
        setTitle("Đăng ký tour");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTour = new JLabel("Tên tour:");
        String[] tours = { "Hà Nội - HCM", "Hà Nội - Đà Nẵng", "Hà Nội - Nha Trang" };
        JComboBox<String> cbTour = new JComboBox<>(tours);

        JLabel lblDate = new JLabel("Ngày khởi hành:");
        String[] dates = { "20/10/2025", "23/12/2025", "01/01/2026" };
        JComboBox<String> cbDate = new JComboBox<>(dates);

        JLabel lblNumberOfPeople = new JLabel("Số người đi:");
        Integer[] number = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        JComboBox<Integer> cbNumber = new JComboBox<>(number);
        
        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String tour = (String) cbTour.getSelectedItem();
            String date = (String) cbDate.getSelectedItem();
            String numPeople = cbNumber.getSelectedItem().toString();

            JOptionPane.showMessageDialog(this,
                    "Họ và tên: " + name +
                            "\nNgày sinh: " + birthday +
                            "\nSố điện thoại: " + phone +
                            "\nEmail: " + email + 
                            "\nTên tour: " + tour +
                            "\nNgày khởi hành: " + date +
                            "\nSố người đi: " + numPeople + " người",
                    "Xác nhận đặt tour",
                    JOptionPane.INFORMATION_MESSAGE);
            
//            Từ tour (tên) với date tìm ra tourId 
//          Có tourId tìm price/người (giá một người, đây không phải phép chia) -> tổng price = price/người *số người
//          Gán Object. cus.setTourBooking(tourId) ....
        });

        JButton btnPay = new JButton("Thanh toán");
        btnPay.addActionListener(e -> {
            int n = (int) cbNumber.getSelectedItem();
//          Khi click nút này sẽ đưa Object Customer vào
//          new XacNhanThanhToan(cus)
            new XacNhanThanhToan(name, phone, n);
        });
        panel.add(lblTour);
        panel.add(cbTour);
        panel.add(lblDate);
        panel.add(cbDate);
        panel.add(lblNumberOfPeople);
        panel.add(cbNumber);
        panel.add(btnConfirm);
        panel.add(btnPay);

        setContentPane(panel);
        setVisible(true);
    }
}

class XacNhanThanhToan extends JFrame {
    public XacNhanThanhToan(String name, String phone, int n) {
        setTitle("Xác nhận thanh toán");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        double pricePerPerson = 1000000; 
        double total = pricePerPerson * n;  //Gán cho total = cus.getPrice()

        JLabel lblTotal = new JLabel("Tổng tiền thanh toán: ");
        JLabel lblTotalAmount = new JLabel(String.format("%.2f VND", total));

        JLabel lblInfo = new JLabel("Nhập mã xác minh: ");
        JTextField txtCode = new JTextField(); //Mã xác minh bằng Id + số người đăng ký (chuyển về chuỗi)
        
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        
        JButton btnNotPay = new JButton("Để sau");
        btnNotPay.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Xác nhận chưa thanh toán");
            dispose();
        });
        JButton btnPay = new JButton("Xác nhận thanh toán");
        btnPay.addActionListener(e -> {
            String code = txtCode.getText();
            if (!code.equals(phone + String.format("%.2f", total))) {
                statusLabel.setText("❌ Mã xác nhận không dúng");
                statusLabel.setForeground(Color.RED);
                return;
            }
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!\nCảm ơn bạn đã sử dụng dịch vụ.");
            dispose();
        });

        panel.add(lblTotal);
        panel.add(lblTotalAmount);
        panel.add(lblInfo);
        panel.add(txtCode);
        panel.add(statusLabel);
        panel.add(new JLabel());
        panel.add(btnNotPay);
        panel.add(btnPay);
        
        
        setContentPane(panel);
        setVisible(true);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Tour;
import DAO.TourDAO; 
import Main.Main;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
public class ManagerForm extends JFrame {
    public ManagerForm() {
        setTitle("Xác minh quản lý");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblXacMinh = new JLabel("Mã xác minh:");
        JPasswordField txtXacMinh = new JPasswordField();
        
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        
        JCheckBox showPass = new JCheckBox("Hiện mật khẩu");
        showPass.addActionListener(e -> {
            if (showPass.isSelected()) {
                txtXacMinh.setEchoChar((char) 0); // hiện mật khẩu
            } else {
                txtXacMinh.setEchoChar('*'); // ẩn mật khẩu
            }
        });

        JButton btnSubmit = new JButton("Xác nhận");
        btnSubmit.addActionListener(e -> {
            String code = new String(txtXacMinh.getPassword());
            if (code.equals("iammanager")) {
                new TourInput();
                dispose();
            } else {
                statusLabel.setText("❌️ Mã xác minh không đúng!");
                statusLabel.setForeground(Color.RED);
            }
        });

        panel.add(lblXacMinh);
        panel.add(txtXacMinh);
        panel.add(showPass);
        panel.add(statusLabel);
        panel.add(new JLabel());
        panel.add(btnSubmit);
        
        setContentPane(panel);
        setVisible(true);
    }
}

class TourInput extends JFrame {
    public TourInput() {
        setTitle("Nhập thông tin chuyến đi");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblStart = new JLabel("Điểm xuất phát:");
        JTextField txtStart = new JTextField();

        JLabel lblDestination = new JLabel("Điểm đến:");
        JTextField txtDestination = new JTextField();

        JLabel lblDayStart = new JLabel("Ngày khởi hành:");
        JTextField txtDayStart = new JTextField();

        JLabel lblNumberOfDays = new JLabel("Số ngày (dạng số, vd: 2.5):");
        JTextField txtNumberOfDays = new JTextField();

        JLabel lblPrice = new JLabel("Giá/người:");
        JTextField txtPrice = new JTextField();

        JLabel lblNumberOfPassengers = new JLabel("Số khách tối đa:");
        JTextField txtNumberOfPassengers = new JTextField();

        JLabel lblNumberOfGuides = new JLabel("Số hướng dẫn viên cần:");
        JTextField txtNumberOfGuides = new JTextField();
        
        JLabel lblGuideCondition = new JLabel("Điều kiện hướng dẫn viên:");
        String[] languages = { "Tiếng Anh", "Tiếng Hàn", "Tiếng Trung", "Tiếng Nhật" };
        JComboBox<String> cbLanguage = new JComboBox<>(languages);
        
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        
        Tour tour = new Tour();
        JButton btnSubmit = new JButton("Nhập");
        btnSubmit.addActionListener(e -> {
            String tourName = txtStart.getText() + " - " + txtDestination.getText();
            String start = txtStart.getText();
            String destination = txtDestination.getText();
            String dayStart = txtDayStart.getText();
            String numberOfDaysStr = txtNumberOfDays.getText();
            String numberOfPassengersStr = txtNumberOfPassengers.getText();
            String numberOfGuidesStr = txtNumberOfGuides.getText();
            String price = txtPrice.getText();
            String guideCondition = cbLanguage.getSelectedItem().toString();

            try {
                // chuyển đổi các giá trị số
                float daysValue = Float.parseFloat(numberOfDaysStr);
                double priceValue = Double.parseDouble(price);
                int passengersValue = Integer.parseInt(numberOfPassengersStr);
                int guidesValue = Integer.parseInt(numberOfGuidesStr);

                // các hàm 'set' để gán giá trị
                tour.setTourName(tourName);
                tour.setStartFrom(start);
                tour.setDestination(destination);
                tour.setDayStart(dayStart);
                tour.setNumberOfDays(daysValue);
                tour.setPrice(priceValue);
                tour.setNumberOfPassengers(passengersValue);
                tour.setNumberOfGuides(guidesValue);
                tour.setLanguageGuideNeed(guideCondition);

                // lưu vào database
                TourDAO tourDAO = new TourDAO();
                tourDAO.addTour(tour);
                
                // cập nhật trạng thái thành công
                statusLabel.setText("✅ Thêm tour thành công!");
                statusLabel.setForeground(Color.GREEN);
                
                // hiển thị lại thông tin tour đã tạo
                 JOptionPane.showMessageDialog(this,
                    "Tour : " + tourName + "\nGiá: " + price + " VND / người\n" + "Thời gian: " + numberOfDaysStr +
                    "\nSố lượng hành khách: " + numberOfPassengersStr + " người" + "\nSố lượng hướng dẫn viên: "
                    + numberOfGuidesStr + " người"
                    + "\nĐiều kiện hướng dẫn viên: " + guideCondition,
                    "Thông tin chuyến đi", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                statusLabel.setText("❌️ Lỗi định dạng số!");
                statusLabel.setForeground(Color.RED);
            } catch (SQLException ex) {
                statusLabel.setText("❌️ Lỗi Database!");
                statusLabel.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, "Lỗi Database: " + ex.getMessage());
            } catch (Exception ex) {
                statusLabel.setText("❌️ Lỗi không xác định!");
                statusLabel.setForeground(Color.RED);
            }
            
            if (tourName.isEmpty() || price.isEmpty() || numberOfDaysStr.isEmpty() || dayStart.isEmpty()
                    || numberOfPassengersStr.isEmpty() || numberOfGuidesStr.isEmpty()) {
                statusLabel.setText("⚠️ Vui lòng nhập đầy đủ thông tin!");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!Main.isDouble(price)) {
                statusLabel.setText("❌️ Giá không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!Main.isInteger(numberOfPassengersStr)) {
                statusLabel.setText("❌️ Số hành khách tối đa không hợp lệ");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!Main.isInteger(numberOfGuidesStr)) {
                statusLabel.setText("❌️ Số hướng dẫn viên không hợp lệ");
                statusLabel.setForeground(Color.RED);
                return;
            }
            else {
                // statusLabel.setText("✅ Đăng ký thành công!");
                // statusLabel.setForeground(Color.GREEN);
            }
            
            /*
            JOptionPane.showMessageDialog(this,
                    "Tour : " + tourName + "\nGiá: " + price + " VND / người\n" + "Thời gian: " + numberOfDaysStr +
                            "\nSố lượng hành khách: " + numberOfPassengersStr + " người" + "\nSố lượng hướng dẫn viên: "
                            + numberOfGuidesStr + " người"
                            + "\nĐiều kiện hướng dẫn viên: " + guideCondition,
                    "Thông tin chuyến đi", JOptionPane.INFORMATION_MESSAGE);
            */
        });
        
        panel.add(lblStart);
        panel.add(txtStart);
        panel.add(lblDestination);
        panel.add(txtDestination);
        panel.add(lblDayStart);
        panel.add(txtDayStart);
        panel.add(lblNumberOfDays);
        panel.add(txtNumberOfDays);
        panel.add(lblPrice);
        panel.add(txtPrice);
        panel.add(lblNumberOfPassengers);
        panel.add(txtNumberOfPassengers);
        panel.add(lblNumberOfGuides);
        panel.add(txtNumberOfGuides);
        panel.add(lblGuideCondition);
        panel.add(cbLanguage);
        panel.add(statusLabel);
        panel.add(btnSubmit);
        
        setContentPane(panel);
        setVisible(true);
    }
}
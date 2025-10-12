/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import Main.Main;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GuideForm extends JFrame {
    public GuideForm() {
        setTitle("Xác minh hướng dẫn viên");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblXacMinh = new JLabel("Mã xác minh:");
        JPasswordField txtXacMinh = new JPasswordField();

        JCheckBox showPass = new JCheckBox("Hiện mật khẩu");
        showPass.addActionListener(e -> {
            if (showPass.isSelected()) {
                txtXacMinh.setEchoChar((char) 0); // hiện mật khẩu
            } else {
                txtXacMinh.setEchoChar('*'); // ẩn mật khẩu
            }
        });
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        
        JButton btnSubmit = new JButton("Xác nhận");
        btnSubmit.addActionListener(e -> {
            String code = new String(txtXacMinh.getPassword());
            if (code.equals("guide123")) {
                new GuideSignIn();
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

class GuideSignIn extends JFrame {
    public GuideSignIn() {
        setTitle("Thông tin hướng dẫn viên");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblName = new JLabel("Họ và tên:");
        JTextField txtName = new JTextField();

        JLabel lblBirthday = new JLabel("Ngày sinh:");
        JTextField txtBirthday = new JTextField();

        JLabel lblPhone = new JLabel("Số điện thoại:");
        JTextField txtPhone = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();

        JLabel lblExperience = new JLabel("Kinh nghiệm (năm):");
        JTextField txtExperience = new JTextField();

        JLabel lblLanguages = new JLabel("<html>Ngoại ngữ <br> (Giữ Ctrl để chọn nhiểu ngôn ngữ): </html>");
        JList<String> listLangs = new JList<>(new String[] { "Tiếng Anh", "Tiếng Trung", "Tiếng Nhật", "Tiếng Hàn" });
        listLangs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listLangs);
        scrollPane.setPreferredSize(new Dimension(120, 80));
        
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
//        Tạo Object Guide
//        Các thông số về tour để chuỗi rỗng
        JButton btnSubmit = new JButton("Tiếp tục");
        btnSubmit.addActionListener(e -> {
            String name = txtName.getText();
            String birthday = txtBirthday.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String exp = txtExperience.getText();
            StringBuilder langs = new StringBuilder();
            for (String lang : listLangs.getSelectedValuesList()) {
                if (langs.length() > 0)
                    langs.append(", ");
                langs.append(lang);
            }
//            Gán các giá trị như name, birthday, phone, ...
            if (name.isEmpty() || birthday.isEmpty() || phone.isEmpty()
                    || email.isEmpty() || exp.isEmpty() || langs.isEmpty()) {
                statusLabel.setText("<html>⚠️ Vui lòng nhập đầy đủ <br>thông tin!</html>");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!Main.truePhoneNumber(phone)) {
                statusLabel.setText("❌ Số điện thoại không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!email.endsWith("@gmail.com")) {
                statusLabel.setText("❌ Email không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            } else if (!Main.isDouble(exp) || Double.parseDouble(exp) < 0) {
                statusLabel.setText("❌ Kinh nghiệm không hợp lệ!");
                statusLabel.setForeground(Color.RED);
                return;
            }
//            Đoạn này sẽ đưa object Guide vào thay vì các giá trị rời rạc
//              new BookinTour(Guide);
            new BookingTour(name, birthday, phone, email, exp, langs.toString());
        });

        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblBirthday);
        panel.add(txtBirthday);
        panel.add(lblPhone);
        panel.add(txtPhone);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblExperience);
        panel.add(txtExperience);

        panel.add(lblLanguages);
        panel.add(scrollPane);

        panel.add(statusLabel);
        panel.add(btnSubmit);

        setContentPane(panel);
        setVisible(true);
    }
}

class BookingTour extends JFrame {
    public BookingTour(String name, String birthday, String phone, String email, String exp, String langs) {
        setTitle("Chọn tour dẫn");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTour = new JLabel("Tên tour:");
        String[] tours = { "Hà Nội - HCM", "Hà Nội - Đà Nẵng", "Hà Nội - Nha Trang" };
        JComboBox<String> cbTour = new JComboBox<>(tours);

        JLabel lblDate = new JLabel("Ngày khởi hành:");
        String[] dates = { "20/10/2025", "23/12/2025", "01/01/2026" };
        JComboBox<String> cbDate = new JComboBox<>(dates);
//        Từ tên và ngày khởi hành -> tourId 
//          Set giá trị cho TourBooking của Object Guide
//          Từ TourBooking(tourId) tìm điều kiện ngôn ngữ của Tour -> Check xem Hướng dẫn viên dủ điều kiện không

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.addActionListener(e -> {
            String tour = (String) cbTour.getSelectedItem();
            String date = (String) cbDate.getSelectedItem();

            JOptionPane.showMessageDialog(this,
                    "Họ và tên: " + name +
                            "\nNgày sinh: " + birthday +
                            "\nSố điện thoại: " + phone +
                            "\nEmail: " + email +
                            "\nKinh nghiệm: " + exp + " năm" +
                            "\nNgoại ngữ: " + langs +
                            "\nTên tour: " + tour +
                            "\nNgày khởi hành: " + date,
                    "Xác nhận dẫn tour",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(lblTour);
        panel.add(cbTour);
        panel.add(lblDate);
        panel.add(cbDate);
        panel.add(new JLabel());
        panel.add(btnConfirm);
        setContentPane(panel);
        setVisible(true);
    }
}
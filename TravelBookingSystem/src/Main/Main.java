package Main;
// Update these imports to match the actual package structure where CustomerForm, GuideForm, and ManagerForm are located.
// For example, if they are in 'view' (lowercase) package, use:
import View.CustomerForm;
import View.GuideForm;
import View.ManagerForm;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Trang chủ");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        String[] options = { "Khách hàng", "Hướng dẫn viên", "Quản lý" };
        JList<String> list = new JList<>(options);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        JButton btnOpen = new JButton("Mở");
        btnOpen.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected == null) {
                statusLabel.setText("⚠️ Vui lòng chọn mục trước!");
                statusLabel.setForeground(Color.RED);
                return;
            }
            switch (selected) {
                case "Khách hàng":
                    new CustomerForm();
                    statusLabel.setText("✅ Thành công!");
                    statusLabel.setForeground(Color.GREEN);
                    break;
                case "Hướng dẫn viên":
                    new GuideForm();
                    statusLabel.setText("✅ Thành công!");
                    statusLabel.setForeground(Color.GREEN);
                    break;
                case "Quản lý":
                    new ManagerForm();
                    statusLabel.setText("✅ Thành công!");
                    statusLabel.setForeground(Color.GREEN);
                    break;
            }
        });
        
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        bottomPanel.add(btnOpen, BorderLayout.CENTER);
        
        
        mainPanel.add(new JScrollPane(list), BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    // tiện ích dùng chung
    public static boolean isDouble(String str) {
        if (str == null || str.isEmpty())
            return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean truePhoneNumber(String str) {
        if (str.length() != 10 && str.length() != 9)
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty())
            return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
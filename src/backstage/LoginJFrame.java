package backstage;

import connect_database.CustomerAddingFunction;
import connect_database.CustomerSearchingFunction;
import connect_database.ManagerFunction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class LoginJFrame extends javax.swing.JFrame {

    static ArrayList<Customer> customers = new ArrayList<>();
    static Manager bankManager;

    /**
     * Creates new form NewJFrame
     */
    public LoginJFrame() {
        initComponents();
        getExistUser();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        managerLoginButton = new javax.swing.JButton();
        customerLoginButton = new javax.swing.JButton();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordTextField = new javax.swing.JTextField();
        usernameTextField = new javax.swing.JTextField();
        welcomeLabel = new javax.swing.JLabel();
        newuserButton = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        managerLoginButton.setText("Manager Login");
        managerLoginButton.setToolTipText("");
        managerLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managerLoginButtonActionPerformed(evt);
            }
        });

        customerLoginButton.setText("Customer Login");
        customerLoginButton.setToolTipText("");
        customerLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customerLoginButtonActionPerformed(evt);
            }
        });

        usernameLabel.setText("Username:");

        passwordLabel.setText("Password:");

        passwordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextFieldActionPerformed(evt);
            }
        });

        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });

        welcomeLabel.setText("Welcome to Bank System!");

        newuserButton.setText("New Customer");
        newuserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newuserButtonActionPerformed(evt);
            }
        });

        errorLabel.setText("                        ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(managerLoginButton)
                        .addGap(69, 69, 69)
                        .addComponent(customerLoginButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(newuserButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(passwordLabel)
                                    .addComponent(usernameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(welcomeLabel)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(errorLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(managerLoginButton)
                    .addComponent(customerLoginButton))
                .addGap(18, 18, 18)
                .addComponent(newuserButton)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void managerLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerLoginButtonActionPerformed
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        int login = ManagerFunction.managerLogin(username, password);
        if (login == 0) {
            bankManager = new Manager(username, password);
            bankManager.setCustomers(customers);
            new ManagerJFrame(bankManager);
        }
        else {
            errorLabel.setText("Wrong name or password.");
        }

    }//GEN-LAST:event_managerLoginButtonActionPerformed

    private void customerLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerLoginButtonActionPerformed
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (validInput(username, password)) {
            int login = CustomerSearchingFunction.customerLogin(username,
                                                                password);
            if (login == -1) {
                errorLabel.setText("Wrong login.");
            }
            else {
                int index = -1;
                for (int i = 0; i < customers.size(); i++) {
                    if (customers.get(i).id == login) {
                        index = i;
                    }
                }
                new CustomerJFrame(customers.get(index));
            }
        }
    }//GEN-LAST:event_customerLoginButtonActionPerformed

    private void passwordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTextFieldActionPerformed

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void newuserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newuserButtonActionPerformed
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (validInput(username, password)) {
            this.dispose();
            Customer customer = new Customer(username, password);
            //new customer in database
            int cid = CustomerAddingFunction.addCustomer(customer.name,
                                                         customer.pwd);
            customers.add(customer);
            customer.setId(cid); //cid is the index of this customer in arraylist
            new CustomerJFrame(customers.get(cid));
        }
    }//GEN-LAST:event_newuserButtonActionPerformed

    public boolean validInput(String username, String password) {
        if (username.length() < 1) {
            errorLabel.setText("Username should not be empty.");
            return false;
        }
        else if (!Tool.is_alpha(username)) {
            errorLabel.setText("Username should consist of letters.");
            return false;
        }
        else if (!Tool.in_range(password, 6, 16)) {
            errorLabel.setText("Password should be between 6-16.");
            return false;
        }
        return true;
    }

    public static void getExistUser() {
        List<String[]> existcustomers = ManagerFunction.searchAllCustomer();

        //id loop
        for (int i = 0; i < Objects.requireNonNull(existcustomers).size(); i++) {
            Customer customer = new Customer(Integer.parseInt(
                    existcustomers.get(i)[0]));
            customer.name = existcustomers.get(i)[1];
            customers.add(customer);
        }
        if (customers.size() > 0) {
            //currency loop
            for (int i = 0; i < customers.size(); i++) {
                if (CustomerSearchingFunction.searchSavingMoneyAmount(
                        customers.get(i).id, "Dollar") != null) {
                    customers.get(i).currency.getMoney().put("Dollar",
                                                             CustomerSearchingFunction.searchSavingMoneyAmount(
                                                                     customers.get(
                                                                             i).id,
                                                                     "Dollar"));
                }
                if (CustomerSearchingFunction.searchSavingMoneyAmount(
                        customers.get(i).id, "RMB") != null) {
                    customers.get(i).currency.getMoney().put("RMB",
                                                             CustomerSearchingFunction.searchSavingMoneyAmount(
                                                                     customers.get(
                                                                             i).id,
                                                                     "RMB"));
                }
                if (CustomerSearchingFunction.searchSavingMoneyAmount(
                        customers.get(i).id, "Pound") != null) {
                    customers.get(i).currency.getMoney().put("Pound",
                                                             CustomerSearchingFunction.searchSavingMoneyAmount(
                                                                     customers.get(
                                                                             i).id,
                                                                     "Pound"));
                }
            }
            //account loop
            List<String[]> existaccounts = ManagerFunction.searchAllCustomerAccount();
            //{customer_ID, customer_name, account_type, currency_type, money_amount}
            for (int i = 0; i < customers.size(); i++) {
                int sc = 0, cc = 0, lc = 0;
                for (int j = 0; j < Objects.requireNonNull(existaccounts).size(); j++) {
                    if (customers.get(i).id == Integer.parseInt(
                            existaccounts.get(j)[0])) {
                        if (existaccounts.get(j)[2].equals("SAVING") && sc == 0) {
                            SavingAccount savingAccount = new SavingAccount(
                                    customers.get(i).id,
                                    customers.get(i).currency);
                            sc = 1;
                            customers.get(i).accounts.add(savingAccount);
                        }
                        if (existaccounts.get(j)[2].equals("CHECKING") && cc == 0) {
                            CheckingAccount checkingAccount = new CheckingAccount(
                                    customers.get(i).id,
                                    customers.get(i).currency);
                            cc = 1;
                            customers.get(i).accounts.add(checkingAccount);
                        }
                        if (existaccounts.get(j)[2].equals("LOAN") && lc == 0) {
                            LoanAccount loanAccount = new LoanAccount(
                                    customers.get(i).id,
                                    customers.get(i).currency);
                            lc = 1;
                            customers.get(i).accounts.add(loanAccount);
                        }
                    }
                }
            }

            //loan amount loop
            List<String[]> loanCustomer = ManagerFunction.searchAllLoanCustomer();
            //{customer_ID, customer_name, "LOAN", "Dollar", money_amount}
            for (int i = 0; i < customers.size(); i++) {
                int index = -1;
                for (int k = 0; k < customers.get(i).accounts.size(); k++) {
                    if (customers.get(i).accounts.get(k).accountType.equals(
                            "LOAN")) {
                        index = k;
                        break;
                    }
                }
                if (index >= 0) {
                    for (int j = 0; j < Objects.requireNonNull(loanCustomer).size(); j++) {
                        if (customers.get(i).id == Integer.parseInt(
                                loanCustomer.get(j)[0])) {
                            ((LoanAccount) customers.get(i).accounts.get(index)).setTotalloan(
                                    new BigDecimal(loanCustomer.get(j)[4]));
                        }
                    }
                }
            }
            //loan collaterals loop
            for (int i = 0; i < customers.size(); i++) {
                List<String[]> collaterallist = CustomerSearchingFunction.searchLoanList(
                        customers.get(i).id);
                // {loan_record_ID, loan_amount, collateral_name}
                if (collaterallist != null && collaterallist.size() > 0) {
                    for (int j = 0; j < collaterallist.size(); j++) {
                        //BigDecimal price=new BigDecimal(collaterallist.get(j)[1]);
                        //price=price.divide(new BigDecimal("0.9"));
                        Collateral collateral = new Collateral(
                                collaterallist.get(j)[2]);
                        customers.get(i).collaterals.add(collateral);
                    }
                    int index = -1;
                    for (int k = 0; k < customers.get(i).accounts.size(); k++) {
                        if (customers.get(i).accounts.get(k).accountType.equals(
                                "LOAN")) {
                            index = k;
                            break;
                        }
                    }
                    if (index >= 0) {
                        customers.get(i).accounts.get(index).collaterals = customers.get(
                                i).collaterals;
                    }
                }
            }
            //transactions loop
            //{account_type, currency_type, money_changed, current_balance, time}
            for (int i = 0; i < customers.size(); i++) {
                List<String[]> transactionlist = CustomerSearchingFunction.searchTransaction(
                        customers.get(i).id);
                for (int j = 0; j < Objects.requireNonNull(transactionlist).size(); j++) {
                    Transaction transaction = new Transaction(
                            "Details only for today's actions",
                            transactionlist.get(j)[0],
                            transactionlist.get(j)[1], transactionlist.get(j)[2],
                            transactionlist.get(j)[3], transactionlist.get(j)[4]);
                    customers.get(i).transactions.add(transaction);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginJFrame();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton customerLoginButton;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JButton managerLoginButton;
    private javax.swing.JButton newuserButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}

package backstage;

import connect_database.CustomerDeletingFunction;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class CustomerJFrame extends javax.swing.JFrame {

    private Customer customer;

    /**
     * Creates new form MainJFrame
     */
    public CustomerJFrame(Customer customer) {
        this.customer = customer;
        initComponents();
        setAccountList();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        accountList = new javax.swing.JList<>();
        checkingButton = new javax.swing.JButton();
        SavingButton = new javax.swing.JButton();
        LoanButton = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(accountList);

        checkingButton.setText("Check");
        checkingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkingButtonActionPerformed(evt);
            }
        });

        SavingButton.setText("Save/WithDraw");
        SavingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SavingButtonActionPerformed(evt);
            }
        });

        LoanButton.setText("Loan");
        LoanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoanButtonActionPerformed(evt);
            }
        });

        errorLabel.setText("        ");

        jLabel1.setText("Accounts:");

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Open Saving Account");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Stock");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Transaction Records");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("open saving/stock account($5) make a check($1) view transactions($10)");

        jLabel3.setText("stock account needs over $1000, with balance over $5000.");

        jButton4.setText("Open Stock Account");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Delete");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(refreshButton)
                                            .addComponent(jButton5))))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jLabel3))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(41, 41, 41)
                                .addComponent(SavingButton)
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(checkingButton)
                                .addGap(39, 39, 39)
                                .addComponent(jButton2)
                                .addGap(36, 36, 36)
                                .addComponent(LoanButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jButton4)
                                .addGap(54, 54, 54)
                                .addComponent(jButton3)
                                .addGap(54, 54, 54)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)))
                .addGap(18, 18, 18)
                .addComponent(errorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(SavingButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkingButton)
                    .addComponent(LoanButton)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkingButtonActionPerformed
        int exist = customer.isthereAccount("CHECKING");
        if (exist < 0) {
            //create new account
            int accountID = customer.createAccount("CHECKING");
            if (accountID >= 0) {
                new CheckJFrame((CheckingAccount) customer.accounts.get(
                        accountID), customer);
            }
            else {
                errorLabel.setText("Failed to create Saving. Need more than $5.");
            }
        }
        else {
            //account already exist
            new CheckJFrame((CheckingAccount) customer.accounts.get(exist),
                            customer);
        }
    }//GEN-LAST:event_checkingButtonActionPerformed

    private void SavingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SavingButtonActionPerformed
        int exist = customer.isthereAccount("SAVING");
        if (exist < 0) {
            errorLabel.setText("There is no saving account. Create a one first.");
        }
        else {
            new SaveJFrame((SavingAccount) customer.accounts.get(exist));
            this.setAccountList();
        }
    }//GEN-LAST:event_SavingButtonActionPerformed

    private void LoanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoanButtonActionPerformed
        int exist = customer.isthereAccount("LOAN");
        if (exist < 0) {
            int accountID = customer.createAccount("LOAN");
            if (accountID >= 0) {
                new LoanJFrame((LoanAccount) customer.accounts.get(
                        accountID));
            }
            else {
                errorLabel.setText("Failed to create Loan. Need more than $8.");
            }
        }
        else {
            new LoanJFrame((LoanAccount) customer.accounts.get(
                    exist));
        }
    }//GEN-LAST:event_LoanButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        this.setAccountList();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int exist = customer.isthereAccount("SAVING");
        if (exist < 0) {
            SavingAccount savingAccount = new SavingAccount(customer.id,
                                                            customer.currency);
            new OpenSavingJFrame(customer, savingAccount);
        }
        else {
            errorLabel.setText("There is already a saving account.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int exist = customer.isthereAccount("STOCK");
        if (exist < 0) {
            errorLabel.setText("There is no stock account. Create a one first.");
        }
        else {
            new StockJFrame((StockAccount) customer.accounts.get(exist));
            this.setAccountList();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        boolean success = customer.currency.sub("Dollar", 10, "1");
        if (!success) {
            errorLabel.setText("Failed to check records. Need more than $10.");
        }
        else {
            errorLabel.setText(
                    "Successfully check records with a charge of $10.");
            new CustomerTransactionJFrame(customer);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int exist = customer.isthereAccount("STOCK");
        if (exist < 0) {
            StockAccount stockAccount = new StockAccount(customer.id,
                                                         customer.currency);
            new OpenStockJFrame(customer, stockAccount);
        }
        else {
            errorLabel.setText("There is already a stock account.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int index = accountList.getSelectedIndex();
        if (index >= 0) {
            this.removeAccount(index);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    public void removeAccount(int index) {
        ArrayList<Account> accounts = customer.getAccounts();
        assert index <= accounts.size() - 1;
        if (accounts.get(index).accountType.equals("SAVING")) {
            boolean isempty = accounts.get(index).currency.is_empty();
            if (isempty) {
                boolean success = accounts.get(index).currency.sub("Dollar",
                                                                   5, "1");
                if (success) {
                    errorLabel.setText(
                            "Successfully delete your saving account!");
                    accounts.get(index).createTransaction("-5", "Dollar",
                                                          "Delete account.");
                    CustomerDeletingFunction.deleteSavingAccount(customer.id);
                    accounts.remove(index);
                }
                else {
                    errorLabel.setText("You don't have enough money.");
                    accounts.get(index).createTransaction("0", "Dollar",
                                                          "Failed to delete account.");
                }
            }
            else {
                errorLabel.setText(
                        "Please withdraw all your money first. (Leave 5 dollars for deleting account)");
                accounts.get(index).createTransaction("0", "Dollar",
                                                      "Failed to delete account.");
            }
        }
        else if (accounts.get(index).accountType.equals("CHECKING")) {
            boolean success = accounts.get(index).currency.sub("Dollar", 5, "1");
            if (success) {
                errorLabel.setText("Successfully delete your checking account.");
                accounts.get(index).createTransaction("-5", "Dollar",
                                                      "Delete account.");
                CustomerDeletingFunction.deleteCheckingAccount(customer.id);
                accounts.remove(index);
            }
            else {
                errorLabel.setText("You don't have enough money.");
                accounts.get(index).createTransaction("0", "Dollar",
                                                      "Failed to delete account.");
            }

        }
        else if (accounts.get(index).accountType.equals("LOAN")) {
            LoanAccount loanAccount = (LoanAccount) accounts.get(index);
            if (loanAccount.is_empty()) {
                boolean success = accounts.get(index).currency.sub("Dollar", 5,
                                                                   "1");
                if (success) {
                    errorLabel.setText("Successfully delete your loan account.");
                    accounts.get(index).createTransaction("-5", "Dollar",
                                                          "Delete account.");
                    CustomerDeletingFunction.deleteLoanAccountWithoutPayback(
                            customer.id);
                    accounts.remove(index);
                }
                else {
                    errorLabel.setText("You don't have enough money.");
                    accounts.get(index).createTransaction("0", "Dollar",
                                                          "Failed to delete account.");
                }
            }
            else {
                errorLabel.setText(
                        "Delete your loan account. Your loans are mine now.");
                accounts.get(index).createTransaction("0", "Dollar",
                                                      "Delete account without payback.");
                CustomerDeletingFunction.deleteLoanAccountWithoutPayback(
                        customer.id);
            }
        }
        this.setAccountList();
    }

    private void setAccountList() {
        ArrayList<String> strings = new ArrayList<String>();
        for (Account account : customer.getAccounts()) {
            strings.add(
                    "(" + account.getAccountType() + ")" + " " + account.toString());
        }
        accountList.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() {
                return strings.size();
            }

            public String getElementAt(int i) {
                return strings.get(i);
            }
        });
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
            java.util.logging.Logger.getLogger(CustomerJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LoanButton;
    private javax.swing.JButton SavingButton;
    private javax.swing.JList<String> accountList;
    private javax.swing.JButton checkingButton;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables
}

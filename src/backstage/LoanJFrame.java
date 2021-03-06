/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backstage;

import backstage.Collateral;
import backstage.LoanAccount;
import backstage.Tool;
import connect_database.CustomerAddingFunction;
import connect_database.CustomerAlteringFunction;
import connect_database.CustomerDeletingFunction;
import connect_database.CustomerSearchingFunction;
import connect_database.ManagerFunction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author user
 */
public class LoanJFrame extends javax.swing.JFrame {

    private LoanAccount account;
    private List<String[]> loanlist;

    /**
     * Creates new form LoanJFrame
     */
    public LoanJFrame(LoanAccount account) {
        this.account = account;
        initComponents();
        setLoanList();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // check the loans
    private void setLoanList() {
        loanlist = CustomerSearchingFunction.searchLoanList(account.customerID);
        assert loanlist != null;
        ArrayList<String> strings = new ArrayList<String>();
        for (String[] loanstrings : loanlist) {
            strings.add(Arrays.toString(loanstrings));
        }

        loanList.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() {
                return strings.size();
            }

            public String getElementAt(int i) {
                return strings.get(i);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        collateralTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        priceTextField = new javax.swing.JTextField();
        errorLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        loanList = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Apply for a loan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        collateralTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collateralTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Collateral:");

        jLabel2.setText("Price($):");

        errorLabel.setText(" ");

        jScrollPane1.setViewportView(loanList);

        jLabel3.setText("List of loans:");

        jButton2.setText("Pay a loan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jButton3)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(collateralTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                    .addComponent(priceTextField)))
                            .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jButton1)))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(37, 37, 37)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(collateralTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void collateralTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collateralTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_collateralTextFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String name = collateralTextField.getText();
        String price = priceTextField.getText();

        if (account.collateralAlreadyExist(name)) {
            errorLabel.setText("You already have the collateral.");
        }
        else if (!Tool.is_number(price)) {
            errorLabel.setText("Invalid price input.");
        }
        else {
            double priceD = Double.parseDouble(price);
            if (priceD < 250) {
                errorLabel.setText(
                        "Your item is too cheap. A collateral shoule be over $250.");
            }
            else {
                errorLabel.setText(
                        "Ok! We will loan you 90% of this collateral.");
                account.currency.add("Dollar", priceD, account.loanRate);
                BigDecimal p = new BigDecimal(price);
                p = p.multiply(new BigDecimal("0.9"));
                Collateral collateral = new Collateral(name, p);
                account.collaterals.add(collateral);
                account.createTransaction(p.toString(), "Dollar",
                                          "Apply for a loan. " + collateral);
                //alter saving account
                CustomerAlteringFunction.alterSavingAccount(account.customerID,
                                                            "Dollar", p);
                //alter manager
                ManagerFunction.alterManagerAccount("Dollar", new BigDecimal(
                                                    "-" + p.toString()));
                //new loan in database
                CustomerAddingFunction.addLoan(account.customerID, p, collateral);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //loanlist: {loan_record_ID, loan_amount, collateral_name}
        int index = loanList.getSelectedIndex();
        if (index >= 0) {
            BigDecimal price = new BigDecimal(loanlist.get(index)[1]);
            boolean success = account.currency.sub("Dollar", price.doubleValue(),
                                                   "1");
            if (success) {
                errorLabel.setText(
                        "Now you have your " + loanlist.get(index)[2] + " again!");
                account.createTransaction("-" + loanlist.get(index)[1], "Dollar",
                                          "Pay for the chosen collateral " + loanlist.get(
                                                  index)[2] + ".");
                //alter manager
                ManagerFunction.alterManagerAccount("Dollar", price);
                //alter saving account
                CustomerAlteringFunction.alterSavingAccount(account.customerID,
                                                            "Dollar",
                                                            new BigDecimal(
                                                                    "-" + loanlist.get(
                                                                            index)[1]));
                CustomerDeletingFunction.deleteOneLoan(account.customerID,
                                                       Integer.parseInt(
                                                               loanlist.get(
                                                                       index)[0]));
                this.setLoanList();
            }
            else {
                account.createTransaction("0", "Dollar",
                                          "Failed to pay for the chosen collateral. " + loanlist.get(
                                                  index)[2] + ".");
                errorLabel.setText("Failed to pay for the chosen collateral.");
            }
        }
        else {
            errorLabel.setText("Please choose a loan.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setLoanList();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(LoanJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoanJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoanJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoanJFrame.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField collateralTextField;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> loanList;
    private javax.swing.JTextField priceTextField;
    // End of variables declaration//GEN-END:variables
}

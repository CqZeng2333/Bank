package backstage;

import connect_database.CustomerDeletingFunction;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends User {

    Currency currency = new Currency();
    ArrayList<Account> accounts = new ArrayList<>();
    ArrayList<Transaction> transactions = new ArrayList<>();
    ArrayList<Collateral> collaterals = new ArrayList<>();

    public Customer(int id) {
        super(id);
    }

    public Customer(String name, String pwd) {
        super(name, pwd);
    }

    public String toString() {
        String str = "ID: " + id + " Name: " + name;
        return str;
    }

    public String printRecords() {
        String str = "";
        transactions.clear();
        if (accounts.size() == 0) {
            System.out.println("No accounts have been made.");
            str += "No accounts have been made.";
        }
        else {
            for (int i = 0; i < accounts.size(); i++) {
                transactions.addAll(accounts.get(i).transactions);
            }
            if (transactions.size() == 0) {
                System.out.println("No transactions have been made.");
                str += "No transactions have been made.";
            }
            else {
                for (int i = 0; i < transactions.size(); i++) {
                    System.out.println(transactions.get(i));
                    str += transactions.get(i) + "\n";
                }
            }
        }
        return str;
    }

    public int removeAccount() {
        System.out.println("Dear customer " + name + ":");
        System.out.println("Deleting an account will charge you 5 dollars.");
        if (accounts.size() == 0) {
            System.out.println("You have not created any account.");
            return -1;
        }
        else {
            for (int i = 0; i < accounts.size(); i++) {
                System.out.print((i + 1) + " ");
                System.out.println(accounts.get(i).accountType);
            }
        }
        System.out.println("Input the ID of the account you want to delete.");
        Scanner choice = new Scanner(System.in);
        String num = choice.nextLine();
        while (!Tool.is_number(num)) {
            System.out.println("Invalid input. Input again.");
            num = choice.nextLine();
        }
        int number = Integer.parseInt(num);
        while (number < 1 || number > accounts.size()) {
            System.out.println("Invalid input. Input again.");
            num = choice.nextLine();
            number = Integer.parseInt(num);
        }
        if (accounts.get(number - 1).accountType.equals("SAVING")) {
            boolean isempty = accounts.get(number - 1).currency.is_empty();
            if (isempty) {
                boolean success = accounts.get(number - 1).currency.sub("Dollar",
                        5, "1");
                if (success) {
                    System.out.println(
                            "Successfully delete your saving account!");
                    accounts.get(number - 1).createTransaction("-5", "Dollar",
                            "Delete account.");
                    CustomerDeletingFunction.deleteSavingAccount(id);
                    accounts.remove(number - 1);
                }
                else {
                    System.out.println("You don't have enough money.");
                    accounts.get(number - 1).createTransaction("0", "Dollar",
                            "Failed to delete account.");
                }
            }
            else {
                System.out.println(
                        "You have to withdraw all your money first. (Leave 5 dollars for deleting account)");
                accounts.get(number - 1).createTransaction("0", "Dollar",
                        "Failed to delete account.");
            }
        }
        else if (accounts.get(number - 1).accountType.equals("CHECKING")) {
            boolean success = currency.sub("Dollar", 5, "1");
            if (success) {
                System.out.println("Successfully delete your checking account!");
                accounts.get(number - 1).createTransaction("-5", "Dollar",
                        "Delete account.");
                CustomerDeletingFunction.deleteCheckingAccount(id);
                accounts.remove(number - 1);
            }
            else {
                System.out.println("You don't have enough money.");
                accounts.get(number - 1).createTransaction("0", "Dollar",
                        "Failed to delete account.");
            }

        }
        else if (accounts.get(number - 1).accountType.equals("LOAN")) {
            LoanAccount loanAccount = (LoanAccount) accounts.get(number - 1);
            System.out.println(
                    "Please be aware that if you still have collaterals in our bank, deleting this account will make you lose all your collaterals.");
            System.out.println("Do you want to go back?(y/n)");
            Scanner exit = new Scanner(System.in);
            String exitornot = exit.nextLine();
            while (!Tool.is_alpha(exitornot)) {
                System.out.println("Invalid input. It should be y or n.");
                exitornot = exit.nextLine();
            }
            while ((!exitornot.equals("y")) && (!exitornot.equals("n")) && (!exitornot.equals(
                    "Y")) && (!exitornot.equals(
                    "N"))) {
                System.out.println("Invalid input. It should be y or n.");
                exitornot = exit.nextLine();
            }
            if (exitornot.equals("y") || exitornot.equals("Y")) {
                return -1;
            }
            if (loanAccount.is_empty()) {
                boolean success = currency.sub("Dollar", 5, "1");
                if (success) {
                    System.out.println("Successfully delete your loan account!");
                    accounts.get(number - 1).createTransaction("-5", "Dollar",
                            "Delete account.");
                    CustomerDeletingFunction.deleteLoanAccountWithoutPayback(id);
                    accounts.remove(number - 1);
                }
                else {
                    System.out.println("You don't have enough money.");
                    accounts.get(number - 1).createTransaction("0", "Dollar",
                            "Failed to delete account.");
                }
            }
            else {
                System.out.println("Your loans are mine now.");
                accounts.get(number - 1).createTransaction("0", "Dollar",
                        "Delete account without payback.");
                CustomerDeletingFunction.deleteLoanAccountWithoutPayback(id);
            }
        }
        return 1;
    }

    public int createAccount(String str) {
        System.out.println("Dear customer " + name + ":");
        if (str.equals("SAVING")) {
            SavingAccount savingAccount = new SavingAccount(id, currency);
            boolean success = savingAccount.initAccount();

            if (!success) {
                return -1;
            }
            else {
                accounts.add(savingAccount);
            }
        }
        else if (str.equals("CHECKING")) {
            CheckingAccount checkingAccount = new CheckingAccount(id, currency);
            boolean success = checkingAccount.initAccount();
            if (!success) {
                return -1;
            }
            else {
                accounts.add(checkingAccount);
            }
        }
        else if (str.equals("LOAN")) {
            LoanAccount loanAccount = new LoanAccount(id, currency, collaterals);
            boolean success = loanAccount.initAccount();
            if (!success) {
                return -1;
            }
            else {
                accounts.add(loanAccount);
            }
        }
        return accounts.size() - 1;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

}
package backstage;

import java.util.ArrayList;

public class Manager extends User {

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    private ArrayList<Customer> customers = new ArrayList<>();

    //public ArrayList<Transaction> transactions=new ArrayList<>();
    Manager() {
    }

    Manager(String name, String pwd) {
        super(name, pwd);
    }

    Manager(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public void printAll() {
        for (int i = 0; i < this.customers.size(); i++) {
            System.out.println(customers.get(i));
        }
    }

    public void printLoan() {
        for (int i = 0; i < this.customers.size(); i++) {
            if (customers.get(i).accounts.size() > 0) {
                for (int j = 0; j < customers.get(i).accounts.size(); j++) {
                    if (customers.get(i).accounts.get(j).accountType.equals(
                            "LOAN")) {
                        System.out.println(customers.get(i));
                        System.out.println(
                                (LoanAccount) customers.get(i).accounts.get(j));
                    }
                }
            }
        }
    }

    public void checkCustomer(int id) {
        for (int i = 0; i < this.customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                System.out.println(customers.get(i));
                if (customers.get(i).accounts.size() == 0) {
                    ;
                }
                else {
                    System.out.println("This customer owns accounts:");
                    for (int j = 0; j < customers.get(i).accounts.size(); j++) {
                        System.out.println(
                                (j + 1) + " " + customers.get(i).accounts.get(j).accountType);
                    }
                    customers.get(i).currency.toString();
                }
                return;
            }
        }
        System.out.println("The customer does not exist!");
    }

    public void checkCustomer(String name) {
        for (int i = 0; i < this.customers.size(); i++) {
            if (customers.get(i).getName().equals(name)) {
                System.out.println(customers.get(i));
                if (customers.get(i).accounts.size() == 0) {
                    ;
                }
                else {
                    System.out.println("This customer owns accounts");
                    for (int j = 0; j < customers.get(i).accounts.size(); j++) {
                        System.out.println(
                                (j + 1) + " " + customers.get(i).accounts.get(j).accountType);
                    }
                    customers.get(i).currency.toString();
                }
                return;
            }
        }
        System.out.println("The customer does not exist!");
    }

    public String getCustomerInfo(int i) {
        String info = "";
        info += customers.get(i);
        if (customers.get(i).accounts.size() > 0) {
            info += "\nAccounts: \n";
            for (int j = 0; j < customers.get(i).accounts.size(); j++) {
                Account account = customers.get(i).accounts.get(j);
                info += (j + 1) + ". " + account.accountType + " " + account.toString() + " \n";
            }
        }
        return info;
    }

    //return the id of the customer, return -1 if cannot find
    public int findCustomerbyName(String name) {
        for (int i = 0; i < this.customers.size(); i++) {
            if (customers.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public int findCustomerbyID(int id) {
        for (int i = 0; i < this.customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}

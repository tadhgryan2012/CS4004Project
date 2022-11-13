package main;

import java.time.LocalDate;
import java.util.ArrayList;

public class Department {
    private ArrayList<Loan> loans;
    private ArrayList<User> users;
    private ArrayList<Staff> staff;

    public Department() {
        loans = new ArrayList<>();
        users = new ArrayList<>();
        staff = new ArrayList<>();
    }

    public boolean loan(Book book, LocalDate date, User user) {
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getBook() == book) return false;
        }
        Loan loan = new Loan(book, date, user);
        book.loan(loan);
        loans.add(loan);
        return true;
    }


    public boolean returnLoan(Book book, LocalDate date, User user) {
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getBook() == book && loans.get(i).getUser() == user) {
                return loans.remove(loans.get(i));
            }
        }
        return false;
    }

        public ArrayList<Loan> getHistoryOfBook (Book book){
            return book.getHistory();
        }

        public ArrayList<Loan> getLoans () {
            return loans;
        }

        public void setLoans (ArrayList < Loan > loans) {
            this.loans = loans;
        }

        public ArrayList<User> getUsers () {
            return users;
        }

        public void setUsers (ArrayList < User > users) {
            this.users = users;
        }

        public ArrayList<Staff> getStaff () {
            return staff;
        }

        public void setStaff (ArrayList < Staff > staff) {
            this.staff = staff;
        }
    }

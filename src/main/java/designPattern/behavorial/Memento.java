package designPattern.behavorial;

import java.util.Stack;

public class Memento {
    /*
- Loose coupling
- Well-defined, but complex
-  Reusable components
-  Hub / Router
-  Examples:
-  java.util.Timer
-  java.lang.reflect.Method#invoke()

- mediator knows about colleagues instead of colleagues knowing about each other
- e.g. Java Timer class
     */


    public static void main(String args[]) {

        Caretaker caretaker = new Caretaker();
        Employee emp = new Employee();
        emp.setName("John Wick");
        emp.setAddress("111 Main Street");
        emp.setPhone("888-555-1212");
        System.out.println("Employee before save:                     " + emp);
        caretaker.save(emp);
        emp.setPhone("444-555-6666");
        caretaker.save(emp);
        System.out.println("Employee after changed phone number save: " + emp);
        emp.setPhone("333-999-6666"); // <--- we haven't called save!
        caretaker.revert(emp);
        System.out.println("Reverts to last save point:               " + emp);
        caretaker.revert(emp);
        System.out.println("Reverted to original:                     " + emp);

    }
}

    class Caretaker {

        private Stack<EmployeeMemento> employeeHistory = new Stack<>();

        public void save(Employee emp) {
            employeeHistory.push(emp.save());
        }

        public void revert(Employee emp) {
            emp.revert(employeeHistory.pop());
        }
    }
    class Employee {

        private String name;
        private String address;
        private String phone;

        public String getAddress() {
            return address;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String toString() {
            return name + " : " + phone;
        }

        public EmployeeMemento save() {
            return new EmployeeMemento(name, phone);
        }

        public void revert(EmployeeMemento emp) {
            this.name = emp.getName();
            this.phone = emp.getPhone();
        }
    }

    class EmployeeMemento {

        private String name;
        private String phone;

        public EmployeeMemento(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }


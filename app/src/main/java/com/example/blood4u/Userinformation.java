package com.example.blood4u;

public class Userinformation {
    public String name;
    public String surname;
    public String birthday;
    public String bloodtype;
    public String blooddonate;

    public Userinformation(){
    }

    public Userinformation(String name,String surname, String birthday, String bloodtype, String blooddonate){
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.bloodtype = bloodtype;
        this.blooddonate = blooddonate;
    }
    public String getUserName() {
        return name;
    }
    public String getUserSurname() {
        return surname;
    }
    public  String getUserBirthday() {
        return birthday;
    }
    public  String getUserbloodtype() {
        return bloodtype;
    }
    public  String getUserblooddonate() {
        return blooddonate;
    }

}

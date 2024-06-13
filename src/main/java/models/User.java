package models;

import java.util.Date;

public class User
{
    private int user_id;
    private String user_name;
    private String user_phone;
    private String user_email;
    private String user_password;
    private String user_sexe;
    private String user_role;
    private String user_image;

    public User(){}

    public User(int user_id,String user_name,String user_phone,String user_email,String user_password,
                String user_sexe,String user_role,String user_image)
    {
        this.user_id=user_id;
        this.user_name=user_name;
        this.user_phone=user_phone;
        this.user_email=user_email;
        this.user_password=user_password;
        this.user_sexe=user_sexe;
        this.user_role=user_role;
        this.user_image=user_image;
    }

    public User(String user_name,String user_phone,String user_email,String user_password,
                String user_sexe,String user_role,String user_image)
    {
        this.user_name=user_name;
        this.user_phone=user_phone;
        this.user_email=user_email;
        this.user_password=user_password;
        this.user_sexe=user_sexe;
        this.user_role=user_role;
        this.user_image=user_image;
    }

    public User(String user_name,String user_phone,String user_email,String user_password,
                String user_sexe)
    {
        this.user_name=user_name;
        this.user_phone=user_phone;
        this.user_email=user_email;
        this.user_password=user_password;
        this.user_sexe=user_sexe;
    }

    public User(int user_id,String user_name,String user_phone,String user_email,String user_password,
                String user_sexe,String user_image)
    {
        this.user_id=user_id;
        this.user_name=user_name;
        this.user_phone=user_phone;
        this.user_email=user_email;
        this.user_password=user_password;
        this.user_sexe=user_sexe;
        this.user_image=user_image;
    }

    public int getUser_id() {return user_id;}
    public void setUser_id(int user_id) {this.user_id=user_id;}

    public String getUser_name() {return user_name;}
    public void setUser_name(String user_name) {this.user_name=user_name;}

    public String getUser_phone() {return user_phone;}
    public void setUser_phone(String user_phone) {this.user_phone=user_phone;}

    public String getUser_email() {return user_email;}
    public void setUser_email(String user_email) {this.user_email=user_email;}

    public String getUser_password() {return user_password;}
    public void setUser_password(String user_password) {this.user_password=user_password;}

    public String getUser_sexe() {return user_sexe;}
    public void setUser_sexe(String user_sexe) {this.user_sexe=user_sexe;}

    public String getUser_role() {return user_role;}
    public void setUser_role(String user_role) {this.user_role=user_role;}

    public String getUser_image() {return user_image;}
    public void setUser_image(String user_image) {this.user_image=user_image;}

    @Override
    public String toString() {
        return "\nUser : \n" +
                "Name : " + this.user_name +
                "\nPhone : " + this.user_phone +
                "\nEmail : " + this.user_email +
                "\nPassword : " + this.user_password +
                "\nSexe : " + this.user_sexe +
                "\n--------------------------\n";
    }
}

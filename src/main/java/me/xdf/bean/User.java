package me.xdf.bean;

import java.io.Serializable;
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private String gender;
    private String email;
    private int age;
    private String imagePath;
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    // 默认构造函数
    public User() {
    }
    // 带参数的构造函数

    public User(int id, String username, String password, String gender, String email, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.age = age;

    }

    public User(int id, String username, String password, String gender, String email, int age, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.imagePath = image;
    }

    public User(int id, String username, String password, String gender, String email, int age, String imagePath, String salt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.imagePath = imagePath;
        this.salt = salt;
    }

    public User(int id,  String username, String gender, String email, int age, String imagePath) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.imagePath = imagePath;
    }

    // Getter和Setter方法
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", image='" + imagePath + '\'' +
                '}';
    }
}
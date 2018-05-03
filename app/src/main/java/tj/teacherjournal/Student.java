package tj.teacherjournal;

/**
 * Created by risatgajsin on 24.04.2018.
 */

public class Student {

    private int id;
    private String name;
    private String gender;
    private String age;
    private String groupid; // Надо бы сделать здесь конструкцию привязки из User -> groupid
    private String registration;
    private String studnumber;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getStudnumber() {
        return studnumber;
    }

    public void setStudnumber(String studnumber) {
        this.studnumber = studnumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

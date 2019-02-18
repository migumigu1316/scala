package day12;

import java.io.Serializable;

/**
 * @ClassName: student
 * @Description: TODO
 * @Author: xqg
 * @Date: 2018/10/8 14:23
 *
 * java 实现第一种反射的方式
 */
public class student implements Serializable {
    private int id;
    private String name;
    private int age;

    @Override
    public String toString() {
        return "student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

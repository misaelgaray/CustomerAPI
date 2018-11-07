package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Customer implements IModelConvert, Serializable {

    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public void convert(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.email = resultSet.getString("email");
        this.age = resultSet.getInt("age");
        this.status = resultSet.getBoolean("status");
    }
}

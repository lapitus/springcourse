package com.lapitus.springcourse.dao;

import com.lapitus.springcourse.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PersonDAO {

    //private List<Person> people;
    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XE";
    private static final String USERNAME = "sbdemo";
    private static final String USERPASSWORD = "sbdemo";

    private static Connection connection;

    static {

        try{
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL,USERNAME,USERPASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Person> index() {

        List<Person> people = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            String SQL = "select * from person2";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {

                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return people;
    }

//    {
//        people = new ArrayList<>();
//
//        people.add(new Person(++PEOPLE_COUNT,"Alexey",14,"laputu@mail.ru"));
//        people.add(new Person(++PEOPLE_COUNT,"Kolya",11,"aaa@aaa.aaa"));
//        people.add(new Person(++PEOPLE_COUNT,"Vasya",7,"bbb@bbb.bbb"));
//        people.add(new Person(++PEOPLE_COUNT,"Petya",6,"ccc@ccc.ccc"));
//        people.add(new Person(++PEOPLE_COUNT,"Ivan",5,"vvv@vvv.vvv"));
//    }

//    public List<Person> index() {
//        return people;
//    }
//
public Person show(int id) {

    Person person = new Person();
    try {
        Statement statement = connection.createStatement();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM person2 WHERE id = ?");

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

    //return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);

    return person;
}

//    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
//    }

    public void save(Person person) {
//INJECTION HERE!!!
//        try {
//            Statement statement = connection.createStatement();
//            String SQL = String.format("insert into person2 values(%s,'%s',%s,'%s')",
//                    ++PEOPLE_COUNT,person.getName(),person.getAge(),person.getEmail()) ;
//            statement.executeUpdate(SQL);
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO person2 VALUES (?,?,?,?)");

            preparedStatement.setInt(1, ++PEOPLE_COUNT);
            preparedStatement.setString(2,person.getName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setString(4,person.getEmail());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

//    public void update(int id, Person person) {
//        Person personForUpdate = show(id);
//
//        personForUpdate.setName(person.getName());
//        personForUpdate.setAge(person.getAge());
//        personForUpdate.setEmail(person.getEmail());
//    }

    public void update(int id, Person person) {
        Person personForUpdate = show(id);

        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE person2 set name = ?, age = ?, email = ? WHERE id = ?");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void delete(int id) {

        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE  FROM person2 WHERE id = ?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

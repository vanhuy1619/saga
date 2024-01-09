package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
//@EnableJpaRepositories("com.example.orderservice.api.repositories")
//@ComponentScan(basePackages = {"com.example.orderservice.api.*"})
//@EntityScan({"com.example.orderservice.api.data"})
public class OrderServiceApplication {

    public static void main(String[] args) {
//        try{
//            System.out.println("hello, world!");
//            Class.forName("org.h2.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:h2:./data/orderDB", "sa", "");
//            if(conn != null) {
//                System.out.println("Connect success");
//            }
//            // add application code here
//            conn.close();
//        }catch(ClassNotFoundException ex){
//            System.out.println( "ERROR: Class not found: " + ex.getMessage() );
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}

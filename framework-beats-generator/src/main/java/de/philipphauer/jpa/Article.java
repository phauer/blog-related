package de.philipphauer.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Article {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    public Article(String name){
        this.name = name;
    }

    public Article(){
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

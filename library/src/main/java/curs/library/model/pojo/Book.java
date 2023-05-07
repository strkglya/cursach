package curs.library.model.pojo;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import curs.library.service.helper.Rounder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple Java Bean object representing a room in a library that can be
 * booked by a {@link User} upon his/her {@link Request}
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Setter
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter         private Long                id;

    @Getter @Setter @Basic(optional = false) @Column(name = "name") private String                 name;


    @Getter         @Basic(optional = false) @Column(name = "author") private String           author;

    @Getter         @Basic(optional = false) @Column(name = "genre") private String           genre;

    @Getter         @Basic(optional = false) @Column(name = "picurl") private String              picURL;

    @Getter         @Basic(optional = false) @Column(name = "price") private double              price;


    @OneToMany (mappedBy = "book")
    @Getter @Setter                          private List<Bill>          billList = new ArrayList<>();

    public Book(String  name, @NonNull String author, @NonNull String genre, @NonNull String picURL, double price) {
        this.name = name;
        this.author = author;
        this.genre=genre;
        this.picURL = picURL;
        setPrice(price);

        log.info("Object Room successfully created");
    }

    public void setPrice(double price) {
        this.price = Rounder.round(price);
        log.info("Double value of 'price' field set succesfully: " + this.price);
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setPicURL(@NonNull String picURL) {
        this.picURL = picURL;
    }

}

package curs.library.model.pojo;

import curs.library.model.enums.Status;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Date;

/**
 * Simple Java Bean object that represents a request made by {@link User}
 * and contains the User, number of places in the {@link Book},
 * class of the {@link Book}, start and end dates {@link Date} and
 * a boolean identifying if the request has been approved byAdmin.
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long                 id;

    @ManyToOne private User                user;

    @Setter @Basic(optional = false) private long bookID;

    @Setter @Basic(optional = false) private String bookName;

    @Enumerated(EnumType.ORDINAL)
    @Basic(optional = false) private Status status;


    public Request(@NonNull User user,@NonNull long bookID,@NonNull String bookName,@NonNull Status status) {
        this.user = user;
        this.bookID = bookID;
        this.bookName = bookName;
        this.status = status;
        log.info("Object Request successfully created");
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }


    public void setStatus(@NonNull Status status) {
        this.status = status;
    }


}

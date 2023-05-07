package curs.library.model.pojo;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import curs.library.model.enums.Role;
import curs.library.model.exceptions.NotEnoughDataException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Simple Java Bean object that represents site user
 */

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
//@AllBasicFalse
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter                                     private Long           id;

    @Column(unique = true, columnDefinition="NVARCHAR(255)")
    @Getter         @Basic(optional = false)    private String         name;

    @Enumerated(EnumType.STRING)
    @Getter         @Basic(optional = false)    private Role           role;

    @Getter         @Basic(optional = false)    private String         passwordEncoded;
    @Getter         @Basic(optional = false)    private String         email;


    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @Getter @Setter                             private Set<Request> requests;

    public User(@NonNull String name, @NonNull Role role, @NonNull String passwordEncoded, @NonNull String email) {
        this.name = name;
        this.role = role;
        this.passwordEncoded = passwordEncoded;
        this.email = email;
        log.info("Object User successfully created");
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setRole(@NonNull Role role) {
        this.role = role;
    }

    public static class Builder {
        private User u;

        /***
         * The Builder class, standard architecture pattern, allows to create new object of User class.
         * All parameters should be present (except Id); otherwise, throws NotEnoughDataException.
         */
        public Builder() {
            u = new User();
        }

        public Builder withName(@NonNull String name) {
            u.name = name;
            return this;
        }

        public Builder withRole(@NonNull Role role) {
            u.role = role;

            return this;
        }

        public Builder withPasswordEncoded(@NonNull String pass) {
            u.passwordEncoded = pass;

            return this;
        }

        public Builder withEmail(@NonNull String email) {
            u.email = email;

            return this;
        }



        public User build() throws NotEnoughDataException {
            if (u.name == null || u.role == null ||
            u.passwordEncoded == null || u.email == null ) {
                throw new NotEnoughDataException("Not enough data to build object User : " + u);
            }
            return u;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                role == user.role &&
                passwordEncoded.equals(user.passwordEncoded) &&
                email.equals(user.email);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role, passwordEncoded, email);
    }
}


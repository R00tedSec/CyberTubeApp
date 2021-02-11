package com.cybertube.web.Users;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cybertube.web.Articles.ArticlesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ArticlesRepository articleRepository;

    public void saveUser(String username, String email, String pass) {
        User user = new User(username, email, pass);
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).get();
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmailIgnoreCase(email);
    }

    public User getUserByName(String username) {
        return this.userRepository.findByUsernameIgnoreCase(username);
    }

    public List<String> getUsersByAuthor(String author) {
        TypedQuery<String> query = entityManager
                .createQuery("SELECT c.username FROM User c WHERE " + "c.username LIKE  ?1", String.class);
        query.setParameter(1, "%" + author + "%");
        return query.getResultList();
    }

    public String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName(); // get logged in username
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean checkIfNotUsed(String username) {
        return userRepository.findByUsername(username) == null;
    }

    public void updateUser(User user) {
        this.userRepository.saveAndFlush(user);
    }

    public void updateUser(String username, String new_username, String email, String pass, String new_pass) {
        Long userID = this.userRepository.findByUsername(username).getId_user();
        if (this.userRepository.existsById(userID)) {
            User auxUser = this.userRepository.getOne(userID);
            if (!new_username.equals(null))
                auxUser.setUsername(new_username);

            if (!email.equals(null))
                auxUser.setEmail(email);

            if ((!new_pass.equals(null)) && BCrypt.checkpw(pass, auxUser.getPass()))
                auxUser.setPass(BCrypt.hashpw(new_pass, BCrypt.gensalt()));

            this.userRepository.saveAndFlush(auxUser);
        }
    }
}

package me.hajk1.foodreservation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args){
        String encodedPassword = new BCryptPasswordEncoder().encode("ThisIsMyRamz1403");
    System.out.println("encodedPassword = " + encodedPassword);
    }
}

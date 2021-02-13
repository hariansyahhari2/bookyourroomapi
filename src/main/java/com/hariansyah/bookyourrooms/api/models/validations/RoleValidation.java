package com.hariansyah.bookyourrooms.api.models.validations;

import com.hariansyah.bookyourrooms.api.configs.jwt.JwtToken;
import com.hariansyah.bookyourrooms.api.entities.Account;
import com.hariansyah.bookyourrooms.api.exceptions.InvalidCredentialsException;
import com.hariansyah.bookyourrooms.api.repositories.AccountRepository;

import javax.servlet.http.HttpServletRequest;

import static com.hariansyah.bookyourrooms.api.enums.RoleEnum.*;

public class RoleValidation {
    public static void validateRoleEmployee(HttpServletRequest request, JwtToken jwtTokenUtil, AccountRepository accountRepository) {
        String token = request.getHeader("Authorization");
        if (token == null && !token.startsWith("Bearer ")) {
            throw new InvalidCredentialsException();
        }
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountRepository.findByUsername(username);
        if (!account.getRole().equals(HOTEL_EMPLOYEE)) {
            throw new InvalidCredentialsException();
        }
    }

    public static void validateRoleAdmin(HttpServletRequest request, JwtToken jwtTokenUtil, AccountRepository accountRepository) {
        String token = request.getHeader("Authorization");
        if (token == null && !token.startsWith("Bearer ")) {
            throw new InvalidCredentialsException();
        }
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountRepository.findByUsername(username);
        if (!account.getRole().equals(ADMIN)) {
            throw new InvalidCredentialsException();
        }
    }

    public static void validateRoleHotelManager(HttpServletRequest request, JwtToken jwtTokenUtil, AccountRepository accountRepository) {
        String token = request.getHeader("Authorization");
        if (token == null && !token.startsWith("Bearer ")) {
            throw new InvalidCredentialsException();
        }
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountRepository.findByUsername(username);
        if (!account.getRole().equals(HOTEL_MANAGER)) {
            throw new InvalidCredentialsException();
        }
    }
}

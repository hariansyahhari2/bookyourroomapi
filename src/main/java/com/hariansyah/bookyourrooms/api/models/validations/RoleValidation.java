package com.hariansyah.bookyourrooms.api.models.validations;

import com.hariansyah.bookyourrooms.api.configs.jwt.JwtToken;
import com.hariansyah.bookyourrooms.api.entities.Account;
import com.hariansyah.bookyourrooms.api.enums.RoleEnum;
import com.hariansyah.bookyourrooms.api.exceptions.InvalidCredentialsException;
import com.hariansyah.bookyourrooms.api.repositories.AccountRepository;

import javax.servlet.http.HttpServletRequest;

import static com.hariansyah.bookyourrooms.api.enums.RoleEnum.*;

public class RoleValidation {
    public static void validateRoleEmployee(HttpServletRequest request, JwtToken jwtTokenUtil, AccountRepository accountRepository) {
        getHeaderToken(request, jwtTokenUtil, accountRepository, HOTEL_EMPLOYEE);
    }

    public static void validateRoleAdmin(HttpServletRequest request, JwtToken jwtTokenUtil, AccountRepository accountRepository) {
        getHeaderToken(request, jwtTokenUtil, accountRepository, ADMIN);
    }

    public static void validateRoleHotelManager(HttpServletRequest request, JwtToken jwtTokenUtil, AccountRepository accountRepository) {
        getHeaderToken(request, jwtTokenUtil, accountRepository, HOTEL_MANAGER);
    }

    private static void getHeaderToken(HttpServletRequest request, JwtToken jwtTokenUtil, AccountRepository accountRepository, RoleEnum role) {
        String token = request.getHeader("Authorization");
        assert token != null;
        if (!token.startsWith("Bearer ")) {
            throw new InvalidCredentialsException();
        }
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountRepository.findByUsername(username);
        if (!account.getRole().equals(role)) {
            throw new InvalidCredentialsException();
        }
    }
}

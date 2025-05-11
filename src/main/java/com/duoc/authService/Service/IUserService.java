package com.duoc.authService.Service;

import com.duoc.authService.Model.User;

public interface IUserService {
    public User createUser(User user);
    public User updatePassword(User user);
    public String login(User user);
    public String generatePassword();
}

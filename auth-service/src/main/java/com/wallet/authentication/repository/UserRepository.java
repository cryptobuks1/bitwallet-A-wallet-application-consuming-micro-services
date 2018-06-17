package com.wallet.authentication.repository;

import com.wallet.authentication.model.User;

public interface UserRepository {
	User findUser(String username);
}

package DAO.Interfaces;

import Model.Account;

public interface AccountDAOInterface {

    void createAccount(Account account);

    Account getAccountById(int account_id);

    Account getAccountByUsername(String username);

    Account insertAccount(Account account);

    void updateAccount(Account account);

    Account deleteAccount(int account_id);
}

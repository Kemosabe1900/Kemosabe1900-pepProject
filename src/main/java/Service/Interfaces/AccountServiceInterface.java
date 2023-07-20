package Service.Interfaces;

import Model.Account;

public interface AccountServiceInterface {
    Account createAccount(Account account);

    Account loginAccount(String username, String password);

    Account getAccountByUsername(String username);

    Account getAccountById(int account_id);

    // Account insertAccount(Account account);

    void updateAccount(Account account);

    Account deleteAccount(int account_id);
}

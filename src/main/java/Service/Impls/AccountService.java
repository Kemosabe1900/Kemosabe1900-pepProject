package Service.Impls;

import Service.Interfaces.AccountServiceInterface;

import DAO.Impls.AccountDAO;
import Model.Account;

public class AccountService implements AccountServiceInterface {

    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account createAccount(Account account) {
        // if (account.getUsername().isEmpty()) {
        // // ctx.status(400); // Bad Request
        // return null;
        // }

        // if (account.getPassword().length() < 4) {
        // // ctx.status(400); // Bad Request
        // return null;
        // }
        // Account existingAccount =
        // accountDAO.getAccountByUsername(account.getUsername());
        // if (existingAccount != null) {
        // return null;
        // }
        return accountDAO.createAccount(account);
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    public Account getAccountById(int account_id) {
        return accountDAO.getAccountById(account_id);
    }

    // public Account insertAccount(Account account) {
    // return accountDAO.insertAccount(account);
    // }

    public void updateAccount(Account account) {
        accountDAO.updateAccount(account);
    }

    public Account deleteAccount(int account_id) {
        return accountDAO.deleteAccount(account_id);
    }

    public Account loginAccount(String username, String password) {
        Account account = accountDAO.getAccountByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

   
}

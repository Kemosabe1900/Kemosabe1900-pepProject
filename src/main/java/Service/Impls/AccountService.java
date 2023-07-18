package Service.Impls;

import Service.Interfaces.AccountServiceInterface;

import DAO.Impls.AccountDAO;
import Model.Account;

public class AccountService implements AccountServiceInterface {
    private final AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account createAccount(Account account) {
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(existingAccount != null){
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    public Account getAccountById(int account_id) {
        return accountDAO.getAccountById(account_id);
    }

    public Account insertAccount(Account account) {
        return accountDAO.insertAccount(account);
    }

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

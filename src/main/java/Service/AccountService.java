package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public Account register(Account account) {
        if (accountExists(account.getUsername()) ||
                account.getUsername() == null || account.getUsername().isEmpty() ||
                account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        return accountDAO.createAccount(account);
    }

    public boolean accountExists(String username) {
        return accountDAO.accountExists(username);
    }

    public Account login(String username, String password) {
        return accountDAO.validateLogin(username, password);
    }
}

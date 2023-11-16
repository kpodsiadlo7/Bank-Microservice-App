package com.mainapp;

import com.mainapp.account.AccountFacade;
import com.mainapp.transaction.dto.TransactionDto;
import com.mainapp.transaction.TransactionFacade;
import com.mainapp.transfer.dto.TransferDto;
import com.mainapp.user.User;
import com.mainapp.user.UserFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final TransactionFacade transactionFacade;
    private final UserFacade userFacade;
    private final AccountFacade accountFacade;

    public boolean createUser(final User user, final ModelMap modelMap) {
        log.info("create user");
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            log.warn("password doesnt match");
            modelMap.put("error", "Password doesn't match!");
            return false;
        }
        try{
            // Próba utworzenia i zwrócenia użytkownika za pomocą 'user-manager'
            User userFromUserManager = userFacade.createAndReturnNewUser(user);

            if (userFromUserManager.getId() == null) {
                log.warn("error " + userFromUserManager.getUsername());
                modelMap.put("error", userFromUserManager.getUsername());
                return false;
            }
            if(userFromUserManager.getUsername().equals("User already exist!")){
                log.info("User already exist!");
                modelMap.put("error", "User already exist!");
            }

        } catch (Exception e){
            log.warn("failed with connecting to 'user-manager'");
            modelMap.put("error", "Critical error");
            return false;
        }
        return true;
    }

    public boolean makeTransaction(final User user, final TransferDto transferDto, final ModelMap modelMap,
                                   final String descriptionTransaction, final Long thisAccountId) {
        log.info("make transaction");
        try {
            TransactionDto returningTransactionDto =
                    transactionFacade.makeTransaction(user.getId(), thisAccountId, descriptionTransaction, transferDto);
            if (returningTransactionDto.getKindTransaction().equals("error")) {
                modelMap.put("error", returningTransactionDto.getDescription());
                return false;
            }
        } catch (Exception e) {
            modelMap.put("error", "There was an error with trying to connect with transactional service");
            try {
                modelMap.put("userBankAccounts", accountFacade.getAllAccountsByUserId(user.getId()));
            } catch (Exception b) {
                modelMap.put("error", "There was an error fetching your accounts");
            }
            modelMap.put("quickTransfer", new TransferDto());
            return false;
        }
        log.info("successful make transaction");
        return true;
    }
}

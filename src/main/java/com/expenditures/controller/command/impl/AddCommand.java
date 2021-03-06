package com.expenditures.controller.command.impl;

import com.expenditures.controller.command.Command;
import com.expenditures.controller.exceptions.IncorrectCommandInputException;
import com.expenditures.entity.CurrencyType;
import com.expenditures.entity.Date;
import com.expenditures.entity.Expense;
import com.expenditures.model.services.AddExpense;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class);
    private static final String COMMAND_PARSER = "add\\s[12]\\d{3}[-](([0][1-9])|([1][0-2]))[-](([0][1-9])|([12][0-9])|([3][01]))\\s\\d+(\\.[\\d]{1,2})?\\s[A-Z]{3}\\s.{3,100}";
    private static final String ERROR_MESSAGE = "Command not found!\nPerhaps you wanted to say \"add yyyy-mm-dd xxxx CUR description\" ?";

    private AddExpense addExpense = new AddExpense();

    public AddCommand() {
    }

    public AddCommand(AddExpense addExpense) {
        this.addExpense = addExpense;
    }

    @Override
    public void execute(String enteredCommand) {
        String[] userInput;

        try {
            if (!checkCommand(enteredCommand, COMMAND_PARSER)) {
                throw new IncorrectCommandInputException(ERROR_MESSAGE);
            }

            userInput = getDataStrFromUserInput(enteredCommand);

            if (isCurrencyTypeIncorrect(userInput[3])) {
                throw new IncorrectCommandInputException(ERROR_MESSAGE);
            }

            Date date = new Date();
            date.setDateUsingStr(userInput[1]);
            Expense expense = new Expense();
            expense.setExpenseAmount(new BigDecimal(userInput[2]));
            expense.setExpenseCurrency(CurrencyType.getCurrencyType(userInput[3]));
            expense.setExpenseDescription(userInput[4]);
            expense.setDate(date);
            Set<Expense> expenseList = new HashSet<>();
            expenseList.add(expense);
            date.setExpenseSet(expenseList);

            addExpense.execute(date);
        } catch (IncorrectCommandInputException ie) {
            logger.info(ie);
            System.out.println(ERROR_MESSAGE);
        }
    }

    @Override
    public String[] getDataStrFromUserInput(String enteredCommand) {
        return enteredCommand.split("\\s", 5);
    }
}

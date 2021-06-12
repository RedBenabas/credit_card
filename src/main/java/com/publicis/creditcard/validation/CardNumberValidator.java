package com.publicis.creditcard.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CardNumberValidator implements
        ConstraintValidator<CardNumberConstraint, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String strippedSpaces = value.replaceAll("\\s+", "");
        boolean isValid = Pattern.matches("\\d{1,19}", strippedSpaces);
        return isValid && isChecksumValid(strippedSpaces);
    }

    private boolean isChecksumValid(String cardNumber)
    {
        int totalSum = 0;
        boolean evenIndexTracker = false;
        for (int index = cardNumber.length() - 1; index >= 0; index--)
        {
            int digitAtIndex = Integer.parseInt(cardNumber.substring(index, index + 1));
            if (evenIndexTracker)
            {
                digitAtIndex *= 2;
                if (digitAtIndex > 9)
                {
                    digitAtIndex -= 9;
                }
            }
            totalSum += digitAtIndex;
            evenIndexTracker = !evenIndexTracker;
        }
        return (totalSum % 10 == 0);
    }
}

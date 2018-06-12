/**
 * 
 */
package com.phr.ade.values;

/**
 * @author DS5002449
 * 
 */
public interface ICareBridgeConstants {
	final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|''(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*'')@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	// final String phoneRegex = "(^\\+[0-9]{3}([0-9]{9}$|[0-9\\-\\s]{10}$))";

	final String phoneRegex = "(^\\+[0-9]{1,3}([0-9]{9}$|[0-9\\-\\s]{10}$))";

	final String passwordRegex = "^[0-9a-zA-Z$!]+$";

	final String passwordRegexMsg = "Password : Use only Number(0-9),Alphabet(a-z,A-Z,$!) with no spaces";

	// Profile Purpose

	final Integer SELF_CARE = 1;
	final Integer CARING_FOR_SOMEONE = 2;
}

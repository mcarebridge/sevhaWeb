/**
 * 
 */

function makeLowerCase(elName) {
	var _str = "document.forms[0]." + elName + ".value";
	var _fieldValue = eval(_str);
	_fieldValue = _fieldValue.toLowerCase().trim();
	var _str1 = "document.forms[0]." + elName + ".value = _fieldValue";
	eval(_str1);
}

function trimSpaces(elName) {
	var _str = "document.forms[0]." + elName + ".value";
	var _fieldValue = eval(_str);
	_fieldValue = _fieldValue.trim();
	var _str1 = "document.forms[0]." + elName + ".value = _fieldValue";
	eval(_str1);
}
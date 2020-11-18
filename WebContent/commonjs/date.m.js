var regDDMMYYYY = '/^DDMMYYYY$/';
var regDD_MM_YYYY = '/^DD-MM-YYYY$/';
var regDateHours = /^([0-1][1-9]|2[0-9]|3[0-1])\d*(\-)(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\S*(\-)\d{4} \d{2}:\d{2}:\d{2}$/;
function parseDouble(value) {
	if (typeof value == 'string') {
		value = value.match(/^-?\d*/)[0];
	}
	return !isNaN(parseInt(value)) ? value * 1 : NaN;
}
function getMonthNo(Month) {
	switch (Month) {
	case "Jan":
		return 0;
	case "Feb":
		return 1;
	case "Mar":
		return 2;
	case "Apr":
		return 3;
	case "May":
		return 4;
	case "Jun":
		return 5;
	case "Jul":
		return 6;
	case "Aug":
		return 7;
	case "Sep":
		return 8;
	case "Oct":
		return 9;
	case "Nov":
		return 10;
	case "Dec":
		return 11;
	}
}
var dayarray = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
var montharray = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
		"Aug", "Sep", "Oct", "Nov", "Dec");
var monthNames = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
		"Sep", "Oct", "Nov", "Dec" ];
var monthNames1 = {
	Jan : 0,
	Feb : 1,
	Mar : 2,
	Apr : 3,
	May : 4,
	Jun : 5,
	Jul : 6,
	Aug : 7,
	Sep : 8,
	Oct : 9,
	Nov : 10,
	Dec : 11
};
function getDateTimeString(date) {
	return date.getDate() + "-" + monthNames[date.getMonth()] + "-"
			+ date.getFullYear() + " " + date.getHours() + ":"
			+ date.getMinutes() + ":" + date.getSeconds();
}
function getUserDate(stringDate) {
	var dd = stringDate.substring(0, 2);
	var mon = stringDate.substring(3, 6);
	var year = stringDate.substring(7, 11);
	return new Date(year, getMonthNo(mon), dd);
}
function getUserDateTime(stringDateTime) {
	var dd = stringDateTime.substring(0, 2);
	var mon = stringDateTime.substring(3, 6);
	var year = stringDateTime.substring(7, 11);
	var hh = stringDateTime.substring(12, 14);
	var mm = stringDateTime.substring(15, 17);
	var ss = stringDateTime.substring(18, 20);
	return new Date(year, getMonthNo(mon), dd, hh, mm, ss);
}
function dateTime() {
	var mydate = new Date();
	var year = mydate.getYear();
	if (year < 1000)
		year += 1900;
	var day = mydate.getDay();
	var month = mydate.getMonth();
	var daym = mydate.getDate();
	if (daym < 10)
		daym = "0" + daym;
	var hours = mydate.getHours();
	var minutes = mydate.getMinutes();
	var seconds = mydate.getSeconds();
	var dn = "AM";
	if (hours >= 12)
		dn = "PM";
	if (hours > 12) {
		hours = hours - 12;
	}
	if (hours == 0)
		hours = 12;
	if (minutes <= 9)
		minutes = "0" + minutes;
	if (seconds <= 9)
		seconds = "0" + seconds;
	var cdate = dayarray[day] + ", " + montharray[month] + " " + daym + ", "
			+ year + " " + hours + ":" + minutes + ":" + seconds + " " + dn;
	if (document.all)
		document.all.clock.innerHTML = cdate;
	else if (document.getElementById)
		document.getElementById("clock").innerHTML = cdate;
	else
		document.write(cdate);
}
if (!document.all && !document.getElementById)
	getthedate();
function timer() {
	if (document.all || document.getElementById)
		setInterval("dateTime()", 1000);
}
var monArray = new Array();
monArray[0] = "Jan";
monArray[1] = "Feb";
monArray[2] = "Mar";
monArray[3] = "Apr";
monArray[4] = "May";
monArray[5] = "Jun";
monArray[6] = "Jul";
monArray[7] = "Aug";
monArray[8] = "Sep";
monArray[9] = "Oct";
monArray[10] = "Nov";
monArray[11] = "Dec";
function getDD_MON_YYYY_Format(dateString) {
	if (dateString == null || dateString == 'null' || dateString == '')
		return '';
	var mn = (dateString.substr(5, 2) == '09') ? 8 : (parseInt(dateString
			.substr(5, 2)) - 1);
	var date = dateString.substr(8, 9) + "-" + monArray[mn] + "-"
			+ dateString.substr(0, 4);
	return date;
}
function getDD_MON_YYYY_Time_Format(dateString) {
	if (dateString == null || dateString == 'null' || dateString == '')
		return '';
	var mn = (dateString.substr(5, 2) == '09') ? 8 : (parseInt(dateString
			.substr(5, 2)) - 1);
	var date = dateString.substr(8, 9) + "-" + monArray[mn] + "-"
			+ dateString.substr(0, 4);
	return date;
}
String.prototype.lpad = function(padString, length) {
	var str = this;
	while (str.length < length)
		str = padString + str;
	return str;
}
String.prototype.rpad = function(padString, length) {
	var str = this;
	while (str.length < length)
		str = str + padString;
	return str;
}
function getAjaxRequestObj() {
	var ajaxRequest;
	try {
		ajaxRequest = new XMLHttpRequest();
	} catch (e) {
		try {
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				ajaxRequest = false;
			}
		}
	}
	return ajaxRequest;
}
'use strict';
var express = require('express');
var app = express()
var request = require('request');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

var messageList = {};
var messageUnseen = {};

/* Message */
var Message = function (to, from, message, lang) {
    this.to      = to;
    this.from    = from;
    this.message = message;
    this.lang    = lang;
    this.time    = new Date().getTime();
};

function addMessage(msg) {
	var user = msg.to;
	if (!messageList[user]) {
		messageList[user] = [];
	}
	messageList[user].push(msg);
}

function getMessages(username) {
	var list = messageList[username];
	if (list) {
		delete messageList[username];
		return list;
	}
}

function seenMessage() {

}

/* UserList */
var UserList = function () {
	this.users = {};
}

/* User */
var User = function (name, lang, number, email) {
    this.name   = name;
    this.lang   = lang || "en";
    this.number = number;
    this.email  = email;
};

/* UserList */
var UserList = function () {
	this.users = {};
}

UserList.prototype.addUser = function(name, lang) {
	var exists = this.has(name);
	if (!exists) {
		this.users[name] = new User(name, lang);
	}
	return !exists;
}

UserList.prototype.has = function(name) {
	return this.users[name] !== undefined;
}

UserList.prototype.get = function(name) {
	return this.users[name];
}


/* Room */
var Room = function (members, roomname) {
	this.members  = members;
	this.roomname = roomname;
}

Room.prototype.addUser = function(username) {
//    if (this.members["username"] != -1 && ) {
		
//	}
};

/* Push Notification */
function sendToUser() {

}

/* Register User */
function registerUser(name, lang) {
	return userList.addUser(name, lang);
}

/* Translation */
function sendMessage(msg, from, to) {
	if (msg && userList.has(from) && userList.has(to)) {
		var userFrom = userList.get(from);
		var userTo   = userList.get(to);
		var message = new Message(userTo.name, userFrom.name, msg, userFrom.lang);
		addMessage(message);
	}
}

function validLanguage(lang) {
	var langs = ["en","zh-CN","es","fr","pt"];
	return (langs.indexOf(lang) > -1);
}

function decode(text) {
	return decodeURIComponent(text);
}

var userList = new UserList();
function init() {
    registerUser("a");
    registerUser("b", "zh-CN");
    registerUser("c", "es");
}
init();


// 104.236.28.245:4730/register/Gibolt
// 104.236.28.245:4730/register/Modi
app.get('/register/:user/:lang', function(req, res) {
    res.type('application/json');
	var user = req.params.user;
	var lang = req.params.lang || "en";
	if (!user || !registerUser(user, lang)) {
		console.log("Failed to register: " + user);
		res.send({status:"failed"});
	}
	else {
		console.log("Registered: " + user);
		res.send({status:"success"});
	}
});

// 104.236.28.245:4730/send/Gibolt/Modi/Hello
app.get('/send/:from/:to/:msg', function(req, res) {
	res.set("Connection", "close");
	res.end();
	var msg  = decode(req.params.msg);
	var from = decode(req.params.from);
	var to   = decode(req.params.to);
	console.log("Sent " + msg + " from " + from + " to " + to);
	sendMessage(msg, from, to);
});

// 104.236.28.245:4730/get/Gibolt
// 104.236.28.245:4730/get/Modi
app.get('/get/:user', function(req, res) {
    res.type('application/json');
	var user = req.params.user;
	var msgs = getMessages(user) || [];
	console.log("Get: " + msgs);
	res.send(msgs);
});

// 104.236.28.245:4730/ping/Gibolt
app.get('/ping/:user', function(req, res) {
    res.type('application/json');
	var user = req.params.user;
	var time = new Date().getTime();
	ping(user);
});

app.listen(process.env.PORT || 4730, '0.0.0.0');

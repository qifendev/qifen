//验证码
var codeTimes;
var timeforCode;

function getCode() {
    codeTimes = 60;
    var me = this;
    var xmlHttp;
    var signEmail = document.getElementById("signEmail").value;
    if (window.XMLHttpRequest) {
        // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlHttp = new XMLHttpRequest();
    } else {
        // IE6, IE5 浏览器执行代码
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var signInfo = document.getElementById("signInfo");
            signInfo.style.visibility = "visible";
            setTimeout("hintInfo(signInfo)", 3000);
            var emailInfoText = xmlHttp.responseText;
            document.getElementById("signInfoText").innerHTML = emailInfoText;
            if ("发送验证码到邮箱成功" === emailInfoText) {
                timeforCode = me.setInterval("subCodeTime()", 1000);
                document.getElementById("codeButton").disabled = true;
            }
        }
    };
    xmlHttp.open("GET", "/signEmail?email=" + signEmail, true);
    xmlHttp.send();
}

function hintInfo(signInfo) {
    signInfo.style.visibility = "hidden";
}


function subCodeTime() {
    var codeRemain = codeTimes--;
    if (codeRemain === 0) {
        window.clearInterval(timeforCode);
        document.getElementById("codeButton").disabled = false;
        codeRemain = "";
    }
    document.getElementById("codeButton").innerHTML = "重新发送" + codeRemain;
}

//注册
function signPost() {
    var signEmail = document.getElementById("signEmail").value;
    var signCode = document.getElementById("signCode").value;
    var pas = document.getElementById("pas").value;
    var conPas = document.getElementById("conPas").value;
    var xmlHttp;
    if (window.XMLHttpRequest) {
        // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlHttp = new XMLHttpRequest();
    } else {
        // IE6, IE5 浏览器执行代码
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var signInfo = document.getElementById("signInfo");
            signInfo.style.visibility = "visible";
            setTimeout("hintInfo(signInfo)", 3000);
            document.getElementById("signInfo").innerHTML = xmlHttp.responseText;
        }
    };
    if (signEmail.length > 5 && signCode.length === 6 && pas.length === conPas.length && pas.length > 7) {
        xmlHttp.open("POST", "/sign?mail=" + signEmail + "&code=" + signCode + "&pas=" + pas + "&conPas=" + conPas, true);
    } else {
        var signInfo = document.getElementById("signInfo");
        signInfo.style.visibility = "visible";
        setTimeout("hintInfo(signInfo)", 3000);
        document.getElementById("signInfo").innerHTML = "输入格式有误";
    }
    xmlHttp.send();
}


function login() {
    var loginMail = document.getElementById("loginMail").value;
    var loginPas = document.getElementById("loginPas").value;

    var xmlHttp;
    if (window.XMLHttpRequest) {
        // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlHttp = new XMLHttpRequest();
    } else {
        // IE6, IE5 浏览器执行代码
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            var loginInfo = document.getElementById("loginInfo");
            loginInfo.style.visibility = "visible";
            setTimeout("hintInfo(loginInfo)", 3000);
            var info = xmlHttp.responseText;
            if (info === "登录成功") {
                window.location.href = "/";
            } else {
                loginInfo.innerHTML = info;
            }
        }
    };
    xmlHttp.open("POST", "/login?mail=" + loginMail + "&pas=" + loginPas, true);
    xmlHttp.send();
}











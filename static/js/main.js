/* Import here the rest of scripts - https://www.npmjs.com/package/bundle-js#usage */

// Text and html

window.escapeHTML = function escapeHTML(html) {
    return ("" + html).replace(/&/g, "&amp;").replace(/</g, "&lt;")
        .replace(/>/g, "&gt;").replace(/"/g, "&quot;")
        .replace(/'/g, "&apos;");
}

// Cookies

window.getCookie = function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
}

window.setCookie = function setCookie(name, value) {
    document.cookie = name + "=" + value + "; expires=2038-01-19 04:14:07; path=/";
}

// Language changing

window.chooseSiteLanguage = function chooseSiteLanguage(lang) {
    setCookie("locale", lang);
    location.reload();
};

// Get query params

window.getParameterByName = function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

// Prevent drops on document

$(document).ready(function () {
    $(document).on("drop", function (e) {
        e.preventDefault();
        e.stopPropagation();
    });

    $(document).on("dragover", function (e) {
        e.preventDefault();
        e.stopPropagation();
    });
});

// Pending request managment

window.PendingRequests = {};

PendingRequests.requests = {};
PendingRequests.timeouts = {};

PendingRequests.addPending = function (key, xhr) {
    this.requests[key] = xhr;
};

PendingRequests.abortPending = function (key) {
    if (this.requests[key]) {
        try {
            this.requests[key].abort();
        } catch (ex) { }
        this.requests[key] = null;
    }
};

PendingRequests.pending = function (key, xhr) {
    this.abortPending(key);
    this.requests[key] = xhr;
};

PendingRequests.addTimeout = function (key, timeout) {
    this.timeouts[key] = timeout;
};

PendingRequests.abortTimeout = function (key) {
    if (this.timeouts[key]) {
        try {
            clearTimeout(this.timeouts[key]);
        } catch (ex) { }
        this.timeouts[key] = null;
    }
};

PendingRequests.timeout = function (key, timeout) {
    this.abortTimeout(key);
    this.timeouts[key] = timeout;
};

PendingRequests.abortAll = function () {
    for (var key in this.requests) {
        this.abortPending(key);
    }
    for (var key in this.timeouts) {
        this.abortTimeout(key);
    }
}

window.scrollBodyUp = function () {
    $('body,html').animate({ scrollTop: 0 }, 200);
};

/* Translation / Multi-Languge utils */

var langData = {};

window.__ = function (key, value) {
    if (value === undefined) {
        if (langData[key] !== undefined) {
            return langData[key];
        } else {
            return key;
        }
    } else {
        langData[key] = value;
    }
};

// Prevent boostrap dropdowns from closing

$(document).on('click.bs.dropdown.data-api', '.dropdown.keep-inside-clicks-open', function (e) {
    e.stopPropagation();
});

$(document).ready(function () {
    // Your init code here
    
});

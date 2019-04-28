/* Import here the rest of scripts - https://www.npmjs.com/package/bundle-js#usage */

// Text and html

window.escapeHTML = function escapeHTML(html) {
    return ("" + html).replace(/&/g, "&amp;").replace(/</g, "&lt;")
        .replace(/>/g, "&gt;").replace(/"/g, "&quot;")
        .replace(/'/g, "&apos;");
}

window.isElementInViewport = function isElementInViewport (el) {

    //special bonus for those using jQuery
    if (typeof jQuery === "function" && el instanceof jQuery) {
        el = el[0];
    }

    var rect = el.getBoundingClientRect();

    return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && /*or $(window).height() */
        rect.right <= (window.innerWidth || document.documentElement.clientWidth) /*or $(window).width() */
    );
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
    
	// Register components
	vue_loadModalComponents();
	vue_loadFileCreationView();
	vue_loadCommentsComponents();
	vue_loadFileView();
	vue_loadFileListView();
	
	// VUE main app
	
	window.App = new Vue({
		el: '#vapp',
        data: {
        	userId: "",
        	userName: "",
        	isUser: false,
        	section: "home",
        	selectedFile: "",
        	sessionToken: "",
        },
        methods: {
        	goHome: function () {
        		if (this.section === "file" && !this.$refs.fileSection.saved) {
        			if (!confirm("Sus cambios se perderán, ¿esta seguro?")) {
        				return;
        			}
        		}
        		this.section = "home";
        		this.$refs.fileListSection.reload("/files/public");
        	},
        	goMyTextFiles: function () {
        		if (this.section === "file" && !this.$refs.fileSection.saved) {
        			if (!confirm("Sus cambios se perderán, ¿esta seguro?")) {
        				return;
        			}
        		}
        		this.section = "my-files";
        		this.$refs.fileListSection.reload("/files/own");
        	},
        	goCreateTextFile: function () {
        		if (this.section === "file" && !this.$refs.fileSection.saved) {
        			if (!confirm("Sus cambios se perderán, ¿esta seguro?")) {
        				return;
        			}
        		}
        		this.section = "create";
        	},
        	goTextFile: function (file) {
        		if (this.section === "file" && !this.$refs.fileSection.saved) {
        			if (!confirm("Sus cambios se perderán, ¿esta seguro?")) {
        				return;
        			}
        		}
        		PendingRequests.pending("load-file", $.get("/files/get", {id: file}, function (response) {
        			if (!response.success) {
        				console.log(JSON.stringify(response));
        				return;
        			}
        			this.section = "file";
        			this.selectedFile = file;
        			this.$refs.fileSection.saved = true;
        			this.$refs.fileSection.file = response.id;
        			this.$refs.fileSection.title = response.title;
        			this.$refs.fileSection.content = response.content;
        			this.$refs.fileSection.userId = response.userId;
        			this.$refs.fileSection.canEdit = response.userId === this.userId;
        			this.$refs.fileSection.reloadComments();
        		}.bind(this)));
        		
        	},
        	editFile: function () {
        		var title = this.$refs.fileSection.title;
        		var content = this.$refs.fileSection.content;
        		if (!title) {
        			this.$refs.fileSection.error = "Debes especificar un título para el texto.";
        			return;
        		}
        		if (title.length > 160) {
        			this.$refs.fileSection.error = "El título no debe ser mayor de 160 caracteres.";
        			return;
        		}
        		
        		PendingRequests.pending("edit-file", $.post("/files/edit", {id: this.selectedFile, title: title, content: content}, function (response) {
        			if (response.success) {
        				this.$refs.fileSection.saved = true;
        			} else {
        				this.$refs.fileCreateSection.error = "No se pudo editar el texto debido a un error inesperado. Compruebe que dispone de conexión a Internet.";
            			return;
        			}
        		}.bind(this)).fail(function () {
        			this.$refs.fileCreateSection.error = "No se pudo editar el texto debido a un error inesperado. Compruebe que dispone de conexión a Internet.";
        			return;
        		}.bind(this)));
        	},
        	showLogin: function () {
        		this.$refs.loginModal.show();
        	},
        	login: function() {
        		var username = this.$refs.loginModal.username;
        		var pw1 = this.$refs.loginModal.password;
        		
        		if (!username) {
        			this.$refs.loginModal.setError("Debe especificar un nombre de usuario.");
        			return;
        		}
        		
        		if (username.length > 80) {
        			this.$refs.loginModal.setError("El nombre de usuario no debe ser mayor que 80 caracteres.");
        			return;
        		}
        		
        		PendingRequests.pending("login", $.post("/login", {name: username, password: pw1, expiration: "no"}, function (response) {
					if (response.success) {
						this.$refs.loginModal.hide()
						this.isUser = true;
            			this.userId = response.userId;
            			this.userName = response.userName;
            			this.sessionToken = response.token;
            			this.$refs.fileSection.canEdit = this.$refs.fileSection.userId === this.userId;
            			this.$refs.fileSection.$refs.commentSection.canComment = true;
            			setCookie("session", this.sessionToken);
					} else {
						switch (response.errorCode) {
							case "USER_NOT_FOUND":
							case "INVALID_PASSWORD":
								this.$refs.loginModal.setError("Nombre de usuario o contraseña incorrectos.");
								break;
							default:
								this.$refs.loginModal.setError("Nombre de usuario o contraseña incorrectos.");
						}
					}
				}.bind(this)).fail(function () {
					this.$refs.loginModal.setError("No se pudo iniciar sesión. Compruebe que dispone de conexión a internet.");
				}.bind(this)));
        	},
        	showSignup: function () {
        		this.$refs.signupModal.show();
        	},
        	signup: function () {
        		var username = this.$refs.signupModal.username;
        		var pw1 = this.$refs.signupModal.password;
        		var pw2 = this.$refs.signupModal.passwordRepeat;
        		
        		if (!username) {
        			this.$refs.signupModal.setError("Debe especificar un nombre de usuario.");
        			return;
        		}
        		
        		if (username.length > 80) {
        			this.$refs.signupModal.setError("El nombre de usuario no debe ser mayor que 80 caracteres.");
        			return;
        		}
        		
        		if (!pw1) {
        			this.$refs.signupModal.setError("Debe especificar una contraseña para su cuenta.");
        			return;
        		}
        		
        		if (pw1 !== pw2) {
        			this.$refs.signupModal.setError("Debe introducir la misma contraseña 2 veces.");
        			return;
        		}
        		
        		PendingRequests.pending("register", $.post("/register", {name: username, password: pw1}, function (response) {
        			if (response.success) {
        				this.$refs.signupModal.hide()
        				PendingRequests.pending("login", $.post("/login", {name: username, password: pw1, expiration: "no"}, function (response) {
        					if (response.success) {
        						this.isUser = true;
                    			this.userId = response.userId;
                    			this.userName = response.userName;
                    			this.sessionToken = response.token;
                    			this.$refs.fileSection.canEdit = this.$refs.fileSection.userId === this.userId;
                    			this.$refs.fileSection.$refs.commentSection.canComment = true;
                    			setCookie("session", this.sessionToken);
        					}
        				}.bind(this)));
        			} else {
        				this.$refs.signupModal.setError("Ya existe un usuario con el nombre escogido.");
        			}
        		}.bind(this)).fail(function () {
        			this.$refs.signupModal.setError("No se pudo llevar a cabo el registro. Compruebe que dispone de conexión a internet.");
        		}.bind(this)));
        	},
        	showLogout: function () {
        		this.$refs.logoutModal.show();
        	},
        	logout: function () {
        		var token = this.sessionToken;
        		this.$refs.logoutModal.hide();
        		setCookie("session", "");
        		this.isUser = false;
    			this.userId = "";
    			this.userName = "";
    			this.sessionToken = "";
    			this.$refs.fileSection.$refs.commentSection.canComment = false;
    			this.goHome();
    			if (token) {
    				PendingRequests.pending("logout", $.post("/logout", {token: token}, function () {
    					return;
    				}));
    			}
        	},
        	postNewFile: function () {
        		var title = this.$refs.fileCreateSection.title;
        		var content = this.$refs.fileCreateSection.content;
        		if (!title) {
        			this.$refs.fileCreateSection.error = "Debes especificar un título para el texto.";
        			return;
        		}
        		if (title.length > 160) {
        			this.$refs.fileCreateSection.error = "El título no debe ser mayor de 160 caracteres.";
        			return;
        		}
        		
        		PendingRequests.pending("create-file", $.post("/files/create", {title: title, content: content}, function (response) {
        			if (response.success) {
        				this.goTextFile(response.id);
        				this.$refs.fileCreateSection.clear();
        			} else {
        				this.$refs.fileCreateSection.error = "No se pudo crear el texto debido a un error inesperado. Compruebe que dispone de conexión a Internet.";
            			return;
        			}
        		}.bind(this)).fail(function () {
        			this.$refs.fileCreateSection.error = "No se pudo crear el texto debido a un error inesperado. Compruebe que dispone de conexión a Internet.";
        			return;
        		}.bind(this)));
        	},
        	checkSession: function () {
        		if (this.sessionToken) {
        			PendingRequests.pending("session-check", $.get("/session", function (response) {
        				if (response.success) {
        					this.isUser = true;
                			this.userId = response.userId;
                			this.userName = response.userName;
                			this.$refs.fileSection.canEdit = this.$refs.fileSection.userId === this.userId;
                			this.$refs.fileSection.$refs.commentSection.canComment = true;
        				} else {
        					this.isUser = false;
                			this.userId = "";
                			this.userName = "";
                			this.$refs.fileSection.$refs.commentSection.canComment = false;
        				}
        			}.bind(this)));
        		} else {
        			this.isUser = false;
        			this.userId = "";
        			this.userName = "";
        			this.$refs.fileSection.$refs.commentSection.canComment = false;
        		}
        	},
        	init: function () {
        		this.sessionToken = getCookie("session") || "";
        		setInterval(this.checkSession.bind(this), 30000);
        		this.checkSession();
        	},
        }
	});
	
	App.init();
	App.$refs.fileListSection.init();
	App.goHome();
});

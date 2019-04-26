/* Modals components */

window.vue_loadModalComponents = function () {
    Vue.component('deletion-confirmation-modal', {
        data: function () {
            return {
            };
        },
        props: ['id', 'message'],
        methods: {
            show: function () {
                $("#" + this.id).modal("show");
            },
            hide: function () {
                $("#" + this.id).modal("hide");
            },
            confirm: function () {
                this.$emit("confirmation");
            }
        },
        template: '<div class="modal fade" v-bind:id="id" tabindex="-1" role="dialog" aria-hidden="true">'
        + '<div class="modal-dialog" role="document">'
        + '<div class="modal-content">'
        + '<div class="modal-header">'
        + '<h5 class="modal-title">' + __("Confirm deletion") + '</h5>'
        + '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
        + '<span aria-hidden="true">&times;</span>'
        + '</button>'
        + '</div>'
        + '<div class="modal-body" v-text="message"></div>'
        + '<div class="modal-footer">'
        + '<button type="button" class="btn btn-secondary" data-dismiss="modal">' + __("Cancel") + '</button>'
        + '<button type="button" class="btn btn-danger" v-on:click="confirm">' + __("Delete") + '</button>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>'
    });

    Vue.component('message-modal', {
        data: function () {
            return {
            };
        },
        props: ['id', 'title', 'message'],
        methods: {
            show: function () {
                $("#" + this.id).modal("show");
            },
            hide: function () {
                $("#" + this.id).modal("hide");
            },
        },
        template: '<div class="modal fade" v-bind:id="id" tabindex="-1" role="dialog" aria-hidden="true">'
        + '<div class="modal-dialog" role="document">'
        + '<div class="modal-content">'
        + '<div class="modal-header">'
        + '<h5 class="modal-title" v-text="title"></h5>'
        + '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
        + '<span aria-hidden="true">&times;</span>'
        + '</button>'
        + '</div>'
        + '<div class="modal-body" v-text="message"></div>'
        + '<div class="modal-footer">'
        + '<button type="button" class="btn btn-secondary" data-dismiss="modal">' + __("Done") + '</button>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>'
    });
    
    Vue.component('login-modal', {
        data: function () {
            return {
            	username: "",
            	password: "",
            	error: "",
            };
        },
        props: ['id'],
        methods: {
            show: function () {
                $("#" + this.id).modal("show");
            },
            hide: function () {
                $("#" + this.id).modal("hide");
                this.username = "";
                this.password = "";
                this.error = "";
            },
            login: function (event) {
            	this.error = "";
            	if (event) {
            		event.preventDefault();
            	}
            	this.$emit("login");
            },
            setError: function (error) {
            	this.error = error;
            },
        },
        template: '<div class="modal fade" v-bind:id="id" tabindex="-1" role="dialog" aria-hidden="true">'
        + '<div class="modal-dialog" role="document">'
        + '<div class="modal-content">'
        + '<div class="modal-header">'
        + '<h5 class="modal-title">Iniciar Sesión</h5>'
        + '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
        + '<span aria-hidden="true">&times;</span>'
        + '</button>'
        + '</div>'
        + '<div class="modal-body">'
        + '<form v-on:submit="login($event)">'
        + ''
        + '<div class="form-group">'
        + '<label>Nombre de usuario</label>'
        + '<input v-model="username" type="text" name="username" class="form-control" placeholder="Usuario">'
        + '</div>'
        + '<div class="form-group">'
        + '<label>Contraseña</label>'
        + '<input v-model="password" type="password" name="password" class="form-control" placeholder="Contraseña">'
        + '</div>'
        + ''
        + '<small class="error-modal" v-text="error"></small>'
        + '</form>'
        + '</div>'
        + '<div class="modal-footer">'
        + '<button type="button" class="btn btn-primary" v-on:click="login">' + __("Iniciar Sesión") + '</button>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>'
    });
    
    Vue.component('logout-modal', {
        data: function () {
            return {
            };
        },
        props: ['id'],
        methods: {
            show: function () {
                $("#" + this.id).modal("show");
            },
            hide: function () {
                $("#" + this.id).modal("hide");
            },
            logout: function (event) {
            	if (event) {
            		event.preventDefault();
            	}
            	this.$emit("logout");
            },
        },
        template: '<div class="modal fade" v-bind:id="id" tabindex="-1" role="dialog" aria-hidden="true">'
        + '<div class="modal-dialog" role="document">'
        + '<div class="modal-content">'
        + '<div class="modal-header">'
        + '<h5 class="modal-title">Cerrar Sesión</h5>'
        + '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
        + '<span aria-hidden="true">&times;</span>'
        + '</button>'
        + '</div>'
        + '<div class="modal-body">'
        + '<p>¿Desea realmente cerrar sesión?</p>'
        + '</div>'
        + '<div class="modal-footer">'
        + '<button type="button" class="btn btn-secondary" v-on:click="hide">' + __("Cancelar") + '</button>'
        + '<button type="button" class="btn btn-primary" v-on:click="logout">' + __("Cerrar Sesión") + '</button>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>'
    });
    
    Vue.component('signup-modal', {
        data: function () {
            return {
            	username: "",
            	password: "",
            	passwordRepeat: "",
            	error: "",
            };
        },
        props: ['id'],
        methods: {
            show: function () {
                $("#" + this.id).modal("show");
            },
            hide: function () {
                $("#" + this.id).modal("hide");
                this.username = "";
                this.password = "";
                this.passwordRepeat = "";
                this.error = "";
            },
            signup: function (event) {
            	if (event) {
            		event.preventDefault();
            	}
            	this.$emit("signup");
            },
            setError: function (error) {
            	this.error = error;
            },
        },
        template: '<div class="modal fade" v-bind:id="id" tabindex="-1" role="dialog" aria-hidden="true">'
            + '<div class="modal-dialog" role="document">'
            + '<div class="modal-content">'
            + '<div class="modal-header">'
            + '<h5 class="modal-title">Registrarse</h5>'
            + '<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
            + '<span aria-hidden="true">&times;</span>'
            + '</button>'
            + '</div>'
            + '<div class="modal-body">'
            + '<form v-on:submit="signup($event)">'
            + ''
            + '<div class="form-group">'
            + '<label>Nombre de usuario</label>'
            + '<input v-model="username" type="text" name="username" class="form-control" placeholder="Usuario">'
            + '</div>'
            + '<div class="form-group">'
            + '<label>Contraseña</label>'
            + '<input v-model="password" type="password" name="password" class="form-control" placeholder="Contraseña">'
            + '</div>'
            + '<div class="form-group">'
            + '<label>Repista su contraseña</label>'
            + '<input v-model="passwordRepeat" type="password" name="password" class="form-control" placeholder="Contraseña">'
            + '</div>'
            + ''
            + '<small class="error-modal" v-text="error"></small>'
            + '</form>'
            + '</div>'
            + '<div class="modal-footer">'
            + '<button type="button" class="btn btn-primary" v-on:click="signup">' + __("Registrarse") + '</button>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '</div>'
    });
};

/* Comments */

window.vue_loadCommentsComponents = function () {
	Vue.component('comments-section', {
        data: function () {
            return {
            	file: "",
            	loading: false,
            	page: 0,
            	totalPages: 1,
            	total: 0,
            	itemsPage: 10,
            	elements: [],
            	message: "",
            	error: "",
            	canComment: false,
            };
        },
        props: [],
        methods: {
            reload: function () {
            	this.loading = false;
        		this.page = 0;
        		this.totalPages = 1;
        		this.elements = [];
        		this.loadNextPage();
            },
            loadNextPage: function () {
        		if (this.loading) return;
        		if (this.page >= this.totalPages) {
        			return;
        		}
        		this.loading = true;
        		PendingRequests.pending("list-comments", $.get("/comments/find", {file: this.file, page: this.page, items: this.itemsPage}, function (response) {
        			this.loading = false;
        			this.page = response.page + 1;
        			this.totalResults = response.page;
        			this.totalPages = response.page;
        			for (var i = 0; i < response.items.length; i++) {
        				this.elements.push(response.items[i]);
        			}
        		}.bind(this)).fail(function () {
        			this.loading = false;
        		}.bind(this)));
        	},
        	post: function () {
            	this.error = "";
            	if (event) {
            		event.preventDefault();
            	}
            	this.$emit("post");
            },
            init: function () {
            	setInterval(function () {
            		if (!this.display) return;
            		if (!isElementInViewport($("#list-load-marker"))) {
            			return;
            		}
            		this.loadNextPage();
            	}.bind(this), 500);
            },
        },
        template: '<div>'
        	+ '<form v-on:submit="post($event)" v-if="canComment">'
            + '<div class="form-group">'
            + '<label>Deja un comentario</label>'
            + '<textarea v-model="message" class="form-control" rows="5"></textarea>'
            + '</div>'
        	+ '<div class="form-group">'
        	+ '<small class="error-label" v-text="error"></small>'
        	+ '</div>'
        	+ '<button type="submit" class="btn btn-primary">Publicar comentario</button>'
        	+ '</form>'
        	+ '<hr>'
        	+ '<div v-for="elem in elements" class="row comment-list-row">'
        	+ '<div class="col-md-12 card comment-list-card">'
        	+ '<div class="card-body">'
        	+ '<h5 class="card-title" v-text="elem.userName"></h5>'
        	+ '<h6 class="card-subtitle mb-2 text-muted" v-text="elem.date"></h6>'
        	+ '<p class="card-text" v-text="elem.message"></p>'
        	+ '</div>'
        	+ '</div>'
        	+ '</div>'
        	+ '<h4 class="text-center" v-if="elements.length === 0"><span v-if="!loading">No hay ningún comentario que mostrar</span><span v-if="loading">Cargando...</span></h4>'
        	+ '<div id="list-load-marker">&nbsp;</div>'
        	+ '</div>'
    });
};


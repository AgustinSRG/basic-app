/* File list */

window.vue_loadFileListView = function () {
	Vue.component('file-list-section', {
        data: function () {
            return {
            	loading: false,
            	url: "",
            	page: 0,
            	totalPages: 1,
            	total: 0,
            	itemsPage: 10,
            	elements: [],
            };
        },
        props: ['display'],
        methods: {
        	reload: function (url) {
        		this.url = url;
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
        		PendingRequests.pending("list-files", $.get(this.url, {page: this.page, items: this.itemsPage}, function (response) {
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
            init: function () {
            	setInterval(function () {
            		if (!this.display) return;
            		if (!isElementInViewport($("#list-load-marker"))) {
            			return;
            		}
            		this.loadNextPage();
            	}.bind(this), 500);
            },
            select: function (file) {
            	App.goTextFile(file);
            },
        },
        template: '<div v-bind:class="{\'d-none\': !display}">'
        	+ '<div v-for="elem in elements" class="row file-list-row">'
        	+ '<div class="col-md-12 card file-list-card" v-on:click="select(elem.id)">'
        	+ '<div class="card-body">'
        	+ '<h5 class="card-title" v-text="elem.title"></h5>'
        	+ '<h6 class="card-subtitle mb-2 text-muted" v-text="elem.userName"></h6>'
        	+ '<p class="card-text" v-text="elem.creationDate"></p>'
        	+ '</div>'
        	+ '</div>'
        	+ '</div>'
        	+ '<h3 class="text-center" v-if="elements.length === 0"><span v-if="!loading">No hay ningún texto que mostrar</span><span v-if="loading">Cargando...</span></h3>'
        	+ '<div id="list-load-marker">&nbsp;</div>'
        	+ '</div>'
    });
};


dependencies = [
    'ngRoute',
    'ui.bootstrap',
    'myApp.filters',
    'myApp.services',
    'myApp.controllers',
    'myApp.directives',
    'myApp.common',
    'myApp.routeConfig'
]

app = angular.module('myApp', dependencies)

angular.module('myApp.routeConfig', ['ngRoute'])
    .config(
        ['$routeProvider', ($routeProvider) ->
            $routeProvider
                .when('/', {templateUrl: '/view'})
                .when('/persons/create', {templateUrl: '/assets/partials/create.html'})
                .when('/persons/edit/:id', {templateUrl: '/assets/partials/update.html'})
                .when('/entity/create', {templateUrl: '/entity/create'})
                .when('/entity/edit/:id', {templateUrl: '/entity/update'})
                .otherwise({redirectTo: '/'})
        ]
    )
    .config(
        ['$locationProvider', ($locationProvider) ->
            $locationProvider.html5Mode(
                {enabled: true, requireBase: false}
            )
        ]
    )

@commonModule = angular.module('myApp.common', [])
@controllersModule = angular.module('myApp.controllers', [])
@servicesModule = angular.module('myApp.services', [])
@modelsModule = angular.module('myApp.models', [])
@directivesModule = angular.module('myApp.directives', [])
@filtersModule = angular.module('myApp.filters', [])
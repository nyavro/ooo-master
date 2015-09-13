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
                .when('/', {templateUrl: '/assets/partials/view.html'})
                .when('/persons/create', {templateUrl: '/assets/partials/create.scala.html'})
                .when('/persons/edit/:id', {templateUrl: '/assets/partials/update.html'})
                .when('/entity/create', {templateUrl: '/entity/create'})
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
routerApp = angular.module('routerApp', ['ui.router']);

routerApp.config(
    [($stateProvider, $urlRouterProvider) ->
        $urlRouterProvider.otherwise('/home')
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'partial-home.html'
        }).state('about', {
        })

    ]
)

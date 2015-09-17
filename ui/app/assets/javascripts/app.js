var routerApp = angular.module('routerApp', ['ui.router']);

routerApp.config(
    function($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');
        $stateProvider
        // HOME STATES AND NESTED VIEWS ========================================
        .state('home', {
                url: '/home',
                templateUrl: 'partial_home'
            }
        )
        // ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
        .state('about', {
            // we'll get to this in a bit
            }
        );
    }
).config([
        '$controllerProvider', function($controllerProvider) {
            $controllerProvider.allowGlobals();
        }
    ]
);

function EmptyController($scope) {
    $scope.message = "hello, man!";
}
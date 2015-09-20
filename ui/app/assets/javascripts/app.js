var dependencies = [
    'ui.router',
    'app.servicesModule',
    'app.controllers',
    'app.directives',
    'app.common '
];
var app = angular.module('app', dependencies);

app.config(
    function($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');
        $stateProvider
            .state('home', {
                    url: '/home',
                    templateUrl: 'partial_home'
                }
            )
            .state('about', {
                    url: '/about',
                    templateUrl: 'partial_about'
                }
            );
    }
).config([
        '$controllerProvider', function($controllerProvider) {
            $controllerProvider.allowGlobals();
        }
    ]
);

servicesModule = angular.module('app.servicesModule', []);
controllersModule = angular.module('app.controllers', []);
directivesModule = angular.module('app.directives', []);
commonModule = angular.module('app.common', []);
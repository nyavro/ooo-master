dependencies = [
    'ui.router',
    'app.servicesModule',
    'app.controllers',
    'app.directives',
    'app.common'
]
angular.module('app', dependencies)
  .config(
    ($stateProvider, $urlRouterProvider) ->
      $urlRouterProvider.otherwise('/home')
      $stateProvider
        .state('home', {url: '/home', templateUrl: 'partial_home'})
        .state('about', {url: '/about', templateUrl: 'partial_about'})
        .state('createEntity', {url: '/entity.create', templateUrl: 'entity/createView'})
        .state('listEntity', {url: '/entity.list', templateUrl: 'entity/listView'})
        .state('editEntity', {url: '/entity.update/:id', templateUrl: 'entity/updateView'})
  )
@servicesModule = angular.module('app.servicesModule', [])
@controllersModule = angular.module('app.controllers', [])
@directivesModule = angular.module('app.directives', [])
@commonModule = angular.module('app.common', [])
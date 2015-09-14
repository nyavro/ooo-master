class UpdateEntityCtrl

  constructor: (@$log, @$location, @$routeParams, @EntityService) ->
      @$log.debug "constructing UpdateEntityController"
      @entity = {}
      @find()

  update: () ->
      @$log.debug "update Entity"
      @EntityService.update(@$routeParams.id, @entity)
      .then(
          (data) =>
            @$log.debug "Promise returned #{data} Entity"
            @entity = data
            @$location.path("/")
        ,
        (error) =>
            @$log.error "Unable to update Entity: #{error}"
      )

  find: () ->
      id = @$routeParams.id
      @$log.debug "findEntity route params: #{id}"

      @EntityService.load(id)
        .then(
          (data) =>
            @$log.debug "Promise returned entity"
            @entity = data
          ,
          (error) =>
            @$log.error "Unable to get Entity: #{error}"
        )

controllersModule.controller('UpdateEntityCtrl', ['$log', '$location', '$routeParams', 'EntityService', UpdateEntityCtrl])
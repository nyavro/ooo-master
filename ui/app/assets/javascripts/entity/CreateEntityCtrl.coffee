class CreateEntityCtrl
    constructor: (@$log, @$location,  @EntityService) ->
        @$log.debug "constructing CreateEntityController"
        @entity = {}

    create: () ->
        @$log.debug "create Entity"
        @EntityService.create(@entity)
        .then(
            (data) =>
                @$log.debug "Promise returned #{data} Entity"
                @entity = data
                @$location.path("/")
            ,
            (error) =>
                @$log.error "Unable to create Entity: #{error}"
        )

controllersModule.controller('CreateEntityCtrl', ['$log', '$location', 'EntityService', CreateEntityCtrl])
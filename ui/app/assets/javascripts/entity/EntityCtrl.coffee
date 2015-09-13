class EntityCtrl

    @name = "Entity"

    constructor: (@$log, @EntityService, @$location) ->
        @$log.debug "constructing #{name} Controller"
        @entities = []
        @loadAll(1)

    loadAll: (clientId) ->
        @$log.debug "loadAll #{name} of #{clientId}"
        @EntityService
            .list(clientId)
            .then(
                (data) =>
                    @$log.debug "Promise returned #{data.length} #{name}"
                    @entities = data
                ,
                (error) =>
                    @$log.error "Unable to get #{name}: #{error}"
            )

    delete: (id) ->
        @$log.debug "delete #{name} #{id}"
        @EntityService
            .delete(id)
            .then(
                (data) =>
                    @$log.debug "Promise returned #{data} #{name}"
                    @entities = @entities.filter (v) -> v.id != id
                    @$location.path("/")
                ,
                (error) => @$log.error "Unable to delete #{name}: #{error}"
            )

controllersModule.controller('EntityCtrl', ['$log', 'EntityService', '$location', EntityCtrl])
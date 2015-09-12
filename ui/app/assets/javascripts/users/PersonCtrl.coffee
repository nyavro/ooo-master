class PersonCtrl

    constructor: (@$log, @PersonService, @$location) ->
        @$log.debug "constructing PersonController"
        @persons = []
        @getAllPersosns()

    getAllPersosns: () ->
        @$log.debug "getAllPersons()"
        @PersonService
            .listPersons()
            .then(
                (data) =>
                    @$log.debug "Promise returned #{data.length} Persons"
                    @persons = data
                ,
                (error) =>
                    @$log.error "Unable to get Persons: #{error}"
            )

    delete: (id) ->
        @$log.debug "delete person #{id}"
        @PersonService
            .delete(id)
            .then(
                (data) =>
                    @$log.debug "Promise returned #{data} Person"
                    @persons = @persons.filter (v) -> v.id != id
                    @$location.path("/")
                ,
                (error) => @$log.error "Unable to delete Person: #{error}"
            )

controllersModule.controller('PersonCtrl', ['$log', 'PersonService', '$location', PersonCtrl])
class PersonCtrl

    constructor: (@$log, @PersonService) ->
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
controllersModule.controller('PersonCtrl', ['$log', 'PersonService', PersonCtrl])
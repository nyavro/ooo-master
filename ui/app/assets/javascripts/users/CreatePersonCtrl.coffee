class CreatePersonCtrl

    constructor: (@$log, @$location,  @PersonService) ->
        @$log.debug "constructing CreatePersonsController"
        @person = {}

    createPerson: () ->
        @$log.debug "createPerson()"
        @PersonService.createPerson(@person)
        .then(
            (data) =>
                @$log.debug "Promise returned #{data} Person"
                @person = data
                @$location.path("/")
            ,
            (error) =>
                @$log.error "Unable to create Person: #{error}"
            )

controllersModule.controller('CreatePersonCtrl', ['$log', '$location', 'PersonService', CreatePersonCtrl])
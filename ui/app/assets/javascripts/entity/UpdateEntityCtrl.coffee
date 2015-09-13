class UpdatePersonCtrl

  constructor: (@$log, @$location, @$routeParams, @PersonService) ->
      @$log.debug "constructing UpdatePersonController"
      @person = {}
      @findPerson()

  updatePerson: () ->
      @$log.debug "updatePerson()"
      @PersonService.updatePerson(@$routeParams.id, @person)
      .then(
          (data) =>
            @$log.debug "Promise returned #{data} Person"
            @person = data
            @$location.path("/")
        ,
        (error) =>
            @$log.error "Unable to update Person: #{error}"
      )

  findPerson: () ->
      id = @$routeParams.id
      @$log.debug "findPerson route params: #{id}"

      @PersonService.load(id)
        .then(
          (data) =>
            @$log.debug "Promise returned person"
            @person = data
          ,
          (error) =>
            @$log.error "Unable to get Person: #{error}"
        )

controllersModule.controller('UpdatePersonCtrl', ['$log', '$location', '$routeParams', 'PersonService', UpdatePersonCtrl])
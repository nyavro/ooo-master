class PersonService
  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = { headers: @headers }

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing PersonService"

  listPersons: () ->
    @$log.debug "listPersons()"
    deferred = @$q.defer()
    @$http.get("/persons")
    .success((data, status, headers) =>
      @$log.info("Successfully listed Persons - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to list Persons - status #{status}")
      deferred.reject(data)
    )
    deferred.promise

  createPerson: (person) ->
    @$log.debug "createPerson #{angular.toJson(person, true)}"
    deferred = @$q.defer()

    @$http.post('/person', person)
    .success((data, status, headers) =>
      @$log.info("Successfully created Person - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to create Person - status #{status}")
      deferred.reject(data)
    )
    deferred.promise

  updatePerson: (name, last, middle, person) ->
    @$log.debug "updatePerson #{angular.toJson(person, true)}"
    deferred = @$q.defer()

    @$http.put("/person/#{firstName}/#{lastName}", person)
    .success((data, status, headers) =>
      @$log.info("Successfully updated Person - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, header) =>
      @$log.error("Failed to update person - status #{status}")
      deferred.reject(data)
    )
    deferred.promise

servicesModule.service('PersonService', ['$log', '$http', '$q', PersonService])
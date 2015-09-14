class EntityService
  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = { headers: @headers }
  @name = "Entity"
  @controllerDomain = "entity"

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing #{name}Service"

  list: (clientId) ->
    @$log.debug "#{name}:list #{clientId}"
    deferred = @$q.defer()
    @$http.get("/entities/list/#{clientId}")
      .success((data, status, headers) =>
        @$log.info("Successfully listed #{name} - status #{status}")
        deferred.resolve(data)
      )
      .error((data, status, headers) =>
        @$log.error("Failed to list #{name} - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

  create: (entity) ->
    @$log.debug "#{name}:create #{angular.toJson(entity, true)}"
    deferred = @$q.defer()
    @$http.post('/entities', entity)
      .success((data, status, headers) =>
        @$log.info("Successfully created #{name} - status #{status}")
        deferred.resolve(data)
      )
      .error((data, status, headers) =>
        @$log.error("Failed to create #{name} - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

  load: (id) ->
    deferred = @$q.defer()
    @$log.debug "#{name}:load #{id}"
    @$http.get("/entities/#{id}")
      .success((data, status, headers) =>
        @$log.info("#{name} loaded - status #{status}")
        deferred.resolve(data)
      )
      .error((data, status, headers) =>
        @$log.error("Failed to load #{name} - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

  update: (id, entity) ->
    @$log.debug "#{name}:update #{angular.toJson(entity, true)}"
    deferred = @$q.defer()
    @$http.put("/entities/#{id}", entity)
      .success((data, status, headers) =>
        @$log.info("Successfully updated #{name} - status #{status}")
        deferred.resolve(data)
      )
      .error((data, status, header) =>
        @$log.error("Failed to update #{name} - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

  delete: (id) ->
    @$log.debug "#{name}:delete #{id}"
    deferred = @$q.defer()
    @$http.delete("/entities/#{id}")
    .success((data, status, headers) =>
      @$log.info("Successfully deleted #{name} - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, header) =>
      @$log.error("Failed to delete #{name} - status #{status}")
      deferred.reject(data)
    )
    deferred.promise

servicesModule.service('EntityService', ['$log', '$http', '$q', EntityService])
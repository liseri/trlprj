(function() {
    'use strict';
    angular
        .module('trlprjApp')
        .factory('Project', Project);

    Project.$inject = ['$resource', 'DateUtils'];

    function Project ($resource, DateUtils) {
        var resourceUrl =  'api/projects/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                        data.completeTime = DateUtils.convertDateTimeFromServer(data.completeTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

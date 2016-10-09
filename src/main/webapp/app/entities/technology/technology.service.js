(function() {
    'use strict';
    angular
        .module('trlprjApp')
        .factory('Technology', Technology);

    Technology.$inject = ['$resource'];

    function Technology ($resource) {
        var resourceUrl =  'api/technologies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

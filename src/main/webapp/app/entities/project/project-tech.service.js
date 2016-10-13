(function() {
    'use strict';
    angular
        .module('trlprjApp')
        .factory('ProjectTech', ProjectTech);

    ProjectTech.$inject = ['$resource'];

    function ProjectTech ($resource) {
        var resourceUrl =  'api/projects/:id/tech/:techId';

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

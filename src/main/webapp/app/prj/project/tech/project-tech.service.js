(function() {
    'use strict';
    angular
        .module('trlprjApp')
        .factory('ProjectTech', ProjectTech);

    ProjectTech.$inject = ['$resource'];

    function ProjectTech ($resource) {
        var resourceUrl =  'api/myprj/:id/tech/:techId';

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
            'update': { method:'PUT' },
            'changeKey': {
                url: 'api/myprj/:id/tech/:techId/key/:isKey',
                method: 'POST'
            },
            'addSubcreator': {
                url: 'api/myprj/:id/tech/:techId/subcreator/:userLogin',
                method: 'POST',
                isArray: true
            },
            'removeSubcreator': {
                url: 'api/myprj/:id/tech/:techId/subcreator/:userFullName',
                method: 'DELETE',
                isArray: true
            }

        });
    }


})();

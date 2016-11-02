(function() {
    'use strict';
    angular
        .module('trlprjApp')
        .factory('TechTree', TechTree);

    TechTree.$inject = ['$resource'];

    function TechTree ($resource) {
        var resourceUrl =  'api/myprj/:prjId/techtree';

        return $resource(resourceUrl, {}, {
            // 'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }


})();

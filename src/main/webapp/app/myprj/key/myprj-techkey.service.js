(function() {
    'use strict';
    angular
        .module('trlprjApp')
        .factory('TechKey', TechKey);

    TechKey.$inject = ['$resource'];

    function TechKey ($resource) {
        var resourceUrl =  'api/keytech/:techId/type/:type';

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
            },
            'update': { method:'PUT' }
        });
    }


})();

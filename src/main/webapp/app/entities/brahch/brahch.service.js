(function() {
    'use strict';
    angular
        .module('trlprjApp')
        .factory('Brahch', Brahch);

    Brahch.$inject = ['$resource'];

    function Brahch ($resource) {
        var resourceUrl =  'api/brahches/:id';

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

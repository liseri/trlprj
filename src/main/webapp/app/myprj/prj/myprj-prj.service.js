(function() {
    'use strict';
    angular
        .module('trlprjApp')
        .factory('MyProject', MyProject);

    MyProject.$inject = ['$resource'];

    function MyProject ($resource) {
        var resourceUrl =  'api/myprj/:id';

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
            }
        });
    }


})();

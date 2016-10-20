(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .factory('User', User);

    User.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'queryAllTrlers': {url:'api/users/allTrlers', method: 'GET', isArray: true},
            'queryAllEvlers': {url:'api/users/allEvlers', method: 'GET', isArray: true},
            'queryAllGeneralUsers': {url:'api/users/allGeneralUsers', method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'}
        });

        return service;
    }
})();

(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('prj', {
            abstract: true,
            parent: 'app'
        });
    }
})();

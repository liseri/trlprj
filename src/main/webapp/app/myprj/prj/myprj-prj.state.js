(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
            $stateProvider.state('prj', {
                parent: 'myprj',
                url: '/prj',
                views: {
                    'prjContent@myprj': {
                        templateUrl: 'app/myprj/prj/myprj-prj.html',
                        controller: 'MyprjPrjController',
                        controllerAs: 'vm'
                    }
                }
            });
    }
})();

(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('myprj', {
            parent: 'app',
            url: '/myprj',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trlprjApp.project.myprj.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/myprj/myprj.html',
                    controller: 'MyprjController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('project');
                    $translatePartialLoader.addPart('technology');
                    $translatePartialLoader.addPart('tCL');
                    $translatePartialLoader.addPart('tRL');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }
})();

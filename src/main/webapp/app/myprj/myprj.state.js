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
                authorities: ['ROLE_USER', 'ROLE_TRL', 'ROLE_EVL', 'ROLE_ADMIN'],
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
                    $translatePartialLoader.addPart('prjStatus');
                    $translatePartialLoader.addPart('technology');
                    $translatePartialLoader.addPart('tCL');
                    $translatePartialLoader.addPart('tRL');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        }).state('prj', {
            parent: 'myprj',
            url: '/prj',
            views: {
                'prjContent@myprj': {
                    templateUrl: 'app/myprj/prj/myprj-prj.html',
                    controller: 'MyprjPrjController',
                    controllerAs: 'vm'
                }
            }
        }).state('techtree', {
            parent: 'prj',
            url: '/{id}/techtree',
            views: {
                'techContent@myprj': {
                    templateUrl: 'app/myprj/techtree/myprj-techtree.html',
                    controller: 'MyprjTechTreeController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();

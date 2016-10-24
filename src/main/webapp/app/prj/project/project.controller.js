(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['$scope', '$state', '$uibModal', 'Project', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function ProjectController($scope, $state, $uibModal, Project, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.start = start;
        vm.pause = pause;
        vm.restart = restart;
        vm.complete = complete;
        vm.openRootTechModal = openRootTechModal;
        vm.openTrlerMgrModal = openTrlerMgrModal;
        vm.openEvlerMgrModal = openEvlerMgrModal;

        loadAll();

        var unsubscribe = $scope.$on('trlprjApp:rootTechUpdated', function(event, result) {
            loadAll();
        });
        $scope.$on('$destroy', unsubscribe);

        function loadAll() {
            Project.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.projects = data;
                vm.page = pagingParams.page;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function start(id) {
            Project.start({id: id},
                function () {
                    loadAll();
                });
        }
        function pause(id) {
            Project.pause({id: id},
                function () {
                    loadAll();
                });
        }
        function restart(id) {
            Project.start({id: id},
                function () {
                    loadAll();
                });
        }
        function complete(id) {
            if (!onfirm("完成后项目将彻底关闭，你确定要“完成”吗？"))
                return;
            Project.complete({id: id},
                function () {
                    loadAll();
                });
        }

        function openRootTechModal(project) {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/prj/project/tech/project-tech-dialog.html',
                controller: 'ProjectTechDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: project
                }
            });
        }

        function openTrlerMgrModal(project) {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/prj/project/trler/project-trler-dialog.html',
                controller: 'ProjectTrlerDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: project
                }
            }).result.then(function () {
                $state.go('project', null, {reload: 'project'});
            }, function () {
                $state.go('^');
            });;
        }

        function openEvlerMgrModal(project) {


            var modalInstance = $uibModal.open({
                templateUrl: 'app/prj/project/evler/project-evler-dialog.html',
                controller: 'ProjectEvlerDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: project
                }
            }).result.then(function () {
                $state.go('project', null, {reload: 'project'});
            }, function () {
                $state.go('^');
            });;
        }
    }
})();

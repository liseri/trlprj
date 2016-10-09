(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('BrahchController', BrahchController);

    BrahchController.$inject = ['$scope', '$state', 'Brahch'];

    function BrahchController ($scope, $state, Brahch) {
        var vm = this;
        
        vm.brahches = [];

        loadAll();

        function loadAll() {
            Brahch.query(function(result) {
                vm.brahches = result;
            });
        }
    }
})();

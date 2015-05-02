angular.module('hypotheekApp', [])
    .controller('HypotheekController', '$http', function($http) {
        var self = this;
        var container = document.getElementById('visualization');
        var dataset = new vis.DataSet([]);
        var options = {
            //start: '2014-06-10',
            //end: '2014-06-18'
        };
        new vis.Graph2d(container, dataset, options);

        self.initialize = function() {
            $http.get('localhost:8080/api/lasten').then(function(data) {
                dataset.clear();
                for (var i = 0; i < data.length; i++) {
                    var obj = data[i];
                    dataset.add({
                        x: i,
                        y: obj.bruto,
                        group: 1
                    });
                    dataset.add({
                        x: i,
                        y: obj.netto,
                        group: 2
                    });
                }
            });
        };

        self.initialize();
    });
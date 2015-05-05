angular.module('hypotheekApp', []).controller('HypotheekController', ['$http', '$location', function($http, $location) {
    var self = this;
    self.model = {
        hypotheeksom: 260000,
        wozwaarde: 255000
    };
    var ctx = document.getElementById("chart").getContext("2d");
    var chart = undefined;

    self.initialize = function() {
        self.redraw();
    };

    self.redraw = function() {
        if (chart) {
            chart.destroy();
            chart = undefined;
        }
        var url = $location.absUrl() + 'api/lasten';
        var options = {
            params: self.model
        };
        $http.get(url, options).then(function(response) {
            var data = response.data;

            var labels = [];
            var brutoData = [];
            var nettoData = [];
            var year = 2016;
            for (var i = 0; i < data.length; i++) {
                var obj = data[i];
                if (i%12 == 0) {
                    labels.push(year);
                    year++;
                }
                else {
                    labels.push('');
                }
                brutoData.push(obj.bruto);
                nettoData.push(obj.netto);
            }

            var chartData = {
                labels: labels,
                datasets: [
                    {
                        label: "Bruto lasten",
                        fillColor: "rgba(255,190,150,0.0)",
                        strokeColor: "rgba(255,190,150,1)",
                        pointColor: "rgba(255,190,150,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(255,190,200,1)",
                        data: brutoData
                    },
                    {
                        label: "My Second dataset",
                        fillColor: "rgba(151,187,205,0.0)",
                        strokeColor: "rgba(151,187,205,1)",
                        pointColor: "rgba(151,187,205,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(151,187,205,1)",
                        data: nettoData
                    }
                ]
            };
            chart = new Chart(ctx).Line(chartData);
        });
    };

    self.initialize();
}]);

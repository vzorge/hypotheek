angular.module('hypotheekApp', []).controller('HypotheekController', ['$http', '$location', function($http, $location) {
    var self = this;
    self.model = {
        wozWaarde: 255000,
        belastingSchijf: 42,
        startDate: new Date(),
        hypotheken : [
            {
                hypotheekSom: 260000,
                rente: 2.9,
                looptijdMaanden: 360,
                aflosvorm: 'Annuiteit'
            }
        ]
    };
    var ctx = document.getElementById("chart").getContext("2d");
    var chart = undefined;

    self.toevoegen = function() {
        self.model.hypotheken.push({
            hypotheekSom: 0,
            rente: 2.9,
            looptijdMaanden: 360,
            aflosvorm: 'Annuiteit'
        })
    };

    self.verwijderen = function(index) {
        self.model.hypotheken.splice(index, 1)
    };

    self.initialize = function() {
        self.redraw();
    };

    self.redraw = function() {
        if (chart) {
            chart.destroy();
            chart = undefined;
            self.data = undefined;
        }
        var url = $location.absUrl() + 'api/lasten';
        $http.post(url, angular.toJson(self.model)).then(function(response) {
            self.data = response.data;

            var labels = [];
            var brutoData = [];
            var nettoData = [];
            for (var i = 0; i < self.data.length; i++) {
                var obj = self.data[i];
                labels.push(obj.year);
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

    Chart.defaults.global.responsive = false;
    Chart.defaults.global.maintainAspectRatio = false;
    Chart.defaults.global.scaleShowVerticalLines = false;
    Chart.defaults.global.animation = false;
    Chart.defaults.global.animationSteps = 10;
    // String - Animation easing effect
    // Possible effects are:
    // [easeInOutQuart, linear, easeOutBounce, easeInBack, easeInOutQuad,
    //  easeOutQuart, easeOutQuad, easeInOutBounce, easeOutSine, easeInOutCubic,
    //  easeInExpo, easeInOutBack, easeInCirc, easeInOutElastic, easeOutBack,
    //  easeInQuad, easeInOutExpo, easeInQuart, easeOutQuint, easeInOutCirc,
    //  easeInSine, easeOutExpo, easeOutCirc, easeOutCubic, easeInQuint,
    //  easeInElastic, easeInOutSine, easeInOutQuint, easeInBounce,
    //  easeOutElastic, easeInCubic]
    Chart.defaults.global.animationEasing = "linear";

    self.initialize();
}]).filter('sumByKey', function() {
    return function(data, key) {
        if (typeof(data) === 'undefined' || typeof(key) === 'undefined') {
            return 0;
        }

        var sum = 0;
        for (var i = data.length - 1; i >= 0; i--) {
            sum += parseInt(data[i][key]);
        }

        return sum;
    };
});

$(document).ready(function () {
    showHealthContent();
    getHeath();
});

function showHealthContent() {
    $('.content').hide();
    $('#health').show();
}

function getHeath() {
    showHealthContent();
    var healthOut = $('#healthOut');
    $.ajax({
        type: 'GET',
        url: 'actuator/health',
        success: function (data) {
            healthOut.text("");
            healthOut.append('app status: ' + data.status + '\n');
            healthOut.append('details:\n');
            $.each(data.details, function (key, value) {
                healthOut.append('  ' + key + ':\n');
                healthOut.append('      status: ' + value.status + '\n');
                healthOut.append('      details:\n');
                $.each(value.details, function (key, value) {
                    healthOut.append('          ' + key + ': ' + value + '\n');
                })
            })
        }
    });
}

function getLogFile() {
    $('.content').hide();
    $('#logfile').show();
    $.ajax({
        type: 'GET',
        url: 'actuator/logfile',
        success: function (data) {
            $('#logfileOut').text(data);
        }
    });
}

function getMetrics() {
    $('.content').hide();
    $('#metrics').show();
    var metrics = $('#metricsButtons');
    $.ajax({
        type: 'GET',
        url: 'actuator/metrics',
        success: function (data) {
            $.each(data.names, function (index, name) {
                var buttonMetric = $('<button>').addClass('btn btn primary').text(name).click(getConcreteMetric);
                metrics.append(buttonMetric);
            })
        }
    })
}

function getConcreteMetric() {
    var metric = $(this).text();
    var metricOut = $('#metricOut');
    $.ajax({
        type: 'GET',
        url: 'actuator/metrics/' + metric,
        success: function (data) {
            metricOut.text("");
            metricOut.append('name: ' + data.name + "\n");
            metricOut.append('description: ' + data.description + "\n");
            metricOut.append('base unit: ' + data.baseUnit + "\n");
            metricOut.append('measurements:\n');
            $.each(data.measurements, function (key, value) {
                metricOut.append(value.statistic + ": " + value.value + "\n");
            })
        }
    });
}